package org.snowflake.game;

import org.snowflake.layer.TiledLayer;

/**
 * Класс, в котором находятся константы времени выполнения.
 *
 * @author White Oak
 */
public class GameContext {

    private final TiledLayer layer;

    public GameContext(TiledLayer layer) {
        this.layer = layer;
    }

    public TiledLayer getLayer() {
        return layer;
    }

}
