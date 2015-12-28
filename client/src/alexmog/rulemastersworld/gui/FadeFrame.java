package alexmog.rulemastersworld.gui;

import de.matthiasmann.twl.ResizableFrame;
import de.matthiasmann.twl.Widget;

public class FadeFrame  extends ResizableFrame {

    public FadeFrame() {
    }

    public void show() {
        setVisible(true);
        requestKeyboardFocus();
    }

    public void hide() {
        if(isVisible() && getFadeDurationHide() > 0) {
//            MinimizeEffect minimizeEffect = new MinimizeEffect(this);
//            minimizeEffect.setAnimationDuration(getFadeDurationHide());
//            setRenderOffscreen(minimizeEffect);
        }
        setVisible(false);
    }

    public void center(float relX, float relY) {
        Widget p = getParent();
        setPosition(
                p.getInnerX() + (int)((p.getInnerWidth() - getWidth()) * relX),
                p.getInnerY() + (int)((p.getInnerHeight() - getHeight()) * relY));
    }

    public void addCloseCallback() {
        addCloseCallback(new Runnable() {
            public void run() {
                hide();
            }
        });
    }
}
