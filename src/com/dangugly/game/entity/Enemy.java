package com.dangugly.game.entity;

import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.AABB;
import com.dangugly.game.util.Vector2f;

import java.awt.*;

public class Enemy extends Entity {

    private AABB sense;
    private int r;

    public Enemy(Sprite sprite, Vector2f origin, int size){
        super(sprite, origin, size);

        acc = 1f;
        maxSpeed = 2f;
        r = 135;

        bounds.setWidth(42); //Decrease hitbox
        bounds.setHeight(20);
        bounds.setxOffset(12);
        bounds.setyOffset(40);

        sense = new AABB(new Vector2f(origin.x - size / 2, origin.y - size / 2), r);
    }

    public void move(Player player){
        if (sense.colCircleBox(player.getBounds())){
            if(pos.y > player.pos.y +1){
                dy -= acc;

                up = true;
                if(dy < -maxSpeed){
                    dy = -maxSpeed;
                }
            } else {
                down = false;
                if(dy < 0){
                    dy += deacc;
                    if(dy > 0){
                        dy = 0;
                    }
                }
            }
            if(pos.y < player.pos.y -1){
                dy += acc;
                down = true;

                if(dy > maxSpeed){
                    dy = maxSpeed;
                }
            } else {
                up = false;
                if(dy > 0){
                    dy -= deacc;
                    if(dy < 0){
                        dy = 0;
                    }
                }
            }
            if(pos.x > player.pos.x +1){
                dx -= acc;
                left = true;

                if(dx < -maxSpeed){
                    dx = -maxSpeed;
                }
            } else {
                right = false;
                if(dx < 0){
                    dx += deacc;
                    left = false;
                    right = true;
                    if(dx > 0){
                        dx = 0;
                    }
                }
            }
            if(pos.x < player.pos.x -1){
                dx += acc;
                right = true;
                if(dx > maxSpeed){
                    dx = maxSpeed;
                }
            } else {
                left = false;
                if(dx > 0){
                    dx -= deacc;
                    if(dx < 0){
                        dx = 0;
                    }
                }
            }
        } else {
            up =false;
            down = false;
            left = false;
            right = false;
            dx = 0;
            dy = 0;
        }
    }

    public void update(Player player){
        super.update();
        move(player);
        if(!bounds.collisionTile(dx, 0)){
            sense.getPos().x += dx;
            pos.x += dx;
        }
        if(!bounds.collisionTile(0, dy)){
            sense.getPos().x += dy;
            pos.x += dy;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(),  (int) bounds.getHeight() );
        g.setColor(Color.blue);
        g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r, r);

        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
