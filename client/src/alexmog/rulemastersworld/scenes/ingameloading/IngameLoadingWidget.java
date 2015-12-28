package alexmog.rulemastersworld.scenes.ingameloading;

import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import alexmog.rulemastersworld.datas.Translator;
import alexmog.rulemastersworld.scenes.IngameLoadingScene;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.Label;

public class IngameLoadingWidget extends DialogLayout {
    private final Label lblStatus;
    private final IngameLoadingScene mLoginScene;
    private boolean mLoaded = false;
    private Button mReturnButton;

    public IngameLoadingWidget(IngameLoadingScene loginScene) {
        mLoginScene = loginScene;
        setTheme("loginscenewidget");
        
        lblStatus = new Label("");
        
        mReturnButton = new Button(Translator.getInstance().getTranslated("ServersScreen.returntomenu"));
        mReturnButton.addCallback(new Runnable() {
            
            public void run() {
                mLoginScene.getGame().enterState(4);
            }
        });
        
        setHorizontalGroup(createParallelGroup()
                .addWidget(lblStatus)
                .addWidget(mReturnButton));
        setVerticalGroup(createSequentialGroup()
                .addWidget(lblStatus)
                .addWidget(mReturnButton));
        reset();
    }

    @Override
    protected void layout() {
        if (mLoaded) {
            lblStatus.setText("");
            mLoginScene.getGame().enterState(2,
                    new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            mLoaded = false;
        }
        // login panel is centered
        adjustSize();
        setPosition((getParent().getWidth() - getWidth()) / 2,
                (getParent().getHeight() - getHeight()) / 2);
    }

    public void setStatus(String status) {
        lblStatus.setText(status);
    }
    
    public void setLoaded(boolean loaded) {
        mLoaded = loaded;
        lblStatus.getParent().invalidateLayout();
    }

    public void addReturnButton() {
        mReturnButton.setEnabled(true);
    }
    
    public void removeReturnButton() {
        mReturnButton.setEnabled(false);
    }

    public void reset() {
        lblStatus.setText(Translator.getInstance().getTranslated("ServersScreen.connectingtoserver"));
        removeReturnButton();
    }

}
