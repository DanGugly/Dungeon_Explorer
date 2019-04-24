package com.dangugly.game.states;

import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;

import java.awt.*;

public class MenuState extends GameState {
    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

        if (key.enter.down){
            gsm.add((GameStateManager.PLAY));
            gsm.pop(GameStateManager.MENU);
        }

    }

    @Override
    public void render(Graphics2D g) {

    }
}
