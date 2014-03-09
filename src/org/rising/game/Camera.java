package org.rising.game;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Riseremi
 */
public class Camera {

    private static final long serialVersionUID = 1L;
    private int x, y;
    private Point destination1;

    public Point getDestination() {
        return destination1;
    }

    public void setDestination(Point destination) {
        this.destination1 = destination;
    }

    public void moveCamera(Graphics g) {
        g.translate(x, y);
    }

    public void unmoveCamera(Graphics g) {
        g.translate(-x, -y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
