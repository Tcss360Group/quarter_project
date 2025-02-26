package dungeon;

public class Pillar extends Physical {
    private static int collectedPillars = 0; // Track how many pillars are collected

    public Pillar(final Atom theLoc, final String theName) {
        super(theLoc, theName);
    }

    public void collect() {
        collectedPillars++;
        System.out.println("You have collected a pillar! Total: " + collectedPillars);

        // Replace the pillar with a stair
        replaceWithStair();

        // Check if the game should end
        if (collectedPillars >= 4) {
            endGame();
        }
    }

    private void replaceWithStair() {
        // Creates a new stair at the current location
        Stair stair = new Stair(this.getLoc(), "Staircase");
        this.getLoc().addContents(stair); // Add the stair to the same location
        this.getLoc().removeContents(this); // Remove the pillar from the location
        System.out.println("A staircase has appeared!");
    }

    private void endGame() {
        System.out.println("You have collected all 4 pillars! The game is over!");
        // Trigger any game end logic here (like showing an end screen or stopping the game)
    }

    public static int getCollectedPillars() {
        return collectedPillars;
    }
}
