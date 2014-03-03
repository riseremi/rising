package org.snowflake.tiles;

/**
 *
 * @author Riseremi
 */
public class Door extends Tile {

    public Door(int id, boolean canWalk) {
        super(id, canWalk);
    }

    public void switchState() {
        canWalk = !canWalk;
    }
}
