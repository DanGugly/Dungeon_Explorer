package com.dangugly.game.states;

import com.dangugly.game.Audio.Sound;
import com.dangugly.game.GamePanel;
import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;

import javax.imageio.ImageIO;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class IntroState extends GameState {

    AudioClip bgMusic = null;

    private BufferedImage logo;

    private int alpha;
    private int ticks;

    private final int FADE_IN = 95;
    private final int LENGTH = 95;
    private final int FADE_OUT = 95;

    public IntroState(GameStateManager gsm) {
        super(gsm);

        bgMusic = Sound.getClip("intro");
        bgMusic.play();
        init();
    }

    @Override
    public void update(double time) {
        ticks++;
        if(ticks < FADE_IN) {
            alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
            if(alpha < 0) alpha = 0;
        }
        if(ticks > FADE_IN + LENGTH) {
            alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
            if(alpha > 255) alpha = 255;
        }
        if(ticks > FADE_IN + LENGTH + FADE_OUT) {
            gsm.pop(GameStateManager.INTRO);
            gsm.add(GameStateManager.MENU);
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (key.enter.down){
            bgMusic.stop();
            gsm.pop(GameStateManager.INTRO);
            key.enter.toggle(false);
            gsm.add(GameStateManager.MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH +1280, GamePanel.height);
        g.drawImage(logo, 0, 0, GamePanel.WIDTH +1280 , GamePanel.height, null);
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, GamePanel.WIDTH+1280, GamePanel.height);
    }

    public void init() {
        ticks = 0;
        try {
            logo = ImageIO.read(new File("res/Logo/logo.gif"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}