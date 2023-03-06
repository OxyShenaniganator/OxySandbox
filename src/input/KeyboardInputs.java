package input;

import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    public boolean isWPressed = false;
    public boolean isAPressed = false;
    public boolean isSPressed = false;
    public boolean isDPressed = false;

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        int input = keyEvent.getKeyCode();

        if (input == KeyEvent.VK_W) {
            isWPressed = true;
            Game.entitiesHandler.setWPressed(true);

        }
        if (input == KeyEvent.VK_A) {
            isAPressed = true;
            Game.entitiesHandler.setAPressed(true);

        }
        if (input == KeyEvent.VK_S) {
            isSPressed = true;
            Game.entitiesHandler.setSPressed(true);

        }
        if (input == KeyEvent.VK_D) {
            isDPressed = true;
            Game.entitiesHandler.setDPressed(true);

        }


    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W -> Game.entitiesHandler.setWPressed(false);
            case KeyEvent.VK_A -> Game.entitiesHandler.setAPressed(false);
            case KeyEvent.VK_S -> Game.entitiesHandler.setSPressed(false);
            case KeyEvent.VK_D -> Game.entitiesHandler.setDPressed(false);
        }

    }
}
