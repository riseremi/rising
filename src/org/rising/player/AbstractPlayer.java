package org.rising.player;

import java.awt.Graphics;
import java.awt.Point;
import org.rising.game.Camera;
import org.rising.game.Direction;
import org.rising.game.GameContext;
import org.rising.layer.Sprite;

/**
 *
 * @author Riseremi
 */
public abstract class AbstractPlayer {

    private static final long serialVersionUID = 1L;

    protected int hp, maxHp;
    protected int minDamage, maxDamage;
    protected int attack;
    protected String name;
    protected int speed;
    private final GameContext context;

    public final static int STEP = 2;
    private final Sprite sprite;
    private final static int UP_CONSEQUENCE = 3, DOWN_CONSEQUENCE = 1, LEFT_CONSEQUENCE = 2, RIGHT_CONSEQUENCE = 0;
    private final int[][] consequences;
    public static final int WIDTH = 32, HEIGHT = 32;
    private volatile Point destination = new Point();
    private Direction direction = Direction.UNDEFINED;
    protected PlayerAnimation playerAnimation;
    private final int id;

    public void stopMoving() {
        resetSequence();
        direction = Direction.UNDEFINED;

//        for (int i = 0; i < Core.getInstance().getKeys().length; i++) {
//            Core.getInstance().getKeys()[i] = false;
//        }
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

    public String getName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }

    public AbstractPlayer(GameContext context, int hp, int maxHp, int minDamage, int maxDamage, int attack, String name, int id, int speed, Sprite sprite,
            int[][] consequences) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.attack = attack;
        this.name = name;
        this.id = id;
        this.speed = speed;
        this.sprite = sprite;
        this.consequences = consequences;
        this.context = context;
        sprite.setSpriteQueueSteps(consequences[RIGHT_CONSEQUENCE]);
    }

    public int getId() {
        return id;
    }

    public void paint(Graphics g) {
        int tx = getX(), ty = getY();
        Sprite ts = sprite;
        ts.setX(tx);
//        ts.paint(g, Math.abs(Tile.WIDTH - AbstractPlayer.WIDTH) / 2, Math.abs(Tile.HEIGHT - AbstractPlayer.HEIGHT));
        sprite.paint(g, 0, 0);
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
        final Camera camera = context.getCamera();
        sprite.nextStep();
        int oldX = getX(), oldY = getY();
        int cameraOldX = camera.getX(), cameraOldY = camera.getY();
        setY(oldY + deltaY);
        setX(oldX + deltaX);
        camera.setX((cameraOldX - deltaX) /*/ Tile.WIDTH * Tile.WIDTH*/);
        camera.setY((cameraOldY - deltaY) /*/ Tile.HEIGHT * Tile.HEIGHT*/);

        if (!context.getWorld().canWalk(getBlocksX(), getBlocksY())) {
            stopMoving();
            setX(oldX);
            setY(oldY);
            camera.setY(cameraOldY);
            camera.setX(cameraOldX);
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

    public void resetSequence() {
        sprite.setStep(0);
    }

    @Override
    public String toString() {
        return name + ": " + id;
    }

//    public void setPosition(int x, int y) {
//        setBlocksX(x);
//        setBlocksY(y);
//        context.getCamera().setX(x * Tile.WIDTH);
//        context.getCamera().setY(y * Tile.HEIGHT);
//    }
}
