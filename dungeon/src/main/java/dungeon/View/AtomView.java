package dungeon.View;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import dungeon.Atom;
import dungeon.GameSprite;
import dungeon.Controller.SystemController.InOut;

/**
 * what gets sent to the DungeonPanel to draw atoms
 * images are in image space, we need an image + an affine transform + bounds
 * to describe an atom irt when how and where it is drawn
 */
public class AtomView implements Shape {
    public static final int DEFAULT_WIDTH = 32;
    public static final int DEFAULT_HEIGHT = 32;
    private int myID;
    private String myName;
    private GameSprite mySprite;
    private BufferedImage myImage;
    /// gives us the translation we should have before knowing the resolution of the screen
    private AffineTransform myTransform;
    /// exactly how we are shown on the screen
    private AffineTransform myScreenSpaceTransform;

    //private Rectangle2D.Double myRect; //i dont know what size rects should be

    public AtomView(final int theID, final Atom theAtom, final Rectangle theScreenBounds) {
        int[] coords = theAtom.getCoords();
        double ox = (double)coords[Atom.X];
        double oy = (double)coords[Atom.Y];

        double sx = theScreenBounds.getX();
        double sy = theScreenBounds.getY();

        ox -= sx;
        oy -= sy;

        AffineTransform transform = new AffineTransform();
        transform.translate(ox, oy);

        myID = theID;
        myName = theAtom.getName();
        mySprite = theAtom.getSprite();
        myTransform = transform;
        myImage = mySprite.getImage();
        //will be filled when we're given to the GamePanel
        myScreenSpaceTransform = null;
        
    }

    public void calculateScreenSpaceTransform(final int resX, final int resY) {
        double resXPerWorldWidth = resX / InOut.SCREEN_WIDTH_TILES;
        double resYPerWorldHeight = resY / InOut.SCREEN_HEIGHT_TILES;
        BufferedImage image = mySprite.getImage();
        double ourXScale = mySprite.getWidth() * resXPerWorldWidth / image.getWidth();
        double ourYScale = mySprite.getHeight() * resYPerWorldHeight / image.getHeight();


        myScreenSpaceTransform = new AffineTransform();

        //we have to rotate by the center of the image
        //myScreenSpaceTransform.translate(-ourXScale/2, -ourYScale/2);
        //myScreenSpaceTransform.translate(ourXScale/2, ourYScale/2);

        double XWidthTranslateFactor = Math.abs(mySprite.getWidth() - 1)/2;
        double YHeightTranslateFactor = Math.abs(mySprite.getHeight() - 1)/2;

        double translateX = myTransform.getTranslateX() + mySprite.getX() + XWidthTranslateFactor;
        double translateY = myTransform.getTranslateY() + mySprite.getY() + YHeightTranslateFactor;

        myScreenSpaceTransform.translate(translateX * resXPerWorldWidth, translateY * resYPerWorldHeight);
        myScreenSpaceTransform.scale(ourXScale, ourYScale);
        myScreenSpaceTransform.rotate(mySprite.getRotation(), image.getWidth() / 2, image.getHeight() / 2);

        //myScreenSpaceTransform.concatenate(myTransform);
        Point2D screenPoint = myScreenSpaceTransform.transform(new Point2D.Double(0.,0.), null);
    }

    public AffineTransform getScreenSpaceTransform() {
        return (AffineTransform)myScreenSpaceTransform.clone();
    }

    public void render(final Graphics2D g) {

        Point2D point = getScreenSpaceTransform().transform(new Point2D.Double(0,0), null);

        g.drawImage(mySprite.getImage(), myScreenSpaceTransform, null);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle first = new Rectangle(0, 0, mySprite.getImage().getWidth(), mySprite.getImage().getHeight());
        Shape transform = myScreenSpaceTransform.createTransformedShape(first);

        Rectangle ret = (Rectangle) myScreenSpaceTransform.createTransformedShape(new Rectangle(0, 0, mySprite.getImage().getWidth(), mySprite.getImage().getHeight())).getBounds();
        int i = 1;
        return ret;

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * returns the alpha of the pixel at the specified point in screen space belongs to if and only if said point is actually inside us, and returns Optional.empty() otherwise
     */
    public Optional<Integer> getContainingPixelAlpha(double x, double y) {

        Point2D point = new Point2D.Double(x,y);
        try {

            point = AffineTransform.getTranslateInstance(myScreenSpaceTransform.getTranslateX(), myScreenSpaceTransform.getTranslateY()).inverseTransform(point, null);
        } catch(Exception e) {
            int d = 1;
        }

        int width = mySprite.getImage().getWidth();
        int height = mySprite.getImage().getHeight();
        boolean inside = point.getX() >= 0 && point.getX() < mySprite.getImage().getWidth() * myScreenSpaceTransform.getScaleX() && point.getY() >= 0 && point.getY() < mySprite.getImage().getHeight() * myScreenSpaceTransform.getScaleY();
        if(!inside) {
            return Optional.empty();
        }
        
        int ret = mySprite.getImage().getRGB((int)(point.getX() / myScreenSpaceTransform.getScaleX()), (int)(point.getY() / myScreenSpaceTransform.getScaleY()));
        return Optional.of(Integer.valueOf((ret >> 24) & 0xff));
    }

    @Override
    public boolean contains(double x, double y) {
        Point2D point = new Point2D.Double(x,y);
        try {

            point = AffineTransform.getTranslateInstance(myScreenSpaceTransform.getTranslateX(), myScreenSpaceTransform.getTranslateY()).inverseTransform(point, null);
        } catch(Exception e) {
            int d = 1;
        }

        int width = mySprite.getImage().getWidth();
        int height = mySprite.getImage().getHeight();
        return point.getX() >= 0 && point.getX() < mySprite.getImage().getWidth() * myScreenSpaceTransform.getScaleX() && point.getY() >= 0 && point.getY() < mySprite.getImage().getHeight() * myScreenSpaceTransform.getScaleY();
        //return mySprite.getImage().getRGB(point.getX(), height)
        //return getBounds().contains(x,y);

    }

    @Override
    public boolean contains(Point2D p) {
        return contains(p.getX(), p.getY());
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return getBounds().contains(x,y,w,h);
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return getBounds().contains(r);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        return myName;
    }

    public void setName(String myName) {
        this.myName = myName;
    }

    public GameSprite getSprite() {
        return mySprite;
    }

    public void setSprite(GameSprite mySprite) {
        this.mySprite = mySprite;
    }

    public void setTransform(final AffineTransform theTransform) {
        myTransform = theTransform;
    }
    public AffineTransform getTransform() {
        return (AffineTransform)myTransform.clone();
    }

    public int getID() {
        return myID;
    }

}
