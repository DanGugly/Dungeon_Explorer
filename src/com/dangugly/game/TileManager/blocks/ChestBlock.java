package com.dangugly.game.TileManager.blocks;

import com.dangugly.game.util.AABB;
import com.dangugly.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChestBlock extends Block{

    public boolean opened = false;

    public ChestBlock(BufferedImage img, Vector2f pos, int w, int h){
        super(img, pos, w, h);
    }

    public boolean update(AABB p) {
        return true;
    }
    public boolean isInside(AABB p){
        return false;
    }
    public void render(Graphics2D g){
        if(! opened){
            super.render(g);
        }

        g.setColor(Color.green); //draw green rect around holes
        // g.drawRect((int) pos.getWorldVar().x,(int) pos.getWorldVar().y, w, h);
    }
}
