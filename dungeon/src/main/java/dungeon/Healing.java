public interface Healing {
    
    // Abstract method to heal
    void healConstructor();

    // Getter for heal amount
    int getHealAmount();

    // Setter for heal amount (assuming it takes an int and a string for some reason)
    void setHealAmount(int amount, String description);
}
