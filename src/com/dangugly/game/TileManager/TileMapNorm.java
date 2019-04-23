package com.dangugly.game.TileManager;

import com.dangugly.game.TileManager.blocks.Block;
import com.dangugly.game.TileManager.blocks.NormBlock;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.AABB;
import com.dangugly.game.util.Vector2f;

import java.awt.*;

public class TileMapNorm extends TileMap{

    private Block[] blocks;
    private int tileWidth;
    private int tileHeight;

    private int height;

    public TileMapNorm(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns){
        blocks = new Block[width*height];
        this.tileHeight=tileHeight;
        this.tileWidth=tileWidth;

        this.height = height;

        String[] block = data.split(",");
        for (int i = 0; i< (width* height); i++){
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if (temp != 0){
                blocks[i] = new NormBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight);
            }
        }
    }

    public void render(Graphics2D g, AABB cam){
        int x = (int) ((cam.getPos().getCamVar().x) / tileWidth);
        int y = (int) ((cam.getPos().getCamVar().y) / tileHeight);
        for (int i = x; i < x + (cam.getWidth() / tileWidth); i++){
            for (int j = y; j < y + (cam.getHeight() / tileHeight); j++ ){
                if(blocks[ i + (j * height)] != null){
                    blocks[i + (j* height)].render(g);
                }
            }
        }
    }
}
