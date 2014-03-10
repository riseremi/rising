package org.rising.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.rising.controllers.MoveController;
import org.rising.layer.LayerIO;
import org.rising.layer.TiledLayer;
import org.rising.player.Player;
import org.rising.tiles.Tile;

/**
 *
 * @author Riseremi
 */
public class Core extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    private static Core game;
    private static LobbyScreen lobby;
    private final Thread graphicsThread;
    private boolean running = false;
    private final Player player;
    private static Core instance;
    private World world;
    private final GameContext context;
    private final JFrame frame;
    private final boolean[] keys = new boolean[256];
    private MoveController moveController;
    private final Camera camera;

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
//        setPreferredSize(new Dimension(640, 480));
        setPreferredSize(new Dimension(37 * Tile.WIDTH, 21 * Tile.HEIGHT)); //37 x 21
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graphicsThread = new Thread(this, "Display");
        Tile.init();
        try {
            world = new World(ImageIO.read(Core.class.getResourceAsStream("/resources/new_tiles.png")),
                    Tile.WIDTH, Tile.HEIGHT, LayerIO.mapW, LayerIO.mapH, 38, 22);
        } catch (IOException ex) {
            System.out.println("Cannot create layer.");
        }
        camera = new Camera();
        //new Thread(camera).start();

        context = new GameContext(world, camera);
        player = new Player(context, "Player", 0);

        player.setBlocksX(world.getLayer().getPaintWidth() / 2);
        player.setBlocksY(world.getLayer().getPaintHeight() / 2);

        setVisible(true);
    }

    public Camera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }

    public void init() {
        game.frame.remove(lobby);
        game.frame.add(game);
//        game.addKeyListener(game);
        game.setVisible(true);
        game.frame.revalidate();
        game.frame.repaint();

        game.frame.pack();
        game.requestFocus();
        running = true;
        moveController = new MoveController();
        graphicsThread.start();
    }

    public Player getPlayer() {
        return player;
    }

    public TiledLayer getLayer() {
        return world.getLayer();
    }

    @Override
    public void run() {
        while (running) {
            try {
                //Thread.sleep(10L);
                update();
                render();
            } catch (Exception ex) {
            }
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, 640, 480);

        camera.moveCamera(g);
        world.paint(g);
        player.paint(g);
        camera.unmoveCamera(g);

        //System.out.println(camera.getX());
        g.dispose();
        bs.show();
    }

    public void update() {
        moveController.processMovement(context, player, keys);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("pressed");
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public static void main(String[] args) {
        game = getInstance();
        game.frame.setResizable(false);
        game.frame.setVisible(true);
        //game.frame.add(game);
        game.addKeyListener(game);
        
        lobby = new LobbyScreen();
        game.frame.add(lobby);
        
        game.frame.pack();
        //game.init();
        game.requestFocus();

    }
}
