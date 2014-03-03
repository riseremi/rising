package org.rising.controllers;

import java.awt.Point;
import java.awt.event.KeyEvent;
import org.rising.game.Direction;
import org.rising.player.Player;
import org.rising.tiles.Tile;

/**
 *
 * @author Riseremi
 */
public class MoveController {

    public void processMovement(Player player, boolean[] keys) {

        if (keys[KeyEvent.VK_UP]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.getSprite().setShift(0);
                player.setDestination(new Point(player.getX(), player.getY() - Tile.HEIGHT));
                player.setDirection(Direction.UP);
            }
        }

        if (keys[KeyEvent.VK_DOWN]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.getSprite().setShift(Tile.HEIGHT - Player.STEP);
                player.setDestination(new Point(player.getX(), player.getY() + Tile.HEIGHT));
                player.setDirection(Direction.DOWN);
            }
        }

        if (keys[KeyEvent.VK_LEFT]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.getSprite().setShift(0);
                player.setDestination(new Point(player.getX() - Tile.WIDTH, player.getY()));
                player.setDirection(Direction.LEFT);
            }
        }

        if (keys[KeyEvent.VK_RIGHT]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.getSprite().setShift(Tile.WIDTH - Player.STEP);
                player.setDestination(new Point(player.getX() + Tile.WIDTH, player.getY()));
                player.setDirection(Direction.RIGHT);
            }
        }
    }
}
