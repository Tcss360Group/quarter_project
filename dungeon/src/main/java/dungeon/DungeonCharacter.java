package dungeon;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dungeon.Controller.GameController;
import dungeon.View.CombatManager;

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

    ///whether or not we have been killed and updated our appearance to appear so.
    private boolean myHasDied;

    public DungeonCharacter(final Atom theLoc, final String theName, final double health, final double damage,
            final double range, final int attackSpeed, final double hitChance) {
        super(theLoc, theName);
        this.maxHealth = health;  
        this.health = health;  
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.hitChance = hitChance;
        myVisionPower = VISION_POWER;
        myHasDied = false;
        setSprite(new GameSprite("bad_guy.png", 0., 0., 10.0));
    }

    @Override
    public void clickOn(final Atom clicked) {
        Atom clickedOuter = clicked.getOuterLoc();
        Atom playerOuter = getOuterLoc();

        GameController controller = Main.getController();

        if(!physicalAccess(clicked, 1)) {
            return;
        }
        
        super.clickOn(clicked);

        if(distance(clickedOuter, playerOuter) == 0) {
            if(this == clicked) {
                controller.pushMessage("That's you");
                
            }
            else if(clicked instanceof DungeonCharacter dg) {
                controller.pushMessage("That's a " + dg.getName() + ", combat isnt implemented so they just stare daggers at you (those daggers do no damage).");
            } else if(clicked instanceof Pickupable && clicked instanceof Movable mov) {
                controller.pushMessage("You pick up the " + clicked.getName() + ".");
                mov.move(this);
            } else if(clicked instanceof Door door) {
                tryMoveTo((Room)door.getDest());
            }
        } else {
            if(clicked instanceof Room room && playerOuter instanceof Room) {
                tryMoveTo(room);
                if(getOuterLoc() == room) {
                    DungeonCharacter dg = null;
                    for(Atom content : room.getContents()) {
                        if(content instanceof Monster monst && monst != this) {
                            dg = monst;
                            break;
                        }
                    }

                    if(dg == null) {
                        return;
                    }

                    Atom dgOuter = dg.getOuterLoc();
                    if(!(dgOuter instanceof Room)) {
                        return;
                    }
                    Room dgRoom = (Room)dgOuter;
                    if(distance(getOuterLoc(), dgRoom) != 0) {
                        return;
                    }
                    if(dg.getHealth() <= 0) {
                        return;
                    }
                    controller.pushMessage("You enter the same room as the " + dg.getName() + ", prepare to fight!");
                    final DungeonCharacter target = dg;
                    try {
                     
                        SwingUtilities.invokeLater(() -> {
                            JFrame parentFrame = new JFrame("Game");
                            parentFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            parentFrame.setSize(800, 600);
                            parentFrame.setLocationRelativeTo(null);
                     
                            CombatManager.battle(parentFrame, this, target);
                            parentFrame.dispose();
                        });
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                }
            } 
        }
    }

    public void tryMoveTo(final Room theOtherRoom) {
        Atom ourRoomA = getOuterLoc();
        if(!(ourRoomA instanceof Atom)) {
            return;
        }
        Room ourRoom = (Room)ourRoomA;
        ArrayList<Atom> roomContents = ourRoom.getContents();
        Door portal = null;
        for(Atom content : roomContents) {
            if(content instanceof Door door) {
                if(door.getDest() == theOtherRoom) {
                    portal = door;
                    break; 
                }
            } 
        }
        if(portal == null) {
            Main.getController().pushMessage("You can't get there because there's no door!");
            return;
        }

        move(theOtherRoom);
    }

    /// Called every tick to make this DC perform an action
    public void pollAction() {
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.min(Math.max(0, health), maxHealth);  // Ensure health does not go below 0
    }
    public void addHealth(final double theDelta) {
        setHealth(health + theDelta);
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

    /// we have died, our health has fallen below zero.
    public void die() {
        myHasDied = true;
        GameSprite mSprite = getSprite();
        mSprite.setRotation(Math.toRadians(90));
        mSprite.setLayer(mSprite.getLayer() / 2);
        setSprite(mSprite);
    }
}
