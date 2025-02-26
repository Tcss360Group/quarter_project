package dungeon;

public interface Healing {
    
    // Abstract method to heal
    void healConstructor();

    // Getter for heal amount
    int getHealAmount();

    // Setter for heal amount 
    void setHealAmount(int amount, String description);
}
