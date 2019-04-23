package com.dangugly.game.states;

import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;

import java.awt.*;

public class PauseState extends GameState {

    public PauseState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update() {
        //System.out.println("PAUSED");
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g) {

    }
}
