package com.dangugly.game.entity;

import com.dangugly.game.graphics.Animation;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

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
    protected int attackSpeed;
    protected int attackDuration;

    protected float dx;
    protected float dy;
    protected float maxSpeed = 4f;
    protected float acc = 3f;
    protected float deacc = 0.3f;

    protected AABB hitBounds;
    protected AABB bounds;

    protected TileCollision tc;

    public Entity(Sprite sprite, Vector2f origin, int size){
        this.sprite = sprite;
        pos = origin;
        this.size = size;

        bounds = new AABB(origin, size, size);
        hitBounds = new AABB(new Vector2f(origin.x+(size/2),origin.y),size,size);

        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);

        tc = new TileCollision(this);
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

    public AABB getBounds() {
        return bounds;
    }

    public int getSize(){ return size;}
    public Animation getAnimation(){ return ani;}

    public void setAnimation(int i, BufferedImage[] frames, int delay){
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate(){
        if(up){
            if(currentAnimation != UP || ani.getDelay()==-1){ //Which row of spritesheet to play animation from
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        }
        else if(down){
            if(currentAnimation != DOWN || ani.getDelay()==-1){
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        }
        else if(left){
            if(currentAnimation != LEFT || ani.getDelay()==-1){
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        }
        else if(right){
            if(currentAnimation != RIGHT || ani.getDelay()==-1){
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        }else if(fallen){
            if(currentAnimation != FALLEN || ani.getDelay()==-1){
                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 15);
            }
        }
        else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }

    private void setHitBoxDirection(){
        if(up){
            hitBounds.setyOffset(-size/2);
            hitBounds.setxOffset(-size/2);
        }
        else if(down){
            hitBounds.setyOffset(size/2);
            hitBounds.setxOffset(-size/2);
        }
        else if(left){
            hitBounds.setyOffset(-size);
            hitBounds.setyOffset(0);
        }
        else if(right){
            hitBounds.setxOffset(0);
            hitBounds.setyOffset(0);
        }
    }
    public void update(){
        animate();
        setHitBoxDirection();
        ani.update();
    }

    public abstract void render(Graphics2D g);
    public void input(KeyHandler key, MouseHandler mouse){}
}
