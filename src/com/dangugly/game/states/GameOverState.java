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
import java.io.File;

public class GameOverState extends GameState {

    AudioClip end = null;

    private BufferedImage logo;
    private BufferedImage score;

    private int alpha;
    private int ticks;

    boolean won = false;

    private final int FADE_IN = 240;

    public GameOverState(GameStateManager gsm, boolean win) {
        super(gsm);

        won = win;
        if(win){
            end = Sound.getClip("win");
        }else {
            end = Sound.getClip("lose");
        }

        end.play();
        init();
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
            key.enter.toggle(false);
            gsm.add((GameStateManager.MENU));
        }

        if (key.escape.down){
            gsm.pop(GameStateManager.MENU);
            System.exit(0);
        }
    }

    @Override
    public void render(Graphics2D g) {

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH +1280, GamePanel.height);
        g.drawImage(logo, 0, 0, GamePanel.WIDTH +1280 , GamePanel.height, null);
        g.drawImage(score, GamePanel.WIDTH +340, GamePanel.height -450, GamePanel.WIDTH +580 , GamePanel.height -550, null);
        Sprite.drawArray(g, gsm.font, " GAME OVER", new Vector2f(480,55), 44, 24);
        if (won){
            Sprite.drawArray(g, gsm.font, "  YOU WON", new Vector2f(480,135), 44, 24);
        } else {
            Sprite.drawArray(g, gsm.font, " YOU LOST", new Vector2f(480,135), 44, 24);
        }
        Sprite.drawArray(g, gsm.font, "FINAL SCORE", new Vector2f(480,215), 42, 24);
        Sprite.drawArray(g, gsm.font, ""+gsm.life, new Vector2f(750,275), 48, 24);
        Sprite.drawArray(g, gsm.font, " "+gsm.kills , new Vector2f(750,315), 48, 24);
        Sprite.drawArray(g, gsm.font,"  "+gsm.saved , new Vector2f(750,355), 48, 24);
        Sprite.drawArray(g, gsm.font,"  "+gsm.deaths , new Vector2f(750,395), 48, 24);
        Sprite.drawArray(g, gsm.font,"PRESS ENTER FOR MENU AND ESC TO EXIT" , new Vector2f(230,480), 36, 24);
    }

    public void init() {
        ticks = 0;
        try {
            logo = ImageIO.read(new File("res/Logo/logo.gif"));
            score = ImageIO.read(new File("res/Logo/end_score.png"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
