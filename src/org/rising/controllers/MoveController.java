package org.rising.controllers;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import org.rising.framework.network.Client;
import org.rising.framework.network.messages.MessageSetPosition;
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
//        Client.getInstance().send(new MessageMove(e.getKeyCode()));

        //for (int i = 0; i < keys.length; i++) {
        //     if (keys[i]) {
        //     }
        // }
        final Camera camera = context.getCamera();

        if (keys[KeyEvent.VK_UP]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.setDestination(new Point(player.getX(), player.getY() - Tile.HEIGHT));
                camera.setDestination(new Point(camera.getX(), camera.getY() + Tile.HEIGHT));
                player.getSprite().setShift(0);
                player.setDirection(Direction.UP);
            }
        }

        if (keys[KeyEvent.VK_DOWN]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.setDestination(new Point(player.getX(), player.getY() + Tile.HEIGHT));
                camera.setDestination(new Point(camera.getX(), camera.getY() - Tile.HEIGHT));
                player.getSprite().setShift(Tile.WIDTH - AbstractPlayer.STEP);
                player.getSprite().setShift(Tile.WIDTH - AbstractPlayer.STEP);
                player.setDirection(Direction.DOWN);
            }
        }

        if (keys[KeyEvent.VK_LEFT]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.setDestination(new Point(player.getX() - Tile.WIDTH, player.getY()));
                camera.setDestination(new Point(camera.getX() + Tile.WIDTH, camera.getY()));
                player.getSprite().setShift(0);
                player.setDirection(Direction.LEFT);
            }
        }

        if (keys[KeyEvent.VK_RIGHT]) {
            if (player.getDirection() == Direction.UNDEFINED) {
                player.setDestination(new Point(player.getX() + Tile.WIDTH, player.getY()));
                camera.setDestination(new Point(camera.getX() - Tile.WIDTH, camera.getY()));
                player.getSprite().setShift(Tile.WIDTH - AbstractPlayer.STEP);
                player.setDirection(Direction.RIGHT);
            }
        }

        if (keys[KeyEvent.VK_D]) {
            context.getWorld().switchDebug();
            keys[KeyEvent.VK_D] = false;
            try {
                Client.getInstance().send(new MessageSetPosition(15, 15));
            } catch (IOException ex) {
            }
        }

    }
}
