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

import de.matthiasmann.twl.DesktopArea;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.Widget;

/**
 * RootPane for all game states.
 * It forwards input events which where not handled by the UI to the game state.
 * 
 * @author Matthias Mann
 */
public class RootPane extends DesktopArea {

    protected BasicTWLGameState state;
    protected int oldMouseX;
    protected int oldMouseY;

    public RootPane(BasicTWLGameState state) {
        if (state == null) {
            throw new NullPointerException("state");
        }
        this.state = state;

        setCanAcceptKeyboardFocus(true);
    }

    /**
     * When subclassing this class it's strongly suggested to provide
     * a default constructor to allow previewing in the Theme Editor.
     */
    protected RootPane() {
        this.state = null;
        
        setCanAcceptKeyboardFocus(true);
        
        System.err.println("This constructor is only intended to by called to preview subclass in the TWL Theme Editor");
    }

    /**
     * Returns the game state to which this root pane is associated with.
     * @return the game state or null when in preview mode (Theme Editor).
     * @see #isPreviewMode() 
     */
    public final BasicTWLGameState getState() {
        return state;
    }
    
    /**
     * Returns true when the root pane is in preview mode (Theme Editor).
     * @return true when the root pane is in preview mode (Theme Editor).
     */
    public final boolean isPreviewMode() {
        return state == null;
    }
    
    @Override
    protected void keyboardFocusLost() {
        if(state != null) {
            state.keyboardFocusLost();
        }
    }

    @Override
    protected boolean requestKeyboardFocus(Widget child) {
        if (child != null && state != null) {
            state.keyboardFocusLost();
        }
        return super.requestKeyboardFocus(child);
    }

    @Override
    protected boolean handleEvent(Event evt) {
        if (super.handleEvent(evt)) {
            return true;
        }

        if(state != null) {
            switch (evt.getType()) {
                case KEY_PRESSED:
                    state.keyPressed(evt.getKeyCode(), evt.getKeyChar());
                    break;
                case KEY_RELEASED:
                    state.keyReleased(evt.getKeyCode(), evt.getKeyChar());
                    break;
                case MOUSE_BTNDOWN:
                    state.mousePressed(evt.getMouseButton(), evt.getMouseX(), evt.getMouseY());
                    break;
                case MOUSE_BTNUP:
                    state.mouseReleased(evt.getMouseButton(), evt.getMouseX(), evt.getMouseY());
                    break;
                case MOUSE_CLICKED:
                    state.mouseClicked(evt.getMouseButton(), evt.getMouseX(), evt.getMouseY(), evt.getMouseClickCount());
                    break;
                case MOUSE_ENTERED:
                case MOUSE_MOVED:
                    state.mouseMoved(oldMouseX, oldMouseY, evt.getMouseX(), evt.getMouseY());
                    break;
                case MOUSE_DRAGGED:
                    state.mouseDragged(oldMouseX, oldMouseY, evt.getMouseX(), evt.getMouseY());
                    break;
                case MOUSE_WHEEL:
                    state.mouseWheelMoved(evt.getMouseWheelDelta());
                    break;
            }
        }

        if (evt.isMouseEvent()) {
            oldMouseX = evt.getMouseX();
            oldMouseY = evt.getMouseY();
        }
        return true;
    }

    @Override
    protected void layout() {
        super.layout();
        state.layoutRootPane();
    }
}
