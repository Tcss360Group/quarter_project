package dungeon;

public abstract class Hero {
    
    private double blockChance;
    private String specialSkill;
    
    public Hero(double blockChance, String specialSkill) {
        this.blockChance = blockChance;
        this.specialSkill = specialSkill;
    }
    
    public double getBlockChance() {
        return blockChance;
    }
    
    public void setBlockChance(double blockChance) {
        this.blockChance = blockChance;
    }
    
    public String getSpecialSkill() {
        return specialSkill;
    }
    
    public void setSpecialSkill(String specialSkill) {
        this.specialSkill = specialSkill;
    }
    
    public abstract void useSpecialSkill();
}
