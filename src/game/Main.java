package game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Michal on 2017-08-04.
 */
public class Main{

    public static void main(String[] args){
        JFrame theFrame = new JFrame("Block Invaders 1.0");
        theFrame.setContentPane(new GameBoard());
        theFrame.pack();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setResizable(false);
        theFrame.setPreferredSize(new Dimension(GameBoard.borderWidth,GameBoard.borderHeight));
        theFrame.setLocationRelativeTo(null);
        theFrame.setVisible(true);
    }
}
