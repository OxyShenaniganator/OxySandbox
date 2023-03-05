package input;

import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    boolean isWPressed = false;
    boolean isAPressed = false;
    boolean isSPressed = false;
    boolean isDPressed = false;

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W:
                isWPressed = true;
                Game.entitiesHandler.changePlayerYDelta(-2);
                break;
            case KeyEvent.VK_A:
                isAPressed = true;
                Game.entitiesHandler.changePlayerXDelta(-2);
                break;
            case KeyEvent.VK_S:
                isSPressed = true;
                Game.entitiesHandler.changePlayerYDelta(2);
                break;
            case KeyEvent.VK_D:
                isDPressed = true;
                Game.entitiesHandler.changePlayerXDelta(2);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W:
                isWPressed = false;
                break;
            case KeyEvent.VK_A:
                isAPressed = false;
                break;
            case KeyEvent.VK_S:
                isSPressed = false;
                break;
            case KeyEvent.VK_D:
                isDPressed = false;
                break;
        }

    }
}
