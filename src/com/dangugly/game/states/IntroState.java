package com.dangugly.game.states;

import com.dangugly.game.GamePanel;
import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class IntroState extends GameState {

    private BufferedImage logo;

    private int alpha;
    private int ticks;

    private final int FADE_IN = 60;
    private final int LENGTH = 60;
    private final int FADE_OUT = 60;

    public IntroState(GameStateManager gsm) {
        super(gsm);
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
            gsm.add(GameStateManager.MENU);
            gsm.pop(GameStateManager.INTRO);
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (key.enter.down){
            ticks = FADE_IN + LENGTH + FADE_OUT +1;
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
            logo = ImageIO.read(getClass().getResourceAsStream("/Logo/logo.gif"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}