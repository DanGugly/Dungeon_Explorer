package com.dangugly.game.states;

import com.dangugly.game.GamePanel;
import com.dangugly.game.TileManager.TileManager;
import com.dangugly.game.entity.Ally;
import com.dangugly.game.entity.Enemy;
import com.dangugly.game.entity.Player;
import com.dangugly.game.graphics.Font;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.*;

import java.awt.*;

public class PlayState extends GameState{

    private Font font;
    private Player player;
    private TileManager tm;
    private Enemy[] enemy;
    private Ally ally;
    private Camera cam;

    public static Vector2f map;

    public PlayState(GameStateManager gsm){
        super(gsm);

        map = new Vector2f();   //0,0 in player new vector
        Vector2f.setWorldVar(map.x,map.y);

        cam = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64 , GamePanel.height+64 ));

        tm = new TileManager("tile/tilemap.xml", cam);
        font = new Font("font/font.png", 10, 10);

        enemy = new Enemy[2];
        //Change size of enemy 48 / 48 w / h
        ally = new Ally(cam, new Sprite("entity/littlegirl.png", 48, 48), new Vector2f(0+(GamePanel.width /2) + 400, 0+(GamePanel.height/2)+ 400),48);

        enemy[1] = new Enemy(cam, new Sprite("entity/wizardPlayer.png", 64, 64), new Vector2f(0 + (GamePanel.width / 2) -100, 0 + (GamePanel.height / 2) -100), 64);

        player = new Player(new Sprite("entity/linkFormatted.png"), new Vector2f(0+(GamePanel.width /2) -64, 0+(GamePanel.height/2)-64),64);
        //player = new Player(new Sprite("entity/wizardPlayer.png", 64 ,64), new Vector2f(0+(GamePanel.width /2) -64, 0+(GamePanel.height/2)-64),64);

        cam.target(player);
    }

    public void update(double time){
        Vector2f.setWorldVar(map.x,map.y);
        if (!(gsm.getState(GameStateManager.PAUSE))){
            player.update(enemy[1], time);
            ally.update(player);
            enemy[1].update(player);
            cam.update();
        }
    }
    public void input(MouseHandler mouse, KeyHandler key){
        key.escape.tick();

        if (!(gsm.getState(GameStateManager.PAUSE))){
            player.input(mouse,key);
            cam.input(mouse,key);
        }

        if(key.escape.clicked){
            if(gsm.getState(GameStateManager.PAUSE)){
                gsm.pop(GameStateManager.PAUSE);
            }else {
                gsm.add(GameStateManager.PAUSE);
            }
        }
    }
    public void render(Graphics2D g){
        tm.render(g);
        if(gsm.getState(GameStateManager.PAUSE)){
            Sprite.drawArray(g, font, "PAUSED", new Vector2f(GamePanel.width /2 - 64,56 ), 38, 24);
        }
        Sprite.drawArray(g, font, GamePanel.oldFrameCount+" FPS", new Vector2f(GamePanel.width - 152,10), 24, 24);
        ally.render(g);
        enemy[1].render(g);
        player.render(g);
        cam.render(g);
    }
}
