package dungeon;

import java.util.ArrayList;

public abstract class Hero extends DungeonCharacter {
    private final double blockChance;
    private final String specialSkill;

    public Hero(final Atom theLoc, final String theName, final double health, final double damage, final double range, final int attackSpeed, final double hitChance, 
                final double blockChance, final String specialSkill) {
        
        super(theLoc, theName, health * 3, damage,  range, attackSpeed, hitChance);
        this.blockChance = blockChance;
        this.specialSkill = specialSkill;
        setSprite(new GameSprite("warrior.jpg", 0., 0., 10.0));
    }

    /**
     * returns the sum damage mod of all the swords in our inventory
     */
    public double getSumWeaponMod() {
        double sumDamageMod = 0;
        for(Atom content : getRecursiveContents()) {
            if(content instanceof Sword swordOfAThousandTruths) {
                sumDamageMod += swordOfAThousandTruths.getDamageModifier();
            }
        }
        return sumDamageMod;
    }

    public int getNumSwords() {
        int numSwords = 0; //yes it scales infinitely
        for(Atom content : getRecursiveContents()) {
            if(content instanceof Sword swordOfAThousandTruths) {
                numSwords++;
            }
        }
        return numSwords;
    }

    public double getBlockChance() {
        return blockChance;
    }

    public String getSpecialSkill() {
        return specialSkill;
    }

    /**
     * returns an arraylist of descriptions of whats happening to the combat manager
     * @param theTarget the target we're using the skill on
     */
    public abstract ArrayList<String> useSpecialSkill(final DungeonCharacter theTarget);

    @Override
    public double attack() {
        return getDamage();
    }
}
