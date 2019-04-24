package com.dangugly.game.states;

import com.dangugly.game.Audio.Sound;
import com.dangugly.game.GamePanel;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;
import com.dangugly.game.util.Vector2f;

import javax.imageio.ImageIO;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverState extends GameState {

    AudioClip end = null;

    private BufferedImage logo;

    private int alpha;
    private int ticks;

    private final int FADE_IN = 240;

    public GameOverState(GameStateManager gsm) {
        super(gsm);

        end = Sound.getClip("lose");
        end.play();
        init();
    }

    public GameOverState(GameStateManager gsm, boolean win) {
        super(gsm);

        end = Sound.getClip("win");
        end.play();
        init(win);
    }

    @Override
    public void update(double time) {
        ticks++;
        if(ticks < FADE_IN) {
            alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
            if(alpha < 0) alpha = 0;
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (key.enter.down){
            end.stop();
            gsm.pop(GameStateManager.GAMEOVER);
            gsm.add((GameStateManager.MENU));
        }

        if (key.escape.down){
            gsm.pop(GameStateManager.MENU);
            System.exit(0);
        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawArray(g, gsm.font, GamePanel.oldFrameCount+" FPS", new Vector2f(GamePanel.width - 152,10), 24, 24);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH +1280, GamePanel.height);
        g.drawImage(logo, 0, 0, GamePanel.WIDTH +1280 , GamePanel.height, null);
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, GamePanel.WIDTH+1280, GamePanel.height);
    }

    public void init(boolean win) {
        ticks = 0;
        try {
            logo = ImageIO.read(getClass().getResourceAsStream("/Logo/logo.gif"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
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
