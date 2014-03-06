package org.rising.controllers;

import java.awt.Point;
import java.awt.event.KeyEvent;
import org.rising.game.Camera;
import org.rising.game.Direction;
import org.rising.game.GameContext;
import org.rising.player.AbstractPlayer;
import org.rising.player.Player;
import org.rising.tiles.Tile;

/**
 *
 * @author Riseremi
 */
public class MoveController {

    public void processMovement(GameContext context, Player player, boolean[] keys) {
        final Camera camera = context.getCamera();

        if (keys[KeyEvent.VK_UP]) {
            if (player.getDirection() == Direction.UNDEFINED) {
//                TiledLayer layer = world.getLayer();
//                layer.setShift(Tile.HEIGHT - AbstractPlayer.STEP);
//                layer.setDestination(new Point(layer.getX(), layer.getY() + Tile.HEIGHT));
                player.setDestination(new Point(player.getX(), player.getY() - Tile.HEIGHT));

                camera.setDestination(new Point(camera.getX(), camera.getY() + Tile.HEIGHT));
//                player.setDestination(new Point(player.getX(), player.getY() - Tile.HEIGHT));
                player.getSprite().setShift(0);
                player.setDirection(Direction.UP);

                //Core.getInstance().getCamera().setX(0);
                //Core.getInstance().getCamera().setY(-16);
            }
        }

        if (keys[KeyEvent.VK_DOWN]) {
            if (player.getDirection() == Direction.UNDEFINED) {
//                TiledLayer layer = world.getLayer();
//                layer.setShift(0);
//                layer.setDestination(new Point(layer.getX(), layer.getY() - Tile.HEIGHT));

                player.setDestination(new Point(player.getX(), player.getY() + Tile.HEIGHT));
                camera.setDestination(new Point(camera.getX(), camera.getY() - Tile.HEIGHT));

                player.getSprite().setShift(Tile.WIDTH - AbstractPlayer.STEP);
                player.setDirection(Direction.DOWN);
            }
        }

        if (keys[KeyEvent.VK_LEFT]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.setDestination(new Point(player.getX() - Tile.WIDTH, player.getY()));
                camera.setDestination(new Point(camera.getX() + Tile.WIDTH, camera.getY()));

                player.getSprite().setShift(0);

//                player.setDirection(Direction.LEFT);
//                TiledLayer layer = world.getLayer();
//                layer.setShift(Tile.WIDTH - AbstractPlayer.STEP);
//                layer.setDestination(new Point(layer.getX() + Tile.WIDTH, layer.getY()));
                player.setDirection(Direction.LEFT);

//                System.out.println((player.getBlocksX() - layer.getBlocksX()) + ":" + (player.getBlocksY() - layer.getBlocksY()));
            }
        }

        if (keys[KeyEvent.VK_RIGHT]) {
            if (player.getDirection() == Direction.UNDEFINED) {
//                player.getSprite().setShift(Tile.WIDTH - Player.STEP);
                player.setDestination(new Point(player.getX() + Tile.WIDTH, player.getY()));
                camera.setDestination(new Point(camera.getX() - Tile.WIDTH, camera.getY()));

                player.getSprite().setShift(Tile.WIDTH - AbstractPlayer.STEP);
                player.setDirection(Direction.RIGHT);
//                TiledLayer layer = world.getLayer();
//                layer.setShift(0);
//                layer.setDestination(new Point(layer.getX() - Tile.WIDTH, layer.getY()));
//                player.setDirection(Direction.RIGHT);
            }
        }
        
        if(keys[KeyEvent.VK_D]) {
            context.getWorld().switchDebug();
            keys[KeyEvent.VK_D] = false;
        }
        
    }
}
