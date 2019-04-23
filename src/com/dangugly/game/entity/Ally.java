package com.dangugly.game.entity;

import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.AABB;
import com.dangugly.game.util.Camera;
import com.dangugly.game.util.Vector2f;

import java.awt.*;

public class Ally extends Entity {

    private AABB sense;
    private int r;
    private Camera cam;

    public Ally(Camera cam, Sprite sprite, Vector2f origin, int size){
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
    }

    public void move(Player player){
        if (sense.colCircleBox(player.getBounds())){
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
            up = down = left = right = false;
            dx =0;
            dy =0;
        }
    }

    private void destroy(){

    }

    public void update(Player player){
        //if(cam.getBoundsOnScreen().collides(this.bounds)){
        super.update();
        move(player);
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
            destroy();
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

        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
        // }
    }
}
