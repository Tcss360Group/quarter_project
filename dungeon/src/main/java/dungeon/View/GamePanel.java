package dungeon.View;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

import java.awt.event.MouseEvent;

import dungeon.Messages.RTV.RenderToViewMessage;
import dungeon.Messages.VTR.ViewToRenderMessage;
import dungeon.Messages.VTM.ClickedOn;
import dungeon.Messages.MTV.Update;

import java.awt.BufferCapabilities;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.ImageCapabilities;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

public class GamePanel extends Canvas implements Runnable  {
    private static final long serialVersionUID = 1L;

    public static final long FRAMERATE_TARGET_MILLISECONDS = 16;
    private volatile boolean running = true; 
    private volatile boolean myMouseIsOver = false;
    private volatile Point myMousePos = null;
    private LinkedBlockingQueue<ViewToRenderMessage> updateQueue;
    private LinkedBlockingQueue<RenderToViewMessage> outputQueue;
    /// so we can check what the user clicked on
    private LinkedBlockingQueue<Point> myClickedPointsQueue;
    private ArrayList<AtomView> currentViews = new ArrayList<>(); 

    public GamePanel(final LinkedBlockingQueue<ViewToRenderMessage> theUpdateQueue, final LinkedBlockingQueue<RenderToViewMessage> theOutputQueue) {
        updateQueue = theUpdateQueue;
        outputQueue = theOutputQueue;
        myClickedPointsQueue = new LinkedBlockingQueue<>();
        //setPreferredSize(new Dimension(800, 600)); 
        setIgnoreRepaint(true);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                myClickedPointsQueue.add(e.getPoint());
            }
            public void mouseEntered(MouseEvent e) {
                myMouseIsOver = true;
            }
            public void mouseExited(MouseEvent e) {
                myMouseIsOver = false;
                myMousePos = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(myMouseIsOver) {
                    myMousePos = e.getPoint();
                }
            }
        });

    }

    public synchronized void addUpdate(ViewToRenderMessage update) {
        updateQueue.offer(update); 
    }

    @Override
    public void run() {
        int x = 1;
        Point lastMousePos = null;
        while (running) {
            long startTime = System.nanoTime();
            BufferStrategy bufferStrategy = getBufferStrategy();
            if(bufferStrategy == null) {
                System.out.println("render loop cant run because bufferStrategy is null");
                sleepForABit(startTime);
                continue;
            }
            boolean shouldRedraw = processUpdates();


            Point clickedPoint = null;
            while(!myClickedPointsQueue.isEmpty()) {
                clickedPoint = myClickedPointsQueue.poll();
            }

            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);


            render(g); 

            if(myMouseIsOver == true || clickedPoint != null) {
                if(myMousePos != null && !myMousePos.equals(lastMousePos)) { 
                    lastMousePos = (Point)myMousePos.clone();
                }
                Point posToUse = clickedPoint != null ? clickedPoint : myMousePos;
                if(posToUse != null) {
                    synchronized(this) {
                        AtomView highestLayerClickedView = null;
                        for(AtomView view : currentViews) {
                            Optional<Integer> ret = view.getContainingPixelAlpha(posToUse.getX(), posToUse.getY());
                            if(ret.isEmpty()) {
                                continue;
                            }
                            int alphaValue = ret.get().intValue();
                            if(alphaValue > 0 && (highestLayerClickedView == null || view.getSprite().getLayer() > highestLayerClickedView.getSprite().getLayer())) {
                                highestLayerClickedView = view;
                            }
                        }
                        if(highestLayerClickedView != null) {
                            AtomView view = highestLayerClickedView;
                            if(clickedPoint != null) {
                                outputQueue.add(new ClickedOn(view));
                            }
                            g.setColor(Color.BLACK);
                            g.drawString(view.getName(), 10, getHeight() - 50);
                        }
                    }
                } 
            }
            bufferStrategy.show(); 

            g.dispose(); 
            sleepForABit(startTime);
        }
    }

    private void sleepForABit(long startTime) {

        long elapsedTime = System.nanoTime() - startTime;
        long sleepTime = Math.max(5, FRAMERATE_TARGET_MILLISECONDS - elapsedTime / 1_000_000);
        try {
            Thread.sleep(sleepTime); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    ///returns true if we should do a full redraw of every AtomView
    private boolean processUpdates() {
        synchronized(this) {

            ViewToRenderMessage update;
            boolean hadUpdate = false;
            while ((update = updateQueue.poll()) != null) {
                if(update instanceof Update up) {
                    currentViews = up.views;
                    hadUpdate = true;
                }
            }

            for(AtomView view : currentViews) {
                view.calculateScreenSpaceTransform(getWidth(), getHeight());
            }


            return hadUpdate;
        }
    }

    private void render(Graphics2D g) {
        synchronized(this) {

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            // Render all AtomViews
            //g.setColor(Color.RED);
            //ArrayList<AtomView> clonedView = (ArrayList<AtomView>)currentViews.clone();
            for (AtomView view : currentViews) {
                //g.drawImage(view.getSprite().getImage(), view.getTransform(), null);
                //g.setColor(Color.RED);
                //Point2D point = view.getScreenSpaceTransform().transform(new Point2D.Double(0,0), null);
                //g.fillRect((int)point.getX(), (int)point.getY(), 100, 100);
                view.render(g);
            }

        }
    }


    public void stopRendering() {
        running = false; // Stop the render loop
    }
}
