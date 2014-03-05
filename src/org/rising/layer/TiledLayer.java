package org.rising.layer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.rising.tiles.Tile;

/**
 * @author LPzhelud use of this class approved by the author 09.11.2012 - 9:15
 * AM
 */
public class TiledLayer extends Layer {

    private final Tile[][] map;//[x][y]
    private final BufferedImage[] tiles;
    private final int tileWidth, tileHeight;
    private final int horizontalTilesNumber, verticalTilesNumber;
    private final int paintWidth, paintHeight;
    private Point destination;
//    private int shift;

    public TiledLayer(BufferedImage image, int tileWidth, int tileHeight,
            int width, int height, int paintWidth, int paintHeight) {
        super(width * tileWidth, height * tileHeight);
        if (image.getWidth() / tileWidth * tileWidth != image.getWidth()
                || image.getHeight() / tileHeight * tileHeight != image.getHeight()) {
            throw new IllegalArgumentException();
        }
        this.paintWidth = paintWidth;
        this.paintHeight = paintHeight;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        tiles = chopImage(image);
        map = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = new Tile(0, true);
            }
        }

        horizontalTilesNumber = width;
        verticalTilesNumber = height;
        destination = new Point(0, 0);
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public int getHorizontalTilesNumber() {
        return horizontalTilesNumber;
    }

    public int getVerticalTilesNumber() {
        return verticalTilesNumber;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public Tile[][] getMap() {
        return map;
    }

    private BufferedImage[] chopImage(BufferedImage image) {
        int x = 0, y = 0;
        List<BufferedImage> list = new ArrayList<>();
        try {
            while (true) {
                while (true) {
                    BufferedImage subImage = image.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                    list.add(subImage);
                    x++;
                    if ((x + 1) * tileWidth > image.getWidth()) {
                        x = 0;
                        break;
                    }
                }
                y++;
                if ((y + 1) * tileHeight > image.getHeight()) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(x);
            System.out.println(y);
        }
        return list.toArray(new BufferedImage[list.size()]);
    }

    public void setTileObject(int x, int y, Tile tile) {
        map[x][y] = tile;
    }

    public void setTile(int x, int y, int tileId) {
        map[x][y].setId(tileId);
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    public Tile getTileObject(int x, int y) {
        return map[x][y];
    }

    public void fillRectTile(int x, int y, int w, int h, int tileId) {
        for (int i = y; i < y + h; i++) {
            for (int j = x; j < w + x; j++) {
                setTile(j, i, tileId);
            }
        }
    }

    //отрисовка слоя, при этом рисуются только помещающиеся на экран тайлы
    @Override
    protected void paintLayer(Graphics g) {
        int xRemainder = (getX() - getBlocksX() * Tile.WIDTH) % Tile.WIDTH;
        int yRemainder = (getY() - getBlocksY() * Tile.HEIGHT) % Tile.HEIGHT;

        for (int i = 0; i < paintWidth; i++) {
            for (int j = 0; j < paintHeight; j++) {
//                paintTile(g, i * tileWidth, j * tileHeight, map[i - (getBlocksX())][j - (getBlocksY())].getId());
                //допустим, карта в -1; 0
                //тогда нужно рисовать с тайла 1; 0
                int tileX = (i - getBlocksX() >= map.length) ? map.length - 1 : i - getBlocksX();
                int tileY = 0;

                int x = i - super.getBlocksX(), y = j - super.getBlocksY();

                if ((x >= 0) && (y >= 0) && (x < horizontalTilesNumber) && (y < verticalTilesNumber)) {
                    paintTile(g, i * tileWidth, j * tileHeight, map[i - (getBlocksX())][j - (getBlocksY())].getId(), xRemainder, yRemainder);
                }
            }
        }
    }

    protected void paintTile(Graphics g, int x, int y, int id, int offX, int offY) {
//        g.drawImage(tiles[Math.abs(id)], x + (getX() % Tile.WIDTH), y + (getY() % Tile.HEIGHT), null);

        g.drawImage(tiles[Math.abs(id)], x + offX, y + offY, null);
//        g.drawImage(tiles[Math.abs(id)], x, y, null);
    }

//    public void moveLeft() {
//        setX(getX() - AbstractPlayer.STEP);
//    }
//
//    public void moveRight() {
//        setX(getX() + AbstractPlayer.STEP);
//    }
//
//    public void moveUp() {
//        setY(getY() - AbstractPlayer.STEP);
//    }
//
//    public void moveDown() {
//        setY(getY() + AbstractPlayer.STEP);
//    }
}
