import javax.swing.*;
import java.awt.*;

public class GameFrame  extends JFrame {

    GameFrame(){
//        GamePanel panel = new GamePanel();
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);

    }

}
