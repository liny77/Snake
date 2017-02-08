package snake;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Created by Shower on 2017/1/23 0023.
 */
public class SnakeModel{
    public enum Direction {UP, DOWN, LEFT, RIGHT}
    public LinkedList<Position> body;
    public boolean eat = false;
    public Direction towards = Direction.RIGHT;
    public int speed = 110;

    public SnakeModel() {
        body = new LinkedList<>();
        reset();
    }

    public void reset() {
        body.clear();
        speed = 110;
        eat = false;
        towards = Direction.RIGHT;
        for (int i = 0; i < 10; ++i) {
            body.add(new Position(10, i * 10));
        }
    }

    public void turn(Direction d) {
        Position h = body.getLast();
        Position t = body.get(body.size() - 2);
        int xx = h.x - t.x;
        int yy = h.y - t.y;
        if (xx == 10 && d != Direction.UP) towards = d;
        else if (xx == -10 && d != Direction.DOWN) towards = d;
        else if (yy == 10 && d != Direction.LEFT) towards = d;
        else if (yy == -10 && d != Direction.RIGHT) towards = d;
    }

    public Position moveForward(Position food) {
        Position newHead = new Position(body.getLast());
        switch (towards) {
            case DOWN:
                newHead.x += 10;
                break;
            case LEFT:
                newHead.y -= 10;
                break;
            case UP:
                newHead.x -= 10;
                break;
            case RIGHT:
                newHead.y += 10;
                break;
        }
        body.add(newHead);
        if (!newHead.equals(food)) body.removeFirst();
        else eat = true;
        return newHead;
    }

    static class Position {
        int x;
        int y;

        //   y
        // x
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Position(Position p) {
            x = p.x;
            y = p.y;
        }

        @Override
        public boolean equals(Object obj) {
            Position p = (Position)obj;
            return x == p.x && y == p.y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}
