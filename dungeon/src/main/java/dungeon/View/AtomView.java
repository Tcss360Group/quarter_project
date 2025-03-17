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
    private AffineTransform myTransform;
    private AffineTransform myScreenSpaceTransform;
    private int myWidth;
    private int myHeight;

    //private Rectangle2D.Double myRect; //i dont know what size rects should be

    public AtomView(final int theID, final Atom theAtom, final Rectangle theScreenBounds) {
        int[] coords = theAtom.getCoords();
        double ox = (double)coords[Atom.X];
        double oy = (double)coords[Atom.Y];

        double sx = theScreenBounds.getX();
        double sy = theScreenBounds.getY();

        ox -= sx;
        oy -= sy;

        GameSprite sprite = theAtom.getSprite();
        BufferedImage theImage = sprite.getImage();

        myWidth = DEFAULT_WIDTH;
        myHeight = DEFAULT_HEIGHT;

        //ox -= myWidth;
        //oy -= myHeight;

        AffineTransform transform = new AffineTransform();
        transform.translate(ox, oy);
        //transform.scale(myWidth / theImage.getWidth(), myHeight / theImage.getHeight());

        myID = theID;
        myName = theAtom.getName();
        mySprite = theAtom.getSprite();
        myTransform = transform;
        //will be filled when we're given to the GamePanel
        myScreenSpaceTransform = null;
    }

    public void calculateScreenSpaceTransform(final int resX, final int resY) {
        double resXPerWorldWidth = resX / InOut.SCREEN_WIDTH_TILES;
        double resYPerWorldHeight = resY / InOut.SCREEN_HEIGHT_TILES;

        double ourXScale = 1 / resXPerWorldWidth;
        double ourYScale = 1 / resYPerWorldHeight;

        myScreenSpaceTransform = new AffineTransform();
        myScreenSpaceTransform.scale(resXPerWorldWidth, resYPerWorldHeight);
        myScreenSpaceTransform.concatenate(myTransform);
        Point2D screenPoint = myScreenSpaceTransform.transform(new Point2D.Double(0.,0.), null);
        int asda = 1;
    }

    public AffineTransform getScreenSpaceTransform() {
        return (AffineTransform)myScreenSpaceTransform.clone();
    }

    public void render(final Graphics2D g) {
        
        //BufferedImage image = mySprite.getImage();
        //int width = image.getWidth();
        //int height = image.getHeight();

        //for(int y = 0; y < height; y++) {
        //    for(int x = 0; x < width; x++) {
        //        int rgb = image.getRGB(x,y);
        //        int red = (rgb >> 16) & 0xFF;
        //        int green = (rgb >> 8) & 0xFF;
        //        int blue = rgb & 0xFF;

        //        // Print the RGB values
        //        System.out.printf("Pixel (%d, %d): R=%d, G=%d, B=%d\n", x, y, red, green, blue);
        //    }
        //}

        //g.setColor(Color.RED);
        //Point2D point1 = getScreenSpaceTransform().transform(new Point2D.Double(0,0), null);
        //g.fillRect((int)point1.getX(), (int)point1.getY(), 100, 100);
        //System.out.println("width: " + image.getWidth() + " height: " + image.getHeight() + " type: " + image.getType());
        Point2D point = getScreenSpaceTransform().transform(new Point2D.Double(0,0), null);
        g.drawImage(mySprite.getImage(), null, (int)point.getX(), (int)point.getY());
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
        return point.getX() >= 0 && point.getX() < mySprite.getImage().getWidth() && point.getY() >= 0 && point.getY() < mySprite.getImage().getHeight();
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
