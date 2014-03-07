package org.rising.game;

import org.rising.layer.TiledLayer;

/**
 * Класс, в котором находятся константы времени выполнения.
 *
 * @author White Oak
 */
public class GameContext {
    private static final long serialVersionUID = 1L;

    private final World world;
    private final Camera camera;

    public GameContext(World layer, Camera camera) {
        this.world = layer;
        this.camera = camera;
    }

    public TiledLayer getLayer() {
        return world.getLayer();
    }

    public Camera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }

}
