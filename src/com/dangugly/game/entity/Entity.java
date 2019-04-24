package com.dangugly.game.entity;

import com.dangugly.game.graphics.Animation;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected final int ATTACK = 5;
    protected final int UP = 3;
    protected final int DOWN = 2;
    protected final int RIGHT = 0;
    protected final int LEFT = 1;
    protected final int FALLEN = 4;
    protected int currentAnimation;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected boolean fallen;

    public boolean xCol = false;
    public boolean yCol = false;

    protected int attackSpeed = 500; // in milliseconds
    protected int attackDuration = 500; // in milliseconds
    protected double attacktime;
    protected boolean canAttack = true;
    protected boolean attacking = false;

    protected float dx;
    protected float dy;
    protected float maxSpeed = 4f;
    protected float acc = 3f;
    protected float deacc = 0.3f;

    protected AABB hitBounds;
    protected AABB bounds;

    public Entity(Sprite sprite, Vector2f origin, int size){
        this.sprite = sprite;
        pos = origin;
        this.size = size;

        bounds = new AABB(origin, size, size);
        hitBounds = new AABB(origin,size,size);

        hitBounds.setxOffset( size / 2);

        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);

    }

    public void setSprite(Sprite sprite){
        this.sprite=sprite;
    }
    public void setFallen(boolean b) {
        fallen = b;
    }
    public void setSize(int i){ size = i;}
    public void setMaxSpeed(float f){ maxSpeed = f;}
    public void setAcc(float f){ acc = f;}
    public void setDeacc(float f){ deacc = f;}

    public int getDirection() {
        if(currentAnimation == UP || currentAnimation == LEFT) {
            return -1;
        }
        return 1;
    }
    public AABB getBounds() {
        return bounds;
    }
    public float getDeacc(){
        return deacc;
    }
    public float getMaxSpeed(){
        return maxSpeed;
    }
    public float getDx(){
        return dx;
    }
    public float getDy(){
        return dy;
    }

    public int getSize(){ return size;}
    public Animation getAnimation(){ return ani;}

    public void setAnimation(int i, BufferedImage[] frames, int delay){
        currentAnimation = i;
        ani.setFrames(i, frames);
        ani.setDelay(delay);
    }

    public void animate() {
        if(attacking) {
            if(currentAnimation < 5) {
                setAnimation(currentAnimation + ATTACK, sprite.getSpriteArray(currentAnimation + ATTACK), attackDuration / 100);
            }
        } else if (up) {
            if ((currentAnimation != UP || ani.getDelay() == -1)) {
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        } else if (down) {
            if ((currentAnimation != DOWN || ani.getDelay() == -1)) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        } else if (left) {
            if ((currentAnimation != LEFT || ani.getDelay() == -1)) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        } else if (right) {
            if ((currentAnimation != RIGHT || ani.getDelay() == -1)) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        } else if (fallen) {
            if (currentAnimation != FALLEN || ani.getDelay() == -1) {
                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 15);
            }
        }
        else {
            if(!attacking && currentAnimation > 4) {
                setAnimation(currentAnimation - ATTACK, sprite.getSpriteArray(currentAnimation - ATTACK), -1);
            } else if(!attacking) {
                setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
            }
        }
    }

    private void setHitBoxDirection() {
        if (up && !attacking) {
            hitBounds.setxOffset((size - hitBounds.getWidth()) / 2);
            hitBounds.setyOffset(-hitBounds.getHeight() / 2 + hitBounds.getXOffset());
        } else if (down && !attacking) {
            hitBounds.setxOffset((size - hitBounds.getWidth()) / 2);
            hitBounds.setyOffset(hitBounds.getHeight() / 2 + hitBounds.getXOffset());
        } else if (left && !attacking) {
            hitBounds.setyOffset((size - hitBounds.getHeight()) / 2);
            hitBounds.setxOffset(-hitBounds.getWidth() / 2 + hitBounds.getYOffset());
        } else if (right && !attacking) {
            hitBounds.setyOffset((size - hitBounds.getHeight()) / 2);
            hitBounds.setxOffset(hitBounds.getWidth() / 2 + hitBounds.getYOffset());
        }
    }

    protected boolean isAttacking(double time) {

        if((attacktime / 1000000) > ((time / 1000000) - attackSpeed)) {
            canAttack = false;
        } else {
            canAttack = true;
        }

        if((attacktime / 1000000) + attackDuration > (time / 1000000)) {
            return true;
        }

        return false;
    }

    public void update() {
        animate();
        setHitBoxDirection();
        ani.update();
    }

    public abstract void render(Graphics2D g);
}
