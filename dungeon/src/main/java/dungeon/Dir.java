package dungeon;

public enum Dir {
    N, E, S, W;

    public Dir clockwise() {
        switch (this) {
            case N: return E;
            case E: return S;
            case S: return W;
            case W: return N;
        }
        return null;
    }

    public Dir counterClockwise() {
        switch (this) {
            case N:
                return W;
            case E:
                return N;
            case S:
                return E;
            case W:
                return S;
        }
        return null;
    }

    public Dir around() {
        switch (this) {
            case N:
                return S;
            case E:
                return W;
            case S:
                return N;
            case W:
                return E;
        }
        return null;
    }

}
