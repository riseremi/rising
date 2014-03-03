package org.snowflake.layer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Remi
 */
public class ItemIcons {

    private final int tileHeight;
    private final int tileWidth;
    private final BufferedImage[] tiles;

    public ItemIcons(final Dimension TILE_SIZE, final BufferedImage image) {
        this.tileWidth = (int) TILE_SIZE.getWidth();
        this.tileHeight = (int) TILE_SIZE.getHeight();
        tiles = chopImage(image);
    }

    private BufferedImage[] chopImage(final BufferedImage image) {
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

    public BufferedImage getImage(final int id) {
        if (!(id >= tiles.length)) {
            return tiles[id];
        } else {
            return null;
        }
    }

    public int getSize() {
        return tiles.length;
    }

}
