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
                Game.entitiesHandler.changePlayerYDelta(-4, isWPressed);
                break;
            case KeyEvent.VK_A:
                isAPressed = true;
                Game.entitiesHandler.changePlayerXDelta(-4, isAPressed);
                break;
            case KeyEvent.VK_S:
                isSPressed = true;
                Game.entitiesHandler.changePlayerYDelta(4, isSPressed);
                break;
            case KeyEvent.VK_D:
                isDPressed = true;
                Game.entitiesHandler.changePlayerXDelta(4,isDPressed);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W:
                isWPressed = false;
                Game.entitiesHandler.changePlayerYDelta(0, isWPressed);
                break;
            case KeyEvent.VK_A:
                isAPressed = false;
                Game.entitiesHandler.changePlayerXDelta(0, isAPressed);
                break;
            case KeyEvent.VK_S:
                isSPressed = false;
                Game.entitiesHandler.changePlayerYDelta(0, isSPressed);
                break;
            case KeyEvent.VK_D:
                isDPressed = false;
                Game.entitiesHandler.changePlayerXDelta(0, isDPressed);
                break;
        }

    }
}
