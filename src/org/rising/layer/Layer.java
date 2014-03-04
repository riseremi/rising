package org.rising.layer;

import java.awt.Graphics;
import org.rising.tiles.Tile;

/**
 *
 * @author Remi
 */
public abstract class Layer {

    private int x, y;
    private final int width, height;
    private int shift;

    public Layer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public final void paintComponent(Graphics g) {
        paintLayer(g);
    }

    public void paint(Graphics g) {
        g.translate(x, y);
        paintLayer(g);
        g.translate(-x, -y);
    }

    //for paint non-tile-size player with offset in the right place
    public void paint(Graphics g, int dx, int dy) {
        g.translate(x - dx, y - dy);
        paintLayer(g);
        g.translate(-(x - dx), -(y - dy));
    }

    protected abstract void paintLayer(Graphics g);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBlocksX() {
        return (x + shift) / Tile.WIDTH;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getBlocksY() {
        return (y + shift) / Tile.HEIGHT;
    }

    public void setBlocksX(int x) {
        this.x = x * Tile.WIDTH;
    }

    public void setBlocksY(int y) {
        this.y = y * Tile.HEIGHT;
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
