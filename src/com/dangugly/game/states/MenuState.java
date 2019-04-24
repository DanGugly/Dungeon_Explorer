package com.dangugly.game.states;

import com.dangugly.game.Audio.Sound;
import com.dangugly.game.GamePanel;
import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuState extends GameState {

    AudioClip bgMusic = null;

    private BufferedImage menu;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        try {
            menu = ImageIO.read(getClass().getResourceAsStream("/Logo/menu.png"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        bgMusic = Sound.getClip("introm");
        bgMusic.loop();
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

        if (key.enter.down){
            bgMusic.stop();
            gsm.add((GameStateManager.PLAY));
            gsm.pop(GameStateManager.MENU);
        }

        if (key.escape.down){
            gsm.pop(GameStateManager.MENU);
            System.exit(0);
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.drawImage(menu, 0, 0, GamePanel.WIDTH +1280 , GamePanel.height, null);
    }
}
