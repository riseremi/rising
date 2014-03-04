package org.rising.player;

import java.awt.Point;
import org.rising.game.Core;
import org.rising.game.Direction;
import org.rising.game.World;

/**
 *
 * @author Riseremi
 */
public class PlayerAnimation implements Runnable {

    @Override
    public void run() {
        while (true) {
            World world = Core.getInstance().getWorld();
            Player player = Core.getInstance().getPlayer();
            Point destination = player.getDestination();

            if (player.getDirection() == Direction.RIGHT) {
                if (destination.getX() - player.getX() <= Player.STEP) {
                    player.setX(destination.x);
                    player.stopMoving();
                } else {
                    player.moveRight();
                }
            }
            if (player.getDirection() == Direction.LEFT) {
                if (player.getX() - destination.getX() <= Player.STEP) {
                    player.setX(destination.x);
                    player.stopMoving();
                } else {
                    player.moveLeft();
                }
            }
            if (player.getDirection() == Direction.UP) {
                if (player.getY() - destination.getY() <= Player.STEP) {
                    player.setY(destination.y);
                    player.stopMoving();
                } else {
                    player.moveUp();
                }
            }
            if (player.getDirection() == Direction.DOWN) {
                if (destination.getY() - player.getY() <= Player.STEP) {
                    player.setY(destination.y);
                    player.stopMoving();
                } else {
                    player.moveDown();
                }
            }
            try {
                Thread.sleep(25L);
            } catch (InterruptedException ex) {
            }
        }
    }

}
