package com.dangugly.game.entity;

import com.dangugly.game.GamePanel;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.states.PlayState;
import com.dangugly.game.util.AABB;
import com.dangugly.game.util.KeyHandler;
import com.dangugly.game.util.MouseHandler;
import com.dangugly.game.util.Vector2f;

import java.awt.*;

public class Player extends Entity {

    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        acc = 2f;
        maxSpeed = 3f;
        bounds.setWidth(30); //Decrease hitbox of player, doesnt need to be too big
        bounds.setHeight(15);
        bounds.setxOffset(12);
        bounds.setyOffset(40);
        bounds.setE(this);
    }

    public void move(){
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
        System.out.println("Resetting player....");
        pos.x = GamePanel.width / 2 - 64 ;
        PlayState.map.x = 0;
        pos.y = GamePanel.height / 2 - 64;
        PlayState.map.y = 0;

        dx = 0;     //Dont keep velocity on respawn
        dy = 0;
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
    }
    public void update(){
        super.update();
        if(!fallen){
            move();
            if(!bounds.collisionTile(dx,0)){
                PlayState.map.x += dx;
                pos.x += dx;
            }
            if (!bounds.collisionTile(0, dy)){
                PlayState.map.y += dy;
                pos.y += dy;
            }
        } else {
            if(ani.hasPlayedOnce()){
                resetPosition();
                fallen = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.blue); //drawing player hitbox
        g.drawRect((int) (pos.getWorldVar().x +bounds.getXOffset()), (int) (pos.getWorldVar().y+bounds.getYOffset()),(int) bounds.getWidth(), (int) bounds.getHeight());
        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

    public void input(MouseHandler mouse, KeyHandler key){

        //if (mouse.getButton() == 1){
        //    System.out.println("Player: "+pos.x+", "+pos.y);
       // }

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
            if(key.attack.down){
                attack = true;
            } else {
                attack = false;
            }
        } else {
            up = false;
            down = false;
            right = false;
            left = false;
        }
    }
}