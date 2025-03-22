package dungeon;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Optional;

public enum Dir  {

    N, E, S, W;

    /// returns the one dir representing the difference between us and them as points. 
    /// only one coord can be different to return a dir
    public static Optional<Dir> getDir(Point us, Point them) {
        if(them.getX() != us.getX() && them.getY() != us.getY()) {
            return Optional.empty();
        }
        if(them.equals(us)) {
            return Optional.empty();
        }
        if(them.getX() > us.getX()) {
            return Optional.of(Dir.E);
        }
        if(them.getX() < us.getX()) {
            return Optional.of(Dir.W);
        }
        if(them.getY() > us.getY()) {
            return Optional.of(Dir.S);
        }
        return Optional.of(Dir.N);
    }

    public static Optional<Dir> getDir(Atom us, Atom them) {
        int[] ourCoords = us.getOuterLoc().getCoords();
        int[] theirCoords = them.getOuterLoc().getCoords();
        if(ourCoords[Atom.Z] != theirCoords[Atom.Z]) {
            return Optional.empty();
        }
        return getDir(new Point(ourCoords[Atom.X], ourCoords[Atom.Y]), new Point(theirCoords[Atom.X], theirCoords[Atom.Y]));
    }

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
