/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rising.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.rising.layer.LayerIO;
import org.rising.layer.TiledLayer;

/**
 *
 * @author Riseremi
 */
public class World {
    private TiledLayer layer;
    private int[][] nullLayer;

    public World(BufferedImage image, int tileWidth, int tileHeight,
            int width, int height, int paintWidth, int paintHeight) {
        nullLayer = new int[width][height];
        layer = new TiledLayer(image, tileWidth, tileHeight, width, height, paintWidth, paintHeight);
        try {
            LayerIO.loadFromFileVersion1("/res/map.rsng", this);
        } catch (IOException ex) {
        }

    }

    public void paint(Graphics g) {
//        for (int x = 0; x < 40; x++) {
//                for (int y = 0; y < 30; y++) {
//                    layer.setTile(x, y, nullLayer[x][y]);
//                }
//            }
        
        layer.paint(g);
        g.setColor(Color.WHITE);
        for (int i = 0; i < nullLayer.length; i++) {
            for (int j = 0; j < nullLayer[0].length; j++) {
                if (nullLayer[i][j] != -1) {
                    g.drawRect(i * 16, j * 16, 16, 16);
                }

            }
        }
    }

    public TiledLayer getLayer() {
        return layer;
    }

    public void setLayer(TiledLayer layer) {
        this.layer = layer;
    }

    public int[][] getNullLayer() {
        return nullLayer;
    }

    public void setNullLayer(int[][] nullLayer) {
        this.nullLayer = nullLayer;
    }

    public boolean canWalk(int x, int y) {
        return nullLayer[x][y] == -1;
    }

}
