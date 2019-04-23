package com.dangugly.game.TileManager;

import com.dangugly.game.util.AABB;

import java.awt.*;

public abstract class TileMap {
    public abstract void render(Graphics2D g, AABB cam);
}
