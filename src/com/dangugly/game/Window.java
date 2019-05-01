package com.dangugly.game;

import javax.swing.JFrame;
public class Window extends JFrame {

    public Window(){
        setTitle("Dungeon Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel(1280,720));
        setIgnoreRepaint(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}