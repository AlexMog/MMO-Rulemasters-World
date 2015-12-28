/*
 * Copyright (c) 2008-2010, Matthias Mann
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Matthias Mann nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package TWLSlick;

import de.matthiasmann.twl.GUI;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.InputAdapter;

/**
 * Forwards input events from Slick to TWL
 * 
 * @author Matthias Mann
 */
class TWLInputForwarder extends InputAdapter {

    private final Input input;
    private final GUI gui;

    public TWLInputForwarder(GUI gui, Input input) {
        if (gui == null) {
            throw new NullPointerException("gui");
        }
        if (input == null) {
            throw new NullPointerException("input");
        }

        this.gui = gui;
        this.input = input;
    }

    @Override
    public void mouseWheelMoved(int change) {
        gui.handleMouseWheel(change);
        input.consumeEvent();
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        gui.handleMouse(x, y, button, true);
        input.consumeEvent();
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        gui.handleMouse(x, y, button, false);
        input.consumeEvent();
    }

    @Override
    public void mouseMoved(int oldX, int oldY, int newX, int newY) {
        gui.handleMouse(newX, newY, -1, false);
        input.consumeEvent();
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newX, int newY) {
        mouseMoved(oldx, oldy, newX, newY);
    }

    @Override
    public void keyPressed(int key, char c) {
        gui.handleKey(key, c, true);
        input.consumeEvent();
    }

    @Override
    public void keyReleased(int key, char c) {
        gui.handleKey(key, c, false);
        input.consumeEvent();
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        input.consumeEvent();
    }

    @Override
    public void inputStarted() {
        gui.updateTime();
        if (!Display.isActive()) {
            gui.clearKeyboardState();
            gui.clearMouseState();

            if (gui.getRootPane() instanceof RootPane) {
                ((RootPane) gui.getRootPane()).keyboardFocusLost();
            }
        }
    }

    @Override
    public void inputEnded() {
        gui.handleKeyRepeat();
    }
}
