package org.rising.game;

import java.awt.Canvas;
import java.awt.Color;
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
//java.lang.ClassFormatError: Incompatible magic value 0 in class file org/rising/player/AbstractPlayer
    private static final long serialVersionUID = 1L;
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
        setPreferredSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graphicsThread = new Thread(this, "Display");
        Tile.init();
        try {
            world = new World(ImageIO.read(Core.class.getResourceAsStream("/resources/tileset.png")),
                    Tile.WIDTH, Tile.HEIGHT, LayerIO.mapW, LayerIO.mapH, 41, 31);
        } catch (IOException ex) {
            System.out.println("Cannot create layer.");
        }
        camera = new Camera();
        context = new GameContext(world, camera);
        player = new Player(context, 0, 0, 0, 0, 0, "Player", 100);

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
            update();
            render();
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 640, 480);

        camera.moveCamera(g);
        world.paint(g);
        player.paint(g);
        camera.unmoveCamera(g);

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
        
        LobbyScreen lobby = new LobbyScreen();
        game.frame.add(lobby);
        lobby.setVisible(true);
        
        game.frame.pack();
        
        //game.init();
        game.requestFocus();
    }
}
