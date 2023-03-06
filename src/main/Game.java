package main;

import main.handler.EntitiesHandler;
import main.handler.TilesHandler;

public class Game implements Runnable{

    private GameFrame gameFrame;
    private GamePanel gamePanel;
    public static EntitiesHandler entitiesHandler;
    public static TilesHandler tilesHandler;

    private Thread gameThread;
    private final int setFPS = 120;
    private final int setUPS = 200;

    private int frames = 0;
    private long lastCheck = 0;

    public Game() {

        entitiesHandler = new EntitiesHandler();
        tilesHandler = new TilesHandler(gamePanel);

        gamePanel = new GamePanel();
        gameFrame = new GameFrame(gamePanel);

        System.out.println(entitiesHandler.entitiesHashMap);

        gamePanel.requestFocus();
        startGameLoop();

    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / setFPS;
        double timePerUpdate = 1000000000.0 / setUPS;
        long prevTime = System.nanoTime();

        int update = 0;
        double deltaU = 0;
        double deltaF = 0;

        lastCheck = System.currentTimeMillis();

        while (gameThread != null) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - prevTime) / timePerUpdate;
            deltaF += (currentTime - prevTime) / timePerFrame;
            prevTime = currentTime;

            if (deltaU >= 1) {

                update();

                update++;
                deltaU--;
            }

            if (deltaF >= 1 ) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }



//            if(nowTime - lastFrame >= timePerFrame) {
//
//                gamePanel.repaint();
//                lastFrame = nowTime;
//                frames++;
//            }

            if(System.currentTimeMillis() - lastCheck >= 1000) {

                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + update);
                frames = 0;
                update = 0;

            }

        }

    }

    private void update() {

        entitiesHandler.update();
        entitiesHandler.sortSpawnedEntity();
        GamePanel.gameUpdate();

    }
}
