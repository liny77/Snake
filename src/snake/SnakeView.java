package snake;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Shower on 2017/1/23 0023.
 */
public class SnakeView extends JFrame {
    public MyCanvas canvas;
    public final int height;
    public final int width;
    public JLabel score;
    public final String s = "Score: ";
    public JPanel panel;

    public SnakeView(SnakeControl sc) throws InterruptedException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Snake");
        setVisible(true);
        addKeyListener(sc);
        height = sc.height;
        width = sc.width;

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setSize(width, height);

        canvas = new MyCanvas(sc, width, height);
        panel.add(canvas, BorderLayout.CENTER);

        score = new JLabel(s + canvas.sc.score);
        add(score, BorderLayout.NORTH);
        add(new JLabel("Control: W, A, S, D"), BorderLayout.SOUTH);

        add(panel);
        pack();
        setResizable(false);
        System.out.println(canvas.getSize().getHeight() + " " + canvas.getSize().getWidth());
    }

    public void run() {
        repaint();
        canvas.sc.run();
    }

    class MyCanvas extends Canvas implements Observer {
        public SnakeModel sm;
        public SnakeControl sc;
        private int width;
        private int height;
        private int side = 10;

        public MyCanvas(SnakeControl sc, int w, int h) {
            this.sc = sc;
            this.sm = sc.sm;
            width = w;
            height = h;
            setSize(width, height);
            setBackground(Color.BLACK);
        }

        @Override
        public void update(Observable o, Object arg) {
            repaint();
            int n = (int)arg;
            score.setText(s + n);
        }

        @Override
        public void update(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width + 10, height + 10);
            g.setColor(Color.WHITE);
            SnakeModel.Position p;
            for (int i = 0; i < sm.body.size() - 1; ++i) {
                p = sm.body.get(i);
                g.fillRect(p.y, p.x, side, side);
            }
            g.setColor(Color.GRAY);
            g.fillRect(sm.body.getLast().y, sm.body.getLast().x, side, side);

            g.setColor(Color.WHITE);
            g.fillRect(sc.food.y, sc.food.x, side, side);
        }
    }
}

