package com.dangugly.game.entity;

import com.dangugly.game.Audio.Sound;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.AABB;
import com.dangugly.game.util.Camera;
import com.dangugly.game.util.Vector2f;

import java.awt.*;

public class Enemy extends Entity {

    private AABB sense;
    private int r;
    private Camera cam;

    private int hits = 0;
    private boolean alive = true;

    public Enemy(Camera cam, Sprite sprite, Vector2f origin, int size){
        super(sprite, origin, size);

        this.cam = cam;
        acc = 1f;
        maxSpeed = 2f;
        r = 300;

        bounds.setWidth(42); //Decrease hitbox
        bounds.setHeight(20);
        bounds.setxOffset(12);
        bounds.setyOffset(40);

        sense = new AABB(new Vector2f(origin.x + size / 2 - r / 2, origin.y + size / 2 - r / 2), r);
        bounds.setE(this);

        ani.setNumFrames(4, UP);
        ani.setNumFrames(4, DOWN);
        ani.setNumFrames(4, ATTACK + RIGHT);
        ani.setNumFrames(4, ATTACK + LEFT);
        ani.setNumFrames(4, ATTACK + UP);
        ani.setNumFrames(4, ATTACK + DOWN);
    }

    public void setHits(){
        hits+=1;
        if (hits > 50){
            alive = false;
        }
    }

    public void addForce(float a, boolean vertical) {
        if(!vertical) {
            dx -= a;
        } else {
            dy -= a;
        }
    }

    public int getHits(){
        return this.hits;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean a){
        alive = a;
    }

    public void move(Player player){
        if (sense.colCircleBox(player.getBounds())){
            Sound.playClip("enemyattack");
            attack = true;
            attacking = true;
            if(pos.y > player.pos.y +1){
                up = true;
                down = false;
                dy -= acc;
                if(dy < -maxSpeed){
                    dy = -maxSpeed;
                }
            }
            else if(pos.y < player.pos.y -1){
                dy += acc;
                down = true;
                up = false;
                if(dy > maxSpeed){
                    dy = maxSpeed;
                }
            } else {
                up = false;
                down = false;
                dy = 0;
            }
            if(pos.x > player.pos.x +1){
                dx -= acc;
                left = true;
                right = false;
                if(dx < -maxSpeed){
                    dx = -maxSpeed;
                }
            } else if(pos.x < player.pos.x -1){
                dx += acc;
                right = true;
                left = false;
                if(dx > maxSpeed){
                    dx = maxSpeed;
                }
            } else {
                left =  false;
                right = false;
                dx = 0;
            }
        } else {
            attacking = up = down = left = right = false;
            dx =0;
            dy =0;
        }
    }

    private void destroy(){
        alive = false;
        Sound.playClip("enemyhit");
    }

    public void update(Player player){
        //if(cam.getBoundsOnScreen().collides(this.bounds)){
        if(alive){
            super.update();
            move(player);
            if(hitBounds.collides(player.getBounds())){
                Sound.playClip("playerhit");
                player.setHits();
                //System.out.println("Hit taken !");
            }
            if(!fallen){
                if(!bounds.collisionTile(dx, 0)){
                    sense.getPos().x += dx;
                    pos.x += dx;
                }
                if(!bounds.collisionTile(0, dy)){
                    sense.getPos().y += dy;
                    pos.y += dy;
                }
            } else {
                fallen = true;
                attack = false;
                player.setKills();
                animate();
                destroy();
            }
        }
        //}
    }

    @Override
    public void render(Graphics2D g) {
        //if(cam.getBoundsOnScreen().collides(this.bounds)){
            g.setColor(Color.green);
           // g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(),  (int) bounds.getHeight() );
            g.setColor(Color.blue);
            //g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r, r);

        if(alive){
            g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
        }
       // }
    }
}
