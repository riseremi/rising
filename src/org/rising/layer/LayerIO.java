package org.rising.layer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.rising.game.World;

/**
 *
 * @author Riseremi
 */
public class LayerIO {
    private static final int mapW = 40, mapH = 30;

    public static void loadFromFileVersion1(String fileName, World world) throws IOException {
        InputStream reader = LayerIO.class.getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(reader));
        TiledLayer layer = world.getLayer();

        int[][] mapTemp = new int[mapW][mapH];

        //layer 0
        for (int i = 0; i < mapH; i++) {
            String line = br.readLine();
            if (line.equals(".")) {
                break;
            }
            String v[] = line.split(" ");
            for (int j = 0; j < mapW; j++) {
                mapTemp[j][i] = Integer.parseInt(v[j]);
            }

            for (int x = 0; x < mapW; x++) {
                for (int y = 0; y < mapH; y++) {
                    world.getNullLayer()[x][y] = mapTemp[x][y];
                }
            }
        }

        System.out.println(world.getNullLayer()[0][0]);

        br.readLine();

        //layer 1
        for (int i = 0; i < mapH; i++) {
            String line = br.readLine();

            String v[] = line.split(" ");
            for (int j = 0; j < mapW; j++) {
                mapTemp[j][i] = Integer.parseInt(v[j]);
            }

            for (int x = 0; x < mapW; x++) {
                for (int y = 0; y < mapH; y++) {
                    layer.setTile(x, y, mapTemp[x][y]);
                }
            }
        }
    }
}
