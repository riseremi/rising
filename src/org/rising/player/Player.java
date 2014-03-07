package org.rising.player;

import java.io.IOException;
import javax.imageio.ImageIO;
import org.rising.game.GameContext;
import org.rising.layer.Sprite;

/**
 *
 * @author Riseremi
 */
public class Player extends AbstractPlayer {

    private final static int[][] CONSEQUENCES = {
        {0, 0, 1, 1, 2, 2, 3, 3},
        {4, 4, 5, 5, 6, 6, 7, 7},
        {8, 8, 9, 9, 10, 10, 11, 11},
        {12, 12, 13, 13, 14, 14, 15, 15}};
    private static final long serialVersionUID = 1L;

    public Player(GameContext context, int hp, int maxHp, int minDamage, int maxDamage, int attack, String name, int speed) throws IOException {
        super(context, hp, maxHp, minDamage, maxDamage, attack, name, speed,
                new Sprite(ImageIO.read(AbstractPlayer.class.getResourceAsStream("/resources/hero.png")), AbstractPlayer.WIDTH, AbstractPlayer.HEIGHT),
                CONSEQUENCES);

        playerAnimation = new PlayerAnimation();
        new Thread(playerAnimation).start();
    }
}
