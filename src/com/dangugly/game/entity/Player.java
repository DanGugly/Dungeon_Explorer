package com.dangugly.game.entity;

import com.dangugly.game.Audio.Sound;
import com.dangugly.game.GamePanel;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.states.PlayState;
import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;
import com.dangugly.game.util.Vector2f;

import java.awt.*;
import java.util.Random;

public class Player extends Entity {

    private int hits = 0;
    private boolean alive = true;

    protected float force = 25f;

    private int kills = 0;
    private int deaths =0;
    private int saved = 0;

    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        acc = 2f;
        maxSpeed = 3f;
        bounds.setWidth(30); //Decrease hitbox of player, doesnt need to be too big
        bounds.setHeight(15);
        bounds.setxOffset(12);
        bounds.setyOffset(40);
        bounds.setE(this);

        hitBounds.setWidth(48);
        hitBounds.setHeight(48);

    }

    public void setSaved() {
        Sound.playClip("opt");
        saved+=1;
    }

    public int getSaved() {
        return saved;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setKills(){
        kills +=1;
    }

    public int getKills() {
        return kills;
    }

    public void setHits(){
        hits+=1;
        if (hits > 250){
            resetPosition();
        }
    }

    public int getHits() {
        return hits;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean a){
        alive = a;
    }

    private void move(){
        if(up){
            dy -= acc;
            if(dy < -maxSpeed){
                dy = -maxSpeed;
            }
        } else {
            if(dy < 0){
                dy += deacc;
                if(dy > 0){
                    dy = 0;
                }
            }
        }
        if(down){
            dy += acc;
            if(dy > maxSpeed){
                dy = maxSpeed;
            }
        } else {
            if(dy > 0){
                dy -= deacc;
                if(dy < 0){
                    dy = 0;
                }
            }
        }
        if(left){
            dx -= acc;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        } else {
            if(dx < 0){
                dx += deacc;
                if(dx > 0){
                    dx = 0;
                }
            }
        }
        if(right){
            dx += acc;
            if(dx > maxSpeed){
                dx = maxSpeed;
            }
        } else {
            if(dx > 0){
                dx -= deacc;
                if(dx < 0){
                    dx = 0;
                }
            }
        }
    }

    private void resetPosition(){
        deaths+=1;
        Sound.playClip("playerdeath");
        System.out.println("Resetting player....");
        pos.x = GamePanel.width / 2 - 64 ;
        PlayState.map.x = 0;
        pos.y = GamePanel.height / 2 - 64;
        PlayState.map.y = 0;

        hits = 0;
        dx = 0;     //Dont keep velocity on respawn
        dy = 0;
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
    }
    public void update(Enemy[] enemy, double time){
        super.update();

        attacking = isAttacking(time);
        for (int x =0; x<13;x++){
            if(enemy[x].isAlive()&& attack && hitBounds.collides(enemy[x].getBounds())){
                Sound.playClip("enemyhit");
                enemy[x].setHits();
                if(enemy[x].getHits() > 50) kills+=1;
                //enemy.addForce(force * getDirection(), currentAnimation == UP || currentAnimation == DOWN);
                System.out.println("Enemy hit !");
            }
        }

        if(!fallen){
            move();
            if(!bounds.collisionTile(dx,0)){
               // PlayState.map.x += dx;        Our camera now controls display movement
                pos.x += dx;
                xCol = false;
            }else {
                xCol = true;
            }
            if (!bounds.collisionTile(0, dy)){
               // PlayState.map.y += dy;
                pos.y += dy;
                yCol = false;
            }else {
                yCol = true;
            }
        } else {
            xCol = true;
            yCol = true;
            if(ani.hasPlayedOnce()){
                animate();
                resetPosition();
                fallen = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        //g.setColor(Color.blue); //drawing player hitbox
        //g.drawRect((int) (pos.getWorldVar().x +bounds.getXOffset()), (int) (pos.getWorldVar().y+bounds.getYOffset()),(int) bounds.getWidth(), (int) bounds.getHeight());

        if(attack){
            g.setColor(Color.red);
           // g.drawRect((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()), (int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int) hitBounds.getWidth(), (int) hitBounds.getHeight());
        }

        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

    public void input(MouseHandler mouse, KeyHandler key){

        if (mouse.getButton() == 1){
            System.out.println("Player: "+pos.x+", "+pos.y);
        }

        if (!fallen){
            if(key.up.down){
                up = true;
            } else {
                up = false;
            }
            if(key.down.down){
                down = true;
            } else {
                down = false;
            }
            if(key.left.down){
                left = true;
            } else {
                left = false;
            }
            if(key.right.down){
                right = true;
            } else {
                right = false;
            }
            if(key.attack.down && canAttack) {
                Random rand = new Random();
                int n = rand.nextInt(3);
                n+=1;
                Sound.playClip("playersword");
                if( n == 1){
                    Sound.playClip("playershout1");
                }
                else if( n == 2){
                    Sound.playClip("playershout2");
                }
                else if( n == 3){
                    Sound.playClip("playershout3");
                }
                else{
                    Sound.playClip("playershout4");
                }
                attack = true;
                attacktime = System.nanoTime();
            } else {
                if(!attacking) {
                    attack = false;
                }
            }
            if(up && down){
                up = down = false;
            }
            if(right && left){
                left = right = false;
            }
        } else {
            up = false;
            down = false;
            right = false;
            left = false;
        }
    }
}