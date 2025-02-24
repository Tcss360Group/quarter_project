package dungeon;

public enum InitOrder {
    ///no staging whatsoever, the atom will initialize immediately
    IMMEDIATE,
    ///the atom is in the first batch to initialize in the next AtomLoader iteration
    NORMAL,
    ///the atom is in the second batch to initialize in the next AtomLoader iteration, allows reacting to normal initers in the same batch
    LATE,
    ///the atom is in the third batch to initialize in the next AtomLoader iteration, allows reacting to normal and late initers in the same batch
    /// you most likely want LATE init, not this.
    LATER;

    public int index() {
        switch (this) {
            case IMMEDIATE: return -1;
            case NORMAL: return 0;
            case LATE: return 1;
            case LATER: return 2;
            default:
                return -1000;
        }
    }
}
