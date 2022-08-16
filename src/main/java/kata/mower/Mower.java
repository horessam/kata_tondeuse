package kata.mower;

import static kata.mower.Orientation.*;

public class Mower {
    private static final char RIGHT_ROTATION_COMMAND = 'D';
    private static final char LEFT_ROTATION_COMMAND = 'G';
    private static final char ADVANCE_COMMAND = 'A';

    private Coordinate coordinate;
    private final Coordinate maxCoordinate;
    private Orientation orientation;

    public Mower(Coordinate coordinate, Coordinate maxCoordinate, Orientation orientation) {
        this.coordinate = coordinate;
        this.maxCoordinate = maxCoordinate;
        this.orientation = orientation;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    private void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void execute(String sequenceCommand) {
        for (int i = 0; i < sequenceCommand.length(); i++) {
            execute(sequenceCommand.charAt(i));
        }
    }

    private void execute(char command) {
        switch (command) {
            case RIGHT_ROTATION_COMMAND -> this.rotateRight();
            case LEFT_ROTATION_COMMAND -> this.rotateLeft();
            case ADVANCE_COMMAND -> this.advance();
            default -> throw new IllegalStateException("Unexpected value: " + command);
        }
    }

    private void rotateLeft() {
        this.orientation = switch (this.orientation) {
            case N -> W;
            case E -> N;
            case S -> E;
            case W -> S;
        };
    }

    private void rotateRight() {
        this.orientation = switch (this.orientation) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }

    private void advance() {
        switch (this.orientation) {
            case W -> goRight();
            case E -> goLeft();
            case N -> goUp();
            case S -> goDown();
        }
    }

    private void goRight() {
        int x = this.coordinate.x();
        if (x > 0) {
            setCoordinate(Coordinate.of(x - 1, this.coordinate.y()));
        }
    }

    private void goLeft() {
        int x = this.coordinate.x();
        if (x < this.maxCoordinate.x()) {
            setCoordinate(Coordinate.of(x + 1, this.coordinate.y()));
        }
    }

    private void goUp() {
        int y = this.coordinate.y();
        if (y < this.maxCoordinate.y()) {
            setCoordinate(Coordinate.of(this.coordinate.x(), y + 1));
        }
    }

    private void goDown() {
        int y = this.coordinate.y();
        if (y > 0) {
            setCoordinate(Coordinate.of(this.coordinate.x(), y - 1));
        }
    }
}
