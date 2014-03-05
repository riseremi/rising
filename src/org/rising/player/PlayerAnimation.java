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
            Point destination2 = world.getLayer().getDestination();

            if (player.getDirection() == Direction.RIGHT) {
//                if (destination.getX() - player.getX() <= Player.STEP) {
//                    player.setX(destination.x);
//                    player.stopMoving();
//                } else {
//                    player.moveRight();
//                }
                if (Math.abs(Math.abs(destination2.getX()) - Math.abs(world.getLayer().getX())) <= Player.STEP) {
                    world.getLayer().setX(destination2.x);
                    player.stopMoving();
                } else {
                    player.right();
                    world.getLayer().moveLeft();
                }
            }
            if (player.getDirection() == Direction.LEFT) {
//                if (player.getX() - destination.getX() <= Player.STEP) {
//                    player.setX(destination.x);
//                    player.stopMoving();
//                } else {
//                    player.moveLeft();
//                }
                if (Math.abs(Math.abs(destination2.getX()) - Math.abs(world.getLayer().getX())) <= Player.STEP) {
                    world.getLayer().setX(destination2.x);
                    player.stopMoving();
                } else {
                    player.left();
                    world.getLayer().moveRight();
                }
            }
            if (player.getDirection() == Direction.UP) {
//                if (player.getY() - destination.getY() <= Player.STEP) {
//                    player.setY(destination.y);
//                    player.stopMoving();
//                } else {
//                    player.moveUp();
//                }
                if (Math.abs(Math.abs(destination2.getY()) - Math.abs(world.getLayer().getY())) <= Player.STEP) {
                    world.getLayer().setY(destination2.y);
                    player.stopMoving();
                } else {
                    player.up();
                    world.getLayer().moveDown();
                }
            }
            if (player.getDirection() == Direction.DOWN) {
//                if (destination.getY() - player.getY() <= Player.STEP) {
//                    player.setY(destination.y);
//                    player.stopMoving();
//                } else {
//                    player.moveDown();
//                }
                if (Math.abs(Math.abs(destination2.getY()) - Math.abs(world.getLayer().getY())) <= Player.STEP) {
                    world.getLayer().setY(destination2.y);
                    player.stopMoving();
                } else {
                    player.down();
                    world.getLayer().moveUp();
                }
            }
            try {
                Thread.sleep(25L);
            } catch (InterruptedException ex) {
            }
        }
    }

}
