package com.dangugly.game.states;

import com.dangugly.game.Audio.Sound;
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
    public static final int INTRO = 4;

    public static int life = 0;
    public static int kills = 0;
    public static int saved = 0;
    public static int deaths = 0;

    public static Vector2f map;

    public int onTopState = 0;

    public static Font font;

    private Sound SoundManager;

    public GameStateManager(){
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);
        states = new GameState[5];

        SoundManager = new Sound();

        font = new Font("font/font.png", 10, 10);
        Sprite.currentFont = font;

        states[INTRO] = new IntroState(this);
        //states[PLAY] = new PlayState(this);
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
        if(state == INTRO){
            states[INTRO] = new IntroState(this);
        }
    }

    public void setScore(int k, int l, int s, int d){
        this.kills = k;
        this.life = l;
        this.saved = s;
        this.deaths = d;
    }

    public void add(int state, boolean end){
        if(state == GAMEOVER){
            states[GAMEOVER] = new GameOverState(this, end);
        }
    }

    public void addAndPop(int state){
        addAndPop(state,0);
    }

    private void addAndPop(int state, int remove) {
        pop(state);
        add(state);
    }

    public void update(double time){
        for(int i = 0; i < states.length; i++){
            if(states[i]!=null){
                states[i].update(time);
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
