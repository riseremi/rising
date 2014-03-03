/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.snowflake.layer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Riseremi
 */
public class LayerIO {
    public static void loadFromFileVersion1(String fileName, TiledLayer layer) throws IOException {
        InputStream reader = LayerIO.class.getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(reader));

        //BufferedReader br = new BufferedReader(new FileReader(fileName));
        int[][] mapTemp = new int[20][20];
        for (int i = 0; i < 20; i++) {
            String line = br.readLine();
            String v[] = line.split(" ");
            for (int j = 0; j < 20; j++) {
                mapTemp[i][j] = Integer.parseInt(v[j]);
            }

            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    layer.setTile(x, y, mapTemp[x][y]);
                }
            }
        }
    }

}
