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

import de.matthiasmann.twl.ActionMap;
import de.matthiasmann.twl.Widget;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The base class for all game states when using TWLStateBasedGame.
 *
 * <p>To create your UI you can override {@link #createRootPane()} or
 * {@link #init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)}.</p>
 *
 * <p>Set the position and size of the widgets inside {@link #layoutRootPane()}.</p>
 *
 * @author Matthias Mann
 */
public abstract class BasicTWLGameState extends BasicGameState {

    private RootPane rootPane;

    /**
     * Returns the root pane for this game state.
     * Calls {@link #createRootPane()} if it has not yet been created.
     *
     * @return the root pane
     */
    public RootPane getRootPane() {
        if(rootPane == null) {
            rootPane = createRootPane();
            if(rootPane.getState() != this) {
                throw new IllegalStateException("rootPane.getState() != this");
            }
        }
        return rootPane;
    }

    /**
     * Installs the rootPane of this state as the active root pane.
     * Calls createRootPane() on first run.
     *
     * @param container the GameContainer instance
     * @param game the StateBasedGame instance
     * @throws SlickException
     * @see #createRootPane()
     */
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        ((TWLStateBasedGame)game).setRootPane(getRootPane());
    }

    /**
     * Override this method to customize the root pane for your UI for this state.
     *
     * <p>The theme name of the RootPane created by this method is "state"+getID().
     * It will also register all action methods to the rootPane.</p>
     *
     * <p>Do not call this method. Call {@link #getRootPane()} instead</p>
     *
     * <p>When overriding this method don't call {@link #getRootPane()} from
     * within this method or it will lead to an endless loop.</p>
     *
     * @return the created root pane
     * @see ActionMap.Action
     * @see ActionMap#addMapping(java.lang.Object)
     * @see Widget#setActionMap(de.matthiasmann.twl.ActionMap)
     * @see Widget#setTheme(java.lang.String)
     */
    protected RootPane createRootPane() {
        assert rootPane == null : "RootPane already created";
        
        RootPane rp = new RootPane(this);
        rp.setTheme("state"+getID());
        rp.getOrCreateActionMap().addMapping(this);
        return rp;
    }

    /**
     * This method is called when keyboard focus is transfered to a UI widget
     * or to another application.
     */
    protected void keyboardFocusLost() {
    }

    /**
     * This method is called when the layout of the root pane needs to be updated.
     *
     * Widget position and size should only be changed within this method.
     *
     * @see Widget#setPosition(int, int)
     * @see Widget#setSize(int, int)
     * @see Widget#adjustSize() 
     */
    protected void layoutRootPane() {
    }
}
