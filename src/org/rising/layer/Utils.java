package org.rising.layer;

import org.rising.tiles.Tile;

/**
 *
 * @author Riseremi
 */
public class Utils {
    /////////////////////////////////////
    public static void drawRect(TiledLayer layer, Tile tile, int x, int y, int w, int h) {
        for (int i = x; i < x + w + 1; i++) {
            layer.getMap()[i][y] = tile.clone();
            layer.getMap()[i][y + h] = tile.clone();
        }
        for (int i = y; i < y + h + 1; i++) {
            layer.getMap()[x][i] = tile.clone();
            layer.getMap()[x + w][i] = tile.clone();
        }
    }

    public static void fillRect(TiledLayer layer, Tile tile, int x, int y, int w, int h) {
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                layer.getMap()[i][j] = tile.clone();
            }
        }
    }
    ///////////////////////////////////
}
