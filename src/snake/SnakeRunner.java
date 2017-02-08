package snake;

/**
 * Created by Shower on 2017/1/23 0023.
 */
public class SnakeRunner {
    public static void main(String[] args) throws InterruptedException {
        SnakeModel sm = new SnakeModel();
        SnakeControl sc = new SnakeControl(sm);
        SnakeView sv = new SnakeView(sc);
        sc.addObserver(sv.canvas);

        sv.run();
    }
}
