package dungeon;

///named values for the ability to see other things or not be seen by other things.
public enum VisionPower {
    NONE(0),
    HAZY(50),
    INVISIBLE(75),
    ///should never be seen unless in debug mode
    ABSTRACT(10000000);

    private final double myPower;

    private VisionPower(final double thePower) {
        myPower = thePower;
    }

    public double power() {
        return myPower;
    }
}
