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
import org.rising.tiles.Tile;

/**
 *
 * @author Riseremi
 */
public class World {

    private static final long serialVersionUID = 1L;
    private TiledLayer layer;
    private int[][] nullLayer;
    private boolean debug;

    public World(BufferedImage image, int tileWidth, int tileHeight,
            int width, int height, int paintWidth, int paintHeight) {
        nullLayer = new int[width][height];
        layer = new TiledLayer(image, tileWidth, tileHeight, width, height, paintWidth, paintHeight);
        try {
            LayerIO.loadFromFileVersion1("/res/new_map.rsng", this);
        } catch (IOException ex) {
        }

    }

    public void switchDebug() {
        debug = !debug;;
    }

    public void paint(Graphics g) {
        layer.paint(g);

        if (debug) {
            g.setColor(Color.WHITE);
            for (int i = 0; i < nullLayer.length; i++) {
                for (int j = 0; j < nullLayer[0].length; j++) {
                    if (nullLayer[i][j] != -1) {
                        g.drawRect(i * Tile.WIDTH + layer.getX(), j * Tile.WIDTH + layer.getY(), Tile.WIDTH, Tile.WIDTH);
                    }

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
