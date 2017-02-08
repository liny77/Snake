package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

/**
 * Created by Shower on 2017/1/23 0023.
 */
public class SnakeControl extends Observable implements KeyListener {
    public SnakeModel sm;
    public final int height = 300;
    public final int width = 200;
    public int score = 0;
    public boolean lose = false;
    public SnakeModel.Position food = new SnakeModel.Position(height / 2, width / 2);
    public Random random = new Random();
    public Thread game;

    public SnakeControl(SnakeModel sm) {
        this.sm = sm;
        initGame();
        generateFood();
    }

    public void initGame() {
        if (game == null) {
            game = new Thread(() -> {
                while (!isLose()) {
//                System.out.println(Arrays.toString(sm.body.toArray()));
                    SnakeModel.Position head = sm.moveForward(food);
                    System.out.println(head);
                    if (outOfBoundary(head)) break;
                    if (sm.eat) {
                        generateFood();
                        sm.eat = false;
                        score++;
                        if (score % 5 == 0 && sm.speed > 30) sm.speed -= 10;
                    }
                    setChanged();
                    notifyObservers(score);
                    try {
                        Thread.sleep(sm.speed);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                lose = true;
                System.out.println(sm.body.getLast().toString());
            });
        }
    }

    public void reset() {
        score = 0;
        lose = false;
        generateFood();
    }

    public void restart() {
        sm.reset();
        reset();
        run();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (sm.towards != SnakeModel.Direction.DOWN) sm.turn(SnakeModel.Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (sm.towards != SnakeModel.Direction.UP) sm.turn(SnakeModel.Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (sm.towards != SnakeModel.Direction.RIGHT) sm.turn(SnakeModel.Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (sm.towards != SnakeModel.Direction.LEFT) sm.turn(SnakeModel.Direction.RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void run() {
        game.run();
    }

    public boolean isLose() {
        SnakeModel.Position head = sm.body.getLast();
        for (int i = 0; i < sm.body.size() - 1; ++i) {
            if (sm.body.get(i).equals(head)) return true;
        }
        return false;
    }

    public boolean outOfBoundary(SnakeModel.Position head) {
        if (head.x < 0 || head.y < 0 || head.x > height || head.y > width) {
            return true;
        }
        return false;
    }

    public void generateFood() {
        do {
            food.x = random.nextInt(height / 10) * 10;
            food.y = random.nextInt(width / 10) * 10;
        } while (sm.body.contains(food));
        System.out.println(food.toString());
    }
}
