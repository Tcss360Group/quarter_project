package dungeon;

public class Sword extends Movable implements Pickupable {
    private static final String NAME = "Sword";
    private static final GameSprite SPRITE = new GameSprite("sword.png", 0.3, 0.0, 0.0, 0.2, 0.6, 15.0);
    /// the player deals this much more damage as a flat rate when this is inside their inventory
    private double myDamageModifier;

    public Sword(final Atom theLoc, final double theDamageMod) {
        super(theLoc, NAME);
        myDamageModifier = theDamageMod;
        setSprite(SPRITE.clone());
    }

    public void setDamageModifier(final double theMod) {
        myDamageModifier = theMod;
    }
    public double getDamageModifier() {
        return myDamageModifier;
    }
}
