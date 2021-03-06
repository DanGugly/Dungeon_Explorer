package com.dangugly.game.util;

import com.dangugly.game.TileManager.TileMapObj;
import com.dangugly.game.TileManager.blocks.Block;
import com.dangugly.game.TileManager.blocks.HoleBlock;
import com.dangugly.game.entity.Entity;

import javax.lang.model.type.NullType;

public class AABB {

    private Vector2f pos;
    private float xOffset;
    private float yOffset;
    private float w;
    private float h;
    private float r;
    private int size;
    private Entity e;

    public void setE(Entity e) {
        this.e = e;}

    public AABB(Vector2f pos, int w, int h){
        this.pos = pos;
        this.w = w;
        this.h = h;

        size = Math.max(w, h);
    }

    public AABB(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;

        size = r;
    }

    public AABB(Vector2f pos, int r, Entity e){
        this.pos = pos;
        this.r = r;
        this.e = e;

        size = r;
    }

    public Vector2f getPos() {return pos;}
    public float getRadius(){ return r;}
    public float getHeight(){return h;}

    public float getWidth() {
        return w;
    }

    public void setBox(Vector2f pos, int w, int h){
        this.pos = pos;
        this.w = w;
        this.h = h;

        size = Math.max(w, h);
    }

    public void setCircle(Vector2f pos, int r){
        this.pos = pos;
        this.r = r;

        size = r;
    }

    public void setWidth(float f){w=f;}
    public void setHeight(float f){h=f;}
    public void setxOffset(float f){xOffset=f;}
    public void setyOffset(float f){yOffset=f;}

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public boolean collides(AABB bBox){
        float ax = ((pos.getWorldVar().x+(xOffset))+(this.w/2));
        float ay = ((pos.getWorldVar().y+(yOffset))+(this.h/2));
        float bx = ((bBox.getPos().getWorldVar().x +(bBox.getWidth() / 2))+(w/2));
        float by = ((bBox.getPos().getWorldVar().y +(bBox.getHeight() / 2))+(h/2));

        if(Math.abs(ax - bx)<(this.w/2)+(bBox.getWidth() / 2)){
            if (Math.abs(ay - by) < (this.h / 2) + (bBox.getHeight() /2)){
                return true;
            }
        }
        return false;
    }

    public boolean colCircleBox(AABB aBox) {

        float dx = Math.max(aBox.getPos().getWorldVar().x + aBox.getXOffset(), Math.min(pos.getWorldVar().x + (r / 2), aBox.getPos().getWorldVar().x + aBox.getXOffset() + aBox.getWidth()));
        float dy = Math.max(aBox.getPos().getWorldVar().y + aBox.getYOffset(), Math.min(pos.getWorldVar().y + (r / 2), aBox.getPos().getWorldVar().y + aBox.getYOffset() + aBox.getHeight()));

        dx = pos.getWorldVar().x + (r / 2) - dx;
        dy = pos.getWorldVar().y + (r / 2) - dy;

        if(Math.sqrt(dx * dx + dy * dy) < r / 2) {
            return true;
        }

        return false;
    }

    public boolean collisionTile(float ax, float ay){
        for(int c = 0; c <4; c++){ //Loop through each corner of the file

            int xt = (int) ( (pos.x +ax) + (c%2) *w+xOffset)/64;
            int yt = (int) ( (pos.y +ay) + ((int) (c/2)) *h+yOffset)/64;

            if(TileMapObj.event_blocks[xt + (yt * TileMapObj.height)] instanceof Block){
                Block block = TileMapObj.event_blocks[xt + (yt * TileMapObj.height)]; //Fix player not falling from standing between holes
                if(block instanceof HoleBlock){
                    return collisionHole(ax, ay, xt, yt, block);
                }
                return block.update(this);
            }
        }
        return false;
    }

    private boolean collisionHole(float ax, float ay, float xt, float yt, Block block){
        int nextXt = (int) ((( (pos.x+ax) +xOffset) / 64) +w / 64); //Add to width and height of the block
        int nextYt = (int) ((( (pos.y+ax) +yOffset) / 64) +h / 64);

        if (block.isInside(e.getBounds())){ //Player in hole block
            e.setFallen(true);
            return block.update(this);
        }
        else {
            if((nextYt == yt +1 )|| (nextXt == xt +1 || nextYt == yt -1 )|| (nextXt == xt -1)){
                if(TileMapObj.event_blocks[nextXt + (nextYt * TileMapObj.height)] instanceof HoleBlock){
                    if (e.getBounds().getPos().x > block.getPos().x){ //player inbetween 2 hole blocks
                        e.setFallen(true);
                    }
                    return false;
                }
             }

             e.setFallen(false);
             return false;
        }
    }
}
