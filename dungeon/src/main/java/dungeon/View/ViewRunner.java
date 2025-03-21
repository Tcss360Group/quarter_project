package dungeon.View;

import java.awt.BorderLayout;
import java.awt.BufferCapabilities;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.ImageCapabilities;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import dungeon.Atom;
import dungeon.DungeonCharacter;
import dungeon.Main;
import dungeon.Controller.GameController;
import dungeon.Messages.MTV.AddMessage;
import dungeon.Messages.MTV.ChangeState;
import dungeon.Messages.MTV.ModelToViewMessage;
import dungeon.Messages.MTV.Update;
import dungeon.Messages.RTV.RenderToViewMessage;
import dungeon.Messages.VTM.ClassSelected;
import dungeon.Messages.VTM.ClickedOn;
import dungeon.Messages.VTM.ViewToModelMessage;
import dungeon.Messages.VTR.ViewToRenderMessage;
import dungeon.Monster;
import dungeon.MonsterFactory;
import dungeon.Room;
import dungeon.View.ViewState;

public final class ViewRunner {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private TitleScreenPanel titleScreen;
    private GamePanel dungeonPanel;
    private JPanel outerDungeonPanel;
    private JPanel messagePanel;
    private JTextArea messageArea;

    private GameController myController;
    private LinkedBlockingQueue<ViewToModelMessage> myVTMQueue;
    private LinkedBlockingQueue<ModelToViewMessage> myMTVQueue;

    /// so we can send updates to the active renderer
    private LinkedBlockingQueue<ViewToRenderMessage> myToGamePanelQueue;
    private LinkedBlockingQueue<RenderToViewMessage> myFromGamePanelQueue;

    private JButton[][] rooms = new JButton[5][5];
    private String selectedHero = "";  // To store the selected hero
    //private Hero hero;  // The player's hero
    private int viewX = 10;
    private int viewY = 10;
    private double scaleX = 1.0 / 32.0;
    private double scaleY = 1.0 / 32.0;


    private ViewState myState = ViewState.MAIN_MENU;
    /// milliseconds
    private long myFrameTime = 16;
    private Timer myTimer = null;
    private Thread myRenderThread = null;

