package org.rising.game;

import org.rising.layer.TiledLayer;

/**
 * Класс, в котором находятся константы времени выполнения.
 *
 * @author White Oak
 */
public class GameContext {

    private final World world;

    public GameContext(World layer) {
        this.world = layer;
    }

    public TiledLayer getLayer() {
        return world.getLayer();
    }

    public World getWorld() {
        return world;
    }

}
