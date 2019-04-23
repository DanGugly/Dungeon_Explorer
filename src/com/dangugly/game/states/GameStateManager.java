package com.dangugly.game.states;

import com.dangugly.game.GamePanel;
import com.dangugly.game.graphics.Font;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;
import com.dangugly.game.util.Vector2f;

import java.awt.*;

public class GameStateManager {

    private GameState states[];

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;

    public static Vector2f map;

    public int onTopState = 0;

    public static Font font;

    public GameStateManager(){
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);
        states = new GameState[4];

        font = new Font("font/font.png", 10, 10);
        Sprite.currentFont = font;

        states[PLAY] = new PlayState(this);
    }

    public boolean getState(int state){
        return states[state] !=null;
    }

    public void pop(int state){
        states[state]=null;
    }

    public void add(int state){
        if(states[state] !=null)
            return;
        if(state == PLAY){
            states[PLAY] = new PlayState(this);
        }
        if(state == MENU){
            states[MENU] = new MenuState(this);
        }
        if(state == PAUSE){
            states[PAUSE] = new PauseState(this);
        }
        if(state == GAMEOVER){
            states[GAMEOVER] = new GameOverState(this);
        }
    }

    public void addAndPop(int state){
        addAndPop(state,0);
    }

    private void addAndPop(int state, int remove) {
        pop(state);
        add(state);
    }

    public void update(){
        for(int i = 0; i < states.length; i++){
            if(states[i]!=null){
                states[i].update();
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key){
        for (int i = 0; i < states.length; i++){
            if(states[i]!=null){
                states[i].input(mouse, key);
            }
        }
    }

    public void render(Graphics2D g){
        for (int i = 0; i < states.length; i++){
            if(states[i]!=null){
                states[i].render(g);
            }
        }
    }
}
