package com.dangugly.game.TileManager;

import com.dangugly.game.TileManager.blocks.*;
import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.AABB;
import com.dangugly.game.util.Vector2f;

import java.awt.*;
import java.util.HashMap;


public class TileMapObj extends TileMap {
    public static Block[] event_blocks;

    private int tileWidth, tileHeight;

    public static int width, height;

    public TileMapObj(String data, Sprite sprite, int width, int height, int tilewidth, int tileHeight, int tileColumns){

        Block tempBlock;
        event_blocks = new Block[width*height];

        this.tileHeight = tileHeight;
        this.tileWidth = tilewidth;

        TileMapObj.width = width;
        TileMapObj.height = height;

        String[] block = data.split(",");
        for (int i = 0; i< (width* height); i++){
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if(temp!=0){
                if (temp == 172){
                    tempBlock = new HoleBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tilewidth, (int) (i / height) * tileHeight), tilewidth, tileHeight);
                }
                else if( temp == 254 ){
                    tempBlock = new ChestBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tilewidth, (int) (i / height) * tileHeight), tilewidth, tileHeight);
                }
                else {
                    tempBlock = new ObjBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tilewidth, (int) (i / height) * tileHeight), tilewidth, tileHeight);
                }
                event_blocks[i] = tempBlock;
            }
        }
    }

    public void render(Graphics2D g, AABB cam){
        int x = (int) ((cam.getPos().getCamVar().x) / tileWidth);
        int y = (int) ((cam.getPos().getCamVar().y) / tileHeight);
        for (int i = x; i < x + (cam.getWidth() / tileWidth); i++){
            for (int j = y; j < y + (cam.getHeight() / tileHeight); j++ ){
                if(event_blocks[ i + (j * height)] != null){
                    event_blocks[i + (j* height)].render(g);
                }
            }
        }
    }
}
