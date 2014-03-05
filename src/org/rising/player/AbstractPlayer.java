package org.rising.player;

import java.awt.Graphics;
import java.awt.Point;
import org.rising.game.Direction;
import org.rising.game.GameContext;
import org.rising.layer.Sprite;
import org.rising.layer.TiledLayer;
import org.rising.tiles.Tile;

/**
 *
 * @author Riseremi
 */
public abstract class AbstractPlayer {

    protected int hp, maxHp;
    protected int minDamage, maxDamage;
    protected int attack;
    protected String name;
    protected int speed;
    private GameContext context;
    public final static int STEP = 2;
    private final Sprite sprite;
    private final static int UP_CONSEQUENCE = 3, DOWN_CONSEQUENCE = 1, LEFT_CONSEQUENCE = 2, RIGHT_CONSEQUENCE = 0;
    private final int[][] consequences;
    public static final int WIDTH = 20, HEIGHT = 28;
    private volatile Point destination = new Point();
    private Direction direction = Direction.UNDEFINED;
    protected PlayerAnimation playerAnimation;

    public void stopMoving() {
        resetSequence();
        direction = Direction.UNDEFINED;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getDestination() {
        return destination;
    }

    public Direction getDirection() {
        return direction;
    }

    public AbstractPlayer(GameContext context, int hp, int maxHp, int minDamage, int maxDamage, int attack, String name, int speed, Sprite sprite,
            int[][] consequences) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.attack = attack;
        this.name = name;
        this.speed = speed;
        this.sprite = sprite;
        this.consequences = consequences;
        this.context = context;
        sprite.setSpriteQueueSteps(consequences[RIGHT_CONSEQUENCE]);
    }

    public void paint(Graphics g) {
        //y - top-left dest corner
        //y + 16 - down-left
        //y - Math.abs(tileWidth - playerWidth) - heroPos dest

        int tx = getX()/* - Math.abs(Tile.WIDTH - AbstractPlayer.WIDTH)*/, ty = getY();
        Sprite ts = sprite;
        ts.setX(tx);
        //ts.setY(ty - Math.abs(Tile.HEIGHT - AbstractPlayer.HEIGHT));
        ts.paint(g, Math.abs(Tile.WIDTH - AbstractPlayer.WIDTH) / 2, Math.abs(Tile.HEIGHT - AbstractPlayer.HEIGHT));
        //ts.setY(ty + Math.abs(Tile.HEIGHT - AbstractPlayer.HEIGHT));


        //g.drawRect(getBlocksX() * 16, getBlocksY() * 16, 16, 16);
    }

    public int getBlocksX() {
        return sprite.getBlocksX();
    }

    public int getBlocksY() {
        return sprite.getBlocksY();
    }

    public void setBlocksX(int x) {
        sprite.setBlocksX(x);
    }

    public void setBlocksY(int y) {
        sprite.setBlocksY(y);
    }

    public int getX() {
        return sprite.getX();
    }

    public void setX(int x) {
        sprite.setX(x);
    }

    public int getY() {
        return sprite.getY();
    }

    public void setY(int y) {
        sprite.setY(y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    private void move(int deltaX, int deltaY) {
        final TiledLayer layer = context.getWorld().getLayer();
        sprite.nextStep();
        int oldX = layer.getX(), oldY = layer.getY();
        layer.setX(oldX + deltaX);
        layer.setY(oldY + deltaY);
        if (!context.getWorld().canWalk(getBlocksX() - layer.getBlocksX(), getBlocksY() - layer.getBlocksY())) {
            stopMoving();
            layer.setX(oldX);
            layer.setY(oldY);
        }
    }

    public void moveUp() {
        sprite.setSpriteQueueSteps(consequences[UP_CONSEQUENCE]);
        move(0, -STEP);
    }

    public void moveDown() {
        sprite.setSpriteQueueSteps(consequences[DOWN_CONSEQUENCE]);
        move(0, STEP);
    }

    public void moveLeft() {
        sprite.setSpriteQueueSteps(consequences[LEFT_CONSEQUENCE]);
        move(-STEP, 0);
    }

    public void moveRight() {
        sprite.setSpriteQueueSteps(consequences[RIGHT_CONSEQUENCE]);
        move(STEP, 0);
    }

    //animate without moving
    public void right() {
        sprite.setSpriteQueueSteps(consequences[RIGHT_CONSEQUENCE]);
        move(-STEP, 0);
    }

    //animate without moving
    public void left() {
        sprite.setSpriteQueueSteps(consequences[LEFT_CONSEQUENCE]);
        move(STEP, 0);
    }

    //animate without moving
    public void up() {
        sprite.setSpriteQueueSteps(consequences[UP_CONSEQUENCE]);
        move(0, STEP);
    }

    //animate without moving
    public void down() {
        sprite.setSpriteQueueSteps(consequences[DOWN_CONSEQUENCE]);
        move(0, -STEP);
    }

    public void resetSequence() {
        sprite.setStep(STEP);
    }
}
