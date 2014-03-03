package org.snowflake.tiles;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 * @author Remi
 */
public class Tile {

    public static final int WIDTH = 16, HEIGHT = 16;
    public static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);
    protected boolean canWalk;
    protected int id;
    //
    public static Tile WALL = new Tile(27, false);
    public static Tile FLOOR = new Tile(5, true);
    public static Tile FLOWER = new Tile(38, true);
    public static Tile MINI_TREE = new Tile(28, false);
    public static ArrayList<Tile> tempTiles = new ArrayList<>();

    public Tile(int id, boolean canWalk) {
        this.id = id;
        this.canWalk = canWalk;
    }

    public static void init() {
        tempTiles.add(WALL.clone());
        tempTiles.add(FLOOR.clone());
        tempTiles.add(FLOWER.clone());
        tempTiles.add(MINI_TREE.clone());
    }

    //вызывается, когда герой наступает на тайл
    public void walkThrough() {
        System.out.println("default walked message");
    }

    public int getId() {
        return id;
    }

    public void setId(int tileId) {
        for (Tile tile : tempTiles) {
            if (tileId == tile.getId()) {
                this.canWalk = tile.canWalk;
            }
        }

        id = tileId;
    }

    public boolean canWalk() {
        return canWalk;
    }

    @Override
    public Tile clone() {
        return new Tile(id, canWalk);
    }
}
