package com.dangugly.game.states;

import com.dangugly.game.Audio.Sound;
import com.dangugly.game.GamePanel;
import com.dangugly.game.TileManager.TileManager;
import com.dangugly.game.entity.Ally;
import com.dangugly.game.entity.Enemy;
import com.dangugly.game.entity.Player;
import com.dangugly.game.graphics.Font;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.*;

import javax.imageio.ImageIO;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlayState extends GameState{

    private Font font;
    private Player player;
    private TileManager tm;
    private Enemy[] enemy;
    private Ally[] ally;
    private Camera cam;

    AudioClip bgMusic = null;

    private BufferedImage hp;
    private BufferedImage save;
    private BufferedImage kill;
    private BufferedImage death;

    private static final String ALLY = "res/entity/littlegirl.png";
    private static final String ENEMY = "res/entity/wizardPlayer.png";

    public static Vector2f map;

    public PlayState(GameStateManager gsm)  {
        super(gsm);

        map = new Vector2f();   //0,0 in player new vector
        Vector2f.setWorldVar(map.x,map.y);

        cam = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64 , GamePanel.height+64 ));

        try {
            hp = ImageIO.read(new File("res/hud/life_icon.png"));
            save = ImageIO.read(new File("res/hud/ally_saved_icon.png"));
            kill = ImageIO.read(new File("res/hud/kills_icon.png"));
            death = ImageIO.read(new File("res/hud/deaths_icon.png"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        tm = new TileManager("res/tile/tilemap.xml", cam);
        font = new Font("res/font/font.png", 10, 10);

        /*
        File file = new File("file.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                integers.add(scanner.nextInt());
            }
            else {
                scanner.next();
            }
        } */
        bgMusic = Sound.getClip("background");
        bgMusic.loop();

        ally = new Ally[4];
        enemy = new Enemy[13];

        int x =0, y = 0;

        File file = new File ("res/tile/allyspawns.txt");
        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < 4; i++){
                if(scanner.hasNext()){
                    x = scanner.nextInt();
                    if(scanner.hasNextInt()){
                        y  = scanner.nextInt();
                    }
                }
                ally[i] = new Ally(cam, new Sprite(ALLY, 48, 48), new Vector2f(x, y),48);
            }

            file = new File("res/tile/enemyspawns.txt");
            scanner = new Scanner(file);
            for (int i = 0; i < 13; i++){
                if(scanner.hasNext()){
                    x = scanner.nextInt();
                    if(scanner.hasNextInt()){
                        y  = scanner.nextInt();
                    }
                }
                enemy[i] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(x, y), 64);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*//Change size of enemy 48 / 48 w / h
        ally[0] = new Ally(cam, new Sprite(ALLY, 48, 48), new Vector2f(1632, 216),48);
        ally[1] = new Ally(cam, new Sprite(ALLY, 48, 48), new Vector2f(2781, 2378),48);
        ally[2] = new Ally(cam, new Sprite(ALLY, 48, 48), new Vector2f(270, 2793),48);
        ally[3] = new Ally(cam, new Sprite(ALLY, 48, 48), new Vector2f(0 + (GamePanel.width / 2) +400, 0 + (GamePanel.height / 2) -50),48);
        */

        //enemy[1] = new Enemy(cam, new Sprite("entity/wizardPlayer.png", 64, 64), new Vector2f(0 + (GamePanel.width / 2) +400, 0 + (GamePanel.height / 2) -100), 64);
        /*enemy[0] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(1606, 390), 64);
        enemy[1] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(1688, 384), 64);
        enemy[2] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(1635, 1162), 64);
        enemy[3] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(1443, 1706), 64);
        enemy[4] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(1106, 2265), 64);
        enemy[5] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(1124, 2704), 64);
        enemy[6] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(2050, 2701), 64);
        enemy[7] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(2384, 1399), 64);
        enemy[8] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(2739, 1421), 64);
        enemy[9] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(2781, 2198), 64);
        enemy[10] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(2377, 801), 64);
        enemy[11] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(2656, 801), 64);
        enemy[12] = new Enemy(cam, new Sprite(ENEMY, 64, 64), new Vector2f(381, 2621), 64); */

        player = new Player(new Sprite("res/entity/linkFormatted.png"), new Vector2f(0+(GamePanel.width /2) -64, 0+(GamePanel.height/2)-64),64);
        //player = new Player(new Sprite("entity/wizardPlayer.png", 64 ,64), new Vector2f(0+(GamePanel.width /2) -64, 0+(GamePanel.height/2)-64),64);

        cam.target(player);
    }

    public void update(double time){
        Vector2f.setWorldVar(map.x,map.y);
        if (!(gsm.getState(GameStateManager.PAUSE))){
            player.update(enemy, time);
            for(int x =0; x < 4; x++){
                ally[x].update(player);
            }
            for(int x =0; x < 13; x++){
                enemy[x].update(player);
            }
            cam.update();
            if(player.getDeaths() < 3 && player.getSaved() > 3 ){   //Win  condition
                gsm.setScore(player.getKills(),250-player.getHits(), player.getSaved(),player.getDeaths());
                bgMusic.stop();
                gsm.add(GameStateManager.GAMEOVER, true);
                gsm.pop(GameStateManager.PLAY);
            } else if (player.getDeaths() >= 3){         //Lose condition
                gsm.setScore(player.getKills(),250-player.getHits(), player.getSaved(),player.getDeaths());
                bgMusic.stop();
                gsm.add(GameStateManager.GAMEOVER, false);
                gsm.pop(GameStateManager.PLAY);
            }
        }
    }
    public void input(MouseHandler mouse, KeyHandler key){
        key.escape.tick();
        key.enter.tick();
        if(key.enter.clicked&&(gsm.getState(GameStateManager.PAUSE))){
            gsm.pop(GameStateManager.PAUSE);
            gsm.pop(GameStateManager.PLAY);
            key.enter.toggle(false);
            gsm.add(GameStateManager.MENU);
        }

        if (!(gsm.getState(GameStateManager.PAUSE))){
            player.input(mouse,key);
            cam.input(mouse,key);
        }

        if(key.escape.clicked){
            if(gsm.getState(GameStateManager.PAUSE)){
                gsm.pop(GameStateManager.PAUSE);
                bgMusic.loop();
            }else {
                gsm.add(GameStateManager.PAUSE);
                bgMusic.stop();
            }
        }


    }
    public void render(Graphics2D g){
        tm.render(g);
        /*Sprite.drawArray(g, font, "PRESS ENTER TO START", new Vector2f(GamePanel.width /2 - 260,150 ), 38, 24);
        Sprite.drawArray(g, font, "PRESS ESCAPE TO QUIT", new Vector2f(GamePanel.width /2 - 260,200 ), 38, 24);
        Sprite.drawArray(g, font, "CONTROLS:", new Vector2f(GamePanel.width /2 - 260,350 ), 38, 24);
        Sprite.drawArray(g, font, "W A S D TO MOVE, SHIFT TO RUN AND", new Vector2f(GamePanel.width /2 - 200,400 ), 38, 24);
        Sprite.drawArray(g, font, "SPACE TO ATTACK, ESC TO PAUSE", new Vector2f(GamePanel.width /2 - 200,450 ), 38, 24);
        Sprite.drawArray(g, font, "OBJECTIVE:", new Vector2f(GamePanel.width /2 - 260,500 ), 38, 24);
        Sprite.drawArray(g, font, "DEFEAT ENEMIES & SAVE ALLIES", new Vector2f(GamePanel.width /2 - 200,550 ), 38, 24);*/
        Sprite.drawArray(g, font, GamePanel.oldFrameCount+" FPS", new Vector2f(GamePanel.width - 152,10), 24, 24);
        g.drawImage(hp, 200, 9, 24 , 24, null);
        Sprite.drawArray(g, font, "Life:"+(250-player.getHits()), new Vector2f(10,10), 24, 24);
        g.drawImage(kill, 200, 32, 24 , 24, null);
        Sprite.drawArray(g, font,"Kills:"+player.getKills() , new Vector2f(10,35), 24, 24);
        g.drawImage(save, 200, 58, 24 , 24, null);
        Sprite.drawArray(g, font,"Saved:"+player.getSaved() , new Vector2f(10,60), 24, 24);
        g.drawImage(death, 200, 85, 24 , 24, null);
        Sprite.drawArray(g, font,"Deaths:"+player.getDeaths() , new Vector2f(10,85), 24, 24);
        for(int x =0; x < 4; x++){
            ally[x].render(g);
        }
        for(int x =0; x < 13; x++){
            enemy[x].render(g);
        }
        player.render(g);
        cam.render(g);
        if(gsm.getState(GameStateManager.PAUSE)){
            Sprite.drawArray(g, font, "PAUSED", new Vector2f(GamePanel.width /2 - 64,56 ), 38, 24);
            Sprite.drawArray(g, font, "PRESS ESCAPE TO UNPAUSE", new Vector2f(GamePanel.width /2 - 260,200 ), 38, 24);
            Sprite.drawArray(g, font, "PRESS ENTER TO RETURN TO MENU", new Vector2f(GamePanel.width /2 - 260,350 ), 38, 24);
        }
    }
}
