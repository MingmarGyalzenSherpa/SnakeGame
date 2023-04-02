import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    boolean gameStarted = false;
    boolean gameOver = false;

    final int SCREEN_WIDTH = 500;
    final int SCREEN_HEIGHT = 500;
    final int UNIT_SIZE = 20;

    final int GAME_UNITS = (this.SCREEN_WIDTH * this.SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    final int DELAY = 75;
    char Direction = 'R';

    Random random = new Random();
    int appleX;
    int appleY;
    int x[] = new int[GAME_UNITS];
    int y[] = new int[GAME_UNITS];

    int score = 0;
    Timer timer;
    int bodyParts = 6;
    JLabel title = new JLabel("SNAKE GAME");
    JButton startBtn = new JButton("START");

    GamePanel() {
        this.setPreferredSize(new Dimension(this.SCREEN_WIDTH, this.SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startBtn.setBounds(200, 200, 100, 30);
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        startBtn.setForeground(Color.white);
        startBtn.setFocusable(false);
        startBtn.setBackground(Color.darkGray);
        startBtn.addActionListener(this);
        setLayout(null);
        add(startBtn);
        title.setBounds(150, 50, 300, 100);
        add(title);


    }

    void startGame() {

        this.gameStarted = true;
        title.setVisible(!gameStarted);
        startBtn.setVisible(!gameStarted);
        repaint();
//        generateBody();
        generateApple();
        timer = new Timer(DELAY, this);
        timer.start();

    }

    void generateApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        for(int i = 0; i < bodyParts ; i++)
        {
            if(appleX == x[i] && appleY == y[i])
            {
                generateApple();
            }
        }
    }

    void eatApple(){
        if(x[0] == appleX && y[0] == appleY)
        {
            bodyParts++;
            score += 5;
            generateApple();
        }
    }

    void move() {


        for (int i = this.bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (Direction) {
            case 'R':
                x[0] = x[0] + this.UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0] - this.UNIT_SIZE;
                break;

            case 'U':
                y[0] = y[0] - this.UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0] + this.UNIT_SIZE;
        }


    }


    void collisionDetection() {
        for(int i = 1; i < bodyParts; i++)
        {
            if(x[0] == x[i] && y[0] == y[i])
            {
                gameStarted = false;
                gameOver = true;
            }
        }

        if( (x[0] > SCREEN_WIDTH) || (x[0] < 0 ) || (y[0] > SCREEN_HEIGHT) || (y[0] < 0))
        {
            gameStarted = false;
            gameOver = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {


        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        if (gameStarted) {
//            for (int i = 0; i < this.SCREEN_HEIGHT / this.UNIT_SIZE; i++) {
//                g.drawLine(i * this.UNIT_SIZE, 0, i * this.UNIT_SIZE, this.SCREEN_HEIGHT);
//                g.drawLine(0, i * this.UNIT_SIZE, this.SCREEN_WIDTH, i * this.UNIT_SIZE);
//            }

            //apple
            g.setColor(Color.GREEN);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //snake body

            for (int i = 0; i < this.bodyParts; i++) {
                if(i == 0){
                    g.setColor(Color.DARK_GRAY);
                }else{
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            //score
            g.setFont(new Font("Times New Roman",Font.PLAIN,20));
            g.drawString("Score = " + score,SCREEN_WIDTH - (UNIT_SIZE * 5),30);

        }
        if(gameOver){
            g.setFont(new Font("Times New Roman",Font.PLAIN,50));
            g.drawString("GAME OVER",SCREEN_WIDTH/5, SCREEN_HEIGHT/3);
            g.drawString("Score = " + score,SCREEN_WIDTH/4,SCREEN_HEIGHT/2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startBtn) {
            startGame();
        }
        if(gameStarted)
        {
            move();
            collisionDetection();
            eatApple();

        }
        repaint();

    }

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            System.out.println(e.getKeyCode());
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (Direction != 'R') {
                        Direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (Direction != 'L') {
                        Direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (Direction != 'D') {
                        Direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (Direction != 'U') {
                        Direction = 'D';
                    }
                    break;
            }
        }
    }

}