    //this is the EDT i think
    public ViewRunner(final GameController theController) {
        myController = theController;
        myMTVQueue = (LinkedBlockingQueue<ModelToViewMessage>)myController.myMTVQueue;
        myVTMQueue = (LinkedBlockingQueue<ViewToModelMessage>)myController.myVTMQueue;
        myTimer = new Timer();

        frame = new JFrame("Dungeon Adventure Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(true);

        // Title screen setup
        titleScreen = new TitleScreenPanel(new TitleScreenController(frame, cardLayout, mainPanel));
        mainPanel.add(titleScreen, "TitleScreen");

        myToGamePanelQueue = new LinkedBlockingQueue<>() {
            @Override
            public boolean add(ViewToRenderMessage toAdd) {

                if(Main.getDebugConcurrency()) {
                    System.out.println("VTRQueue.add(): " + toAdd.toString());
                }
                return super.add(toAdd);
            }
            @Override
            public ViewToRenderMessage poll() {
                ViewToRenderMessage ret = super.poll();
                if(ret != null) {
                    if(Main.getDebugConcurrency()) {
                        System.out.println("VTRQueue poll() got " + ret.toString());
                    }
                }
                return ret;
            }
        };
        myFromGamePanelQueue = new LinkedBlockingQueue<>() {

            @Override
            public boolean add(RenderToViewMessage toAdd) {

                if(Main.getDebugConcurrency()) {
                    System.out.println("RTVQueue added " + toAdd.toString());
                }
                return super.add(toAdd);
            }
            @Override
            public RenderToViewMessage poll() {
                RenderToViewMessage ret = super.poll();

                if(Main.getDebugConcurrency()) {
                    System.out.println("RTVQueue poll() got " + ret.toString());
                }
                return ret;
            }
        };

        outerDungeonPanel = new JPanel(new BorderLayout());
        // Dungeon grid setup
        //dungeonPanel = new JPanel(new GridLayout(5, 5));
        // Dungeon grid setup
        dungeonPanel = new GamePanel(myToGamePanelQueue, myFromGamePanelQueue);
        outerDungeonPanel.add(dungeonPanel, BorderLayout.CENTER);

        messagePanel = new JPanel();
        messageArea = new JTextArea(10, 100);
        messageArea.setEditable(false);
        messagePanel.add(new JScrollPane(messageArea));
        outerDungeonPanel.add(messagePanel, BorderLayout.SOUTH);
        messageArea.setText("");

        mainPanel.add(outerDungeonPanel, "Dungeon");

        frame.add(mainPanel);
        cardLayout.show(mainPanel, "TitleScreen"); // Show title screen first
        frame.setVisible(true);
        frame.pack();

        myRenderThread = new Thread(() -> {
            dungeonPanel.run();
        });
        //myRenderThread.start();

        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkQueues();
            }
        }, myFrameTime);

    }

    public void checkQueues() {
        BufferStrategy bufferStrategy = frame.getBufferStrategy();
        
        //System.out.println("ViewRunner.checkQueues()");
        long startingTime = System.nanoTime();
        LinkedBlockingQueue<ModelToViewMessage> in = myMTVQueue;
        LinkedBlockingQueue<ViewToModelMessage> out = myVTMQueue;
        LinkedBlockingQueue<ViewToRenderMessage> toRender = myToGamePanelQueue;
        LinkedBlockingQueue<RenderToViewMessage> fromRender = myFromGamePanelQueue;

        while(!in.isEmpty()) {
            ModelToViewMessage input = in.remove();
            //System.out.println("ViewRunner.checkQueues() received MTV input: " + input.toString());
            if(input instanceof ChangeState cs) {
                if(myState == ViewState.MAIN_MENU && cs.state == ViewState.GAME) {
                    cardLayout.show(mainPanel, "Dungeon");

                    ImageCapabilities acceleration = new ImageCapabilities(true);
                    try {
                        dungeonPanel.createBufferStrategy(2, new BufferCapabilities(acceleration, acceleration, BufferCapabilities.FlipContents.UNDEFINED));

                    } catch (Exception e) {
                        int j = 1;
                    }
                    myRenderThread.start();
                }
                myState = cs.state;
            }
            else if(input instanceof Update up) {
                if(myState != ViewState.GAME) {
                    continue;
                }
                ArrayList<AtomView> newAtoms = up.views;
                newAtoms.sort((a, b) -> {return (int) (a.getSprite().getLayer() - b.getSprite().getLayer());});
                toRender.add(new Update((ArrayList<AtomView>)newAtoms.clone()));
                continue;
            } else if(input instanceof AddMessage am) {
                messageArea.append(am.message + "\n");
                messageArea.setCaretPosition(messageArea.getDocument().getLength());
            }
        }
        while(!fromRender.isEmpty()) {
            RenderToViewMessage input = fromRender.remove();
            System.out.println("ViewRunner.checkQueues() received RTV input: " + input.toString());
            if(input instanceof ClickedOn clcked) {
                out.add(clcked);
            }
        } 

        long ranFor = System.nanoTime() - startingTime;
        long sleepFor = Math.max(0, (myFrameTime - ranFor) / 1000);

        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkQueues();
            }
        }, sleepFor);
    }

    public String getHero() {
        return selectedHero;
    }

    // Title screen controller to handle button clicks
    private class TitleScreenController implements ActionListener {
        private final JFrame myFrame;
        private final CardLayout myLayout;
        private final JPanel myMainPanel;
        public TitleScreenController(final JFrame frame, final CardLayout cardLayout, final JPanel mainPanel) {
            myFrame = frame;
            myLayout = cardLayout;
            myMainPanel = mainPanel;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
    
            switch (command) {
                case "New Game":
                    // Show hero selection dialog before starting the game
                    showHeroSelectionDialog(myFrame, myLayout, myMainPanel);
                    break;
                case "Load Game":
                    JOptionPane.showMessageDialog(myFrame, "Implement");
                    break;
                case "Exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
            }
        }
    }


    private void showHeroSelectionDialog(final JFrame frame, final CardLayout cardLayout, final JPanel mainPanel) {
        // Create hero options
        String[] heroes = {"Warrior", "Thief", "Priestess"};
        String chosenHero = (String) JOptionPane.showInputDialog(
                frame,
                "Select your hero:",
                "Hero Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                heroes,
                heroes[0]
        );

        if (chosenHero != null) {
            //this.selectedHero = chosenHero;
            System.out.println(selectedHero + " selected!");

            //// Create the hero object based on selection
            //Room initialRoom = new Room(new int[]{0, 0}, "Starting Room"); // Example room for hero location
            //switch (chosenHero) {
            //    case "Warrior":
            //        this.hero = new Warrior(initialRoom); 
            //        break;
            //    case "Thief":
            //        this.hero = new Thief(initialRoom);  
            //        break;
            //    case "Priestess":
            //        this.hero = new Priestess(initialRoom); 
            //        break;
            //    default:
            //        this.hero = new Warrior(initialRoom); // Default to Warrior if invalid
            //}

            // Initialize dungeon grid and switch to it
            //initializeDungeon();
            //myState = ViewState.GAME;
            //cardLayout.show(mainPanel, "Dungeon");
            myVTMQueue.add(new ClassSelected(chosenHero));
        } else {
            System.out.println("Hero selection canceled.");
        }
    }

    private class RoomActionListener implements ActionListener {
        private final int row, col;
    
        public RoomActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked room at (" + row + ", " + col + ")");
            // Create a random monster for combat
            Room currentRoom = new Room(new int[]{row, col}, "Room " + (row * 5 + col + 1));
            Monster monster = MonsterFactory.createRandomMonster(currentRoom); 
            // Start combat
            //CombatManager.battle(frame, hero, monster);  
        }
    }
    

    //// Main method
    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(ViewRunner::new);
    //}
}
