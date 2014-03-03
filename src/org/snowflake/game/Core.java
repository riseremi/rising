package org.snowflake.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.snowflake.layer.TiledLayer;
import org.snowflake.player.Player;
import org.snowflake.tiles.Tile;

/**
 *
 * @author Riseremi
 */
public class Core extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    private final Thread graphicsThread;
    private boolean running = false;
    private final Player player;
    private static Core instance;
    private TiledLayer layer;
    private final GameContext context;
    private final JFrame frame;
    private volatile Point destination = new Point();
    private final boolean[] keys = new boolean[256];
    private Direction direction = Direction.UNDEFINED;

    public static Core getInstance() {
        if (instance == null) {
            try {
                instance = new Core();
                return instance;
            } catch (IOException ex) {
            }
        }
        return instance;
    }

    private Core() throws IOException {
        frame = new JFrame("Prototype");
        setPreferredSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graphicsThread = new Thread(this, "Display");
        Tile.init();
        try {
            layer = new TiledLayer(ImageIO.read(Core.class.getResourceAsStream("/resources/tileset.png")),
                    Tile.WIDTH, Tile.HEIGHT, 40, 30, 40, 30);
        } catch (IOException ex) {
            System.out.println("cannot create layer");
        }

        context = new GameContext(layer);
        player = new Player(context, 0, 0, 0, 0, 0, "Test", 100);
        player.setBlocksX(8);
        player.setBlocksY(9);
        player.setY(128);

        setVisible(true);
    }

    public void stopMoving() {
        direction = Direction.UNDEFINED;
        player.resetSequence();
    }

    public void init() {
        running = true;
        graphicsThread.start();

        Thread testAnimation = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (direction == Direction.RIGHT) {
                        if (destination.getX() - player.getX() <= Player.STEP) {
                            player.setX(destination.x);
                            stopMoving();
                        } else {
                            player.moveRight(layer);
                        }
                    }
                    if (direction == Direction.LEFT) {
                        if (player.getX() - destination.getX() <= Player.STEP) {
                            player.setX(destination.x);
                            stopMoving();
                        } else {
                            player.moveLeft(layer);
                        }
                    }
                    if (direction == Direction.UP) {
                        if (player.getY() - destination.getY() <= Player.STEP) {
                            player.setY(destination.y);
                            stopMoving();
                        } else {
                            player.moveUp(layer);
                        }
                    }
                    if (direction == Direction.DOWN) {
                        if (destination.getY() - player.getY() <= Player.STEP) {
                            player.setY(destination.y);
                            stopMoving();
                        } else {
                            player.moveDown(layer);
                        }
                    }
                    try {
                        Thread.sleep(25L);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        testAnimation.start();
    }

    public Player getPlayer() {
        return player;
    }

    public TiledLayer getLayer() {
        return layer;
    }

    @Override
    public void run() {
        while (running) {
            update();
            render();
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 640, 480);

        layer.paint(g);
        player.paint(g);

        g.dispose();
        bs.show();
    }

    public void update() {
        if (keys[KeyEvent.VK_UP]) {
            if (direction == Direction.UNDEFINED) {
                player.getSprite().setShift(0);
                destination = new Point(player.getX(), player.getY() - Tile.HEIGHT);
                direction = Direction.UP;
            }
        }

        if (keys[KeyEvent.VK_DOWN]) {
            if (direction == Direction.UNDEFINED) {
                player.getSprite().setShift(Tile.HEIGHT - Player.STEP);
                destination = new Point(player.getX(), player.getY() + Tile.HEIGHT);
                direction = Direction.DOWN;
            }
        }

        if (keys[KeyEvent.VK_LEFT]) {
            if (direction == Direction.UNDEFINED) {
                player.getSprite().setShift(0);
                destination = new Point(player.getX() - Tile.WIDTH, player.getY());
                direction = Direction.LEFT;
            }
        }

        if (keys[KeyEvent.VK_RIGHT]) {
            if (direction == Direction.UNDEFINED) {
                player.getSprite().setShift(Tile.WIDTH - Player.STEP);
                destination = new Point(player.getX() + Tile.WIDTH, player.getY());
                direction = Direction.RIGHT;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public static void main(String[] args) {
        Core game = getInstance();
        game.frame.setResizable(false);
        game.frame.setVisible(true);
        game.frame.add(game);
        game.addKeyListener(game);
        game.frame.pack();
        game.init();
        game.requestFocus();
    }
}
