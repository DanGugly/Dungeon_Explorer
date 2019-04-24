package com.dangugly.game.states;

import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;

import java.awt.*;

public class GameOverState extends GameState {
    public GameOverState(GameStateManager gsm, boolean win) {
        super(gsm);
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (key.enter.down){
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

    }
}
