package com.dangugly.game.graphics;

import com.dangugly.game.util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Font {

    private BufferedImage FONTSHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;
    public int w;
    public int h;
    private int wLetter;
    private int hLetter;

    public Font(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadFontArray();
    }

    public Font(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("Loading: " + file + "...");
        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadFontArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        w = i;
        wLetter = FONTSHEET.getWidth() / w;
    }

    public void setHeight(int i) {
        h = i;
        hLetter = FONTSHEET.getHeight() / h;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    private BufferedImage loadFont(String file) {
        BufferedImage sprite = null;
        try {
            System.out.println(file);
            sprite = ImageIO.read(new File(file));
        } catch (Exception e) {
            System.out.println("ERROR: Couldn't load file...");
        }
        return sprite;
    }

    public void loadFontArray() {
        spriteArray = new BufferedImage[wLetter][hLetter];

        for (int x = 0; x < wLetter; x++) {
            for (int y = 0; y < hLetter; y++) {
                spriteArray[x][y] = getLetter(x, y);
            }
        }
    }

    public BufferedImage getFontSheet() {
        return FONTSHEET;
    }

    public BufferedImage getLetter(int x, int y) {
        return FONTSHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage getFont(char letter) {
        int value = letter ;

        int x = value % wLetter;
        int y = value / wLetter;

        return getLetter(x, y);
    }

    public BufferedImage[] getSpriteArray(int i) {
        return spriteArray[i];
    }

    public BufferedImage[][] getSpriteArray2(int i) {
        return spriteArray;
    }
}