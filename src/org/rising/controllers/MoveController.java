package org.rising.controllers;

import java.awt.Point;
import java.awt.event.KeyEvent;
import org.rising.game.Direction;
import org.rising.game.World;
import org.rising.layer.TiledLayer;
import org.rising.player.Player;
import org.rising.tiles.Tile;

/**
 *
 * @author Riseremi
 */
public class MoveController {

    public void processMovement(Player player, World world, boolean[] keys) {

        if (keys[KeyEvent.VK_UP]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                TiledLayer layer = world.getLayer();
//                layer.setShift(Tile.HEIGHT - AbstractPlayer.STEP);
                layer.setDestination(new Point(layer.getX(), layer.getY() + Tile.HEIGHT));
                player.setDirection(Direction.UP);
//                player.setDestination(new Point(player.getX(), player.getY() - Tile.HEIGHT));
            }
        }

        if (keys[KeyEvent.VK_DOWN]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                TiledLayer layer = world.getLayer();
                layer.setShift(0);
                layer.setDestination(new Point(layer.getX(), layer.getY() - Tile.HEIGHT));
                player.setDirection(Direction.DOWN);

//                player.setDestination(new Point(player.getX(), player.getY() + Tile.HEIGHT));
            }
        }

        if (keys[KeyEvent.VK_LEFT]) {
            if (player.getDirection() == Direction.UNDEFINED) {
//                player.setDestination(new Point(player.getX() - Tile.WIDTH, player.getY()));
//                player.setDirection(Direction.LEFT);
                TiledLayer layer = world.getLayer();
//                layer.setShift(Tile.WIDTH - AbstractPlayer.STEP);
                layer.setDestination(new Point(layer.getX() + Tile.WIDTH, layer.getY()));
                player.setDirection(Direction.LEFT);

//                System.out.println((player.getBlocksX() - layer.getBlocksX()) + ":" + (player.getBlocksY() - layer.getBlocksY()));
            }
        }

        if (keys[KeyEvent.VK_RIGHT]) {
            if (player.getDirection() == Direction.UNDEFINED) {
//                player.getSprite().setShift(Tile.WIDTH - Player.STEP);
//                player.setDestination(new Point(player.getX() + Tile.WIDTH, player.getY()));
//                player.setDirection(Direction.RIGHT);
                TiledLayer layer = world.getLayer();
                layer.setShift(0);
                layer.setDestination(new Point(layer.getX() - Tile.WIDTH, layer.getY()));
                player.setDirection(Direction.RIGHT);
            }
        }
    }
}
