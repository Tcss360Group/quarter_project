package dungeon;

import java.util.ArrayList;

public abstract class DungeonCharacter extends Movable {

    private static final double VISION_POWER = VisionPower.NONE.power();

    private double health;
    private final double maxHealth;  // Store the initial max health
    private double damage;
    private double range;
    private int attackSpeed;
    private double hitChance;
    /// any other movable in the room with a vision power lower than this can be seen by 
    private double myVisionPower;

    public DungeonCharacter(final Atom theLoc, final String theName, final double health, final double damage,
            final double range, final int attackSpeed, final double hitChance) {
        super(theLoc, theName);
        this.maxHealth = health;  // Set maxHealth during construction
        this.health = health;  // Current health
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.hitChance = hitChance;
        myVisionPower = VISION_POWER;
        setSprite(new GameSprite("door-S.png", 0., 0., 10.0));
    }

    @Override
    public void clickOn(final Atom clicked) {
        Atom clickedOuter = clicked.getOuterLoc();
        Atom playerOuter = getOuterLoc();

        //if(!physicalAccess(clicked, 1)) {
        //    return;
        //}
        
        super.clickOn(clicked);

        if(distance(clickedOuter, playerOuter) == 0) {
            if(this == clicked) {
                System.out.println("That's you");
            }
            else if(clicked instanceof DungeonCharacter dg) {
                System.out.println("That's a " + dg.getName() + ", combat isnt implemented so they just stare daggers at you (those daggers do no damage).");
            } else if(clicked instanceof Pickupable && clicked instanceof Movable mov) {
                System.out.println("You pick up the " + clicked.getName() + ".");
                mov.move(this);
            }
        } else {
            if(clicked instanceof Room room && playerOuter instanceof Room ourRoom) {
                ArrayList<Atom> roomContents = ourRoom.getContents();
                Door portal = null;
                for(Atom content : roomContents) {
                    if(content instanceof Door door) {
                        if(door.getDest() == room) {
                            portal = door;
                            break; 
                        }
                    } 
                }
                if(portal == null) {
                    System.out.println("You can't get there because there's no door!");
                    return;
                }

                move(room);
            }
        }
    }

    /// Called every tick to make this DC perform an action
    public void pollAction() {
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.max(0, health);  // Ensure health does not go below 0
    }

    public double getMaxHealth() {
        return maxHealth;  // Return the max health (unchanged value)
    }

    public double getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public double getHitChance() {
        return hitChance;
    }

    public double getVisionPower() {
        return myVisionPower;
    }

    public void setVisible(final double theVisionPower) {
        myVisionPower = theVisionPower;
    }

    public abstract double attack();
}
