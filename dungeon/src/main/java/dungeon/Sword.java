package dungeon;

public class Sword extends Movable implements Pickupable {
    private static final String NAME = "Sword";
    /// the player deals this much more damage as a flat rate when this is inside their inventory
    private int myDamageModifier;

    public Sword(final Atom theLoc) {
        super(theLoc, NAME);
    }

    public void setDamageModifier(final int theMod) {
        myDamageModifier = theMod;
    }
    public int getDamageModifier() {
        return myDamageModifier;
    }
}
