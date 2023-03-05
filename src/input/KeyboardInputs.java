package input;

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

        }
        if (input == KeyEvent.VK_A) {
            isAPressed = true;

        }
        if (input == KeyEvent.VK_S) {
            isSPressed = true;

        }
        if (input == KeyEvent.VK_D) {
            isDPressed = true;

        }


    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W -> isWPressed = false;
            case KeyEvent.VK_A -> isAPressed = false;
            case KeyEvent.VK_S -> isSPressed = false;
            case KeyEvent.VK_D -> isDPressed = false;
        }

    }
}
