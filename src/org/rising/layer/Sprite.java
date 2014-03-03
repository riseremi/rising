/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rising.layer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author White Oak
 */
public class Sprite extends Layer {

    private final BufferedImage[] tiles;
    private int[] spriteQueueSteps;
    private int currentStep;

    public Sprite(BufferedImage image, int tileWidth, int tileHeight) {
        super(tileWidth, tileHeight);
        if (image.getWidth() / tileWidth * tileWidth != image.getWidth()
                || image.getHeight() / tileHeight * tileHeight != image.getHeight()) {
            throw new IllegalArgumentException();
        }
        tiles = chopImage(image);
        spriteQueueSteps = new int[tiles.length];
        for (int i = 0; i < spriteQueueSteps.length; i++) {
            spriteQueueSteps[i] = i;
        }
    }

    private BufferedImage[] chopImage(BufferedImage image) {
        int x = 0, y = 0;
        List<BufferedImage> list = new ArrayList<>();
        try {
            while (true) {
                while (true) {
                    BufferedImage subImage = image.getSubimage(x * getWidth(), y * getHeight(), getWidth(), getHeight());
                    list.add(subImage);
                    x++;
                    if ((x + 1) * getWidth() > image.getWidth()) {
                        x = 0;
                        break;
                    }
                }
                y++;
                if ((y + 1) * getHeight() > image.getHeight()) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(x);
            System.out.println(y);
        }
        return list.toArray(new BufferedImage[list.size()]);
    }

    @Override
    protected void paintLayer(Graphics g) {
        g.drawImage(tiles[spriteQueueSteps[currentStep]], 0, 0, null);
    }

    public void nextStep() {
        currentStep++;
        if (currentStep >= spriteQueueSteps.length) {
            currentStep = 0;
        }
    }

    public void previousStep() {
        currentStep--;
        if (currentStep <= 0) {
            currentStep = spriteQueueSteps.length - 1;
        }
    }

    public void setStep(int step) {
        currentStep = step % spriteQueueSteps.length;
    }

    public int[] getSpriteQueueSteps() {
        return spriteQueueSteps;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setSpriteQueueSteps(int[] spriteQueueSteps) {
        this.spriteQueueSteps = spriteQueueSteps;
    }
}
