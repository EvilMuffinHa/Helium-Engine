package org.hl.engine.io;

import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private int[] keyState = new int[GLFW.GLFW_KEY_LAST];
    private int[] buttonState = new int[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private long window;
    private Display display;

    private boolean inWindow;

    private double mouseX, mouseY;
    private double scrollX, scrollY;

    // Sets up the callbacks based on the window
    public Input(Display d) {
        this.display = d;
        this.window = this.display.getWindow();

        glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
            keys[key] = action != GLFW.GLFW_RELEASE;
            keyState[key] = action;
        });

        glfwSetMouseButtonCallback(this.window, (window, button, action, mods) -> {
            buttons[button] = action != GLFW.GLFW_RELEASE;
            buttonState[button] = action;
        });

        glfwSetCursorPosCallback(this.window, (window, xpos, ypos) -> {
            mouseX = xpos;
            mouseY = ypos;
        });

        glfwSetCursorEnterCallback(this.window, (window, entered) -> {
            inWindow = entered;
        });

        glfwSetScrollCallback(this.window, (window, xoffset, yoffset) -> {
            scrollX += xoffset;
            scrollY += yoffset;
        });

        resetKeyboard();
        resetButtons();
    }

    // All states (is<name>Down will return whether it has been held down but <name>Press only returns a press)

    public boolean isKeyDown(int key) {
        return keys[key];
    }

    public boolean keyPress(int key) {
        return keyState[key] == GLFW.GLFW_PRESS;
    }

    public boolean keyReleased(int key) {
        return keyState[key] == GLFW.GLFW_RELEASE;
    }

    public boolean isButtonDown(int button) {
        return buttons[button];
    }

    public boolean buttonPress(int button) {
        return buttonState[button] == GLFW.GLFW_PRESS;
    }

    public boolean buttonReleased(int button) {
        return buttonState[button] == GLFW.GLFW_RELEASE;
    }

    // Resets keyboard and buttons so the presses will only be registered once
    private void resetKeyboard() {
        Arrays.fill(keyState, -1);
    }

    private void resetButtons() {
        Arrays.fill(buttonState, -1);
    }


    // This function should only be called after all input has been taken inside the loop. It must be called if keyPress and buttonPress should work.

    public void reset() {
        resetKeyboard();
        resetButtons();
    }

    // Scroll, mouse, and window getters

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public boolean inWindow() {
        return inWindow;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }
}
