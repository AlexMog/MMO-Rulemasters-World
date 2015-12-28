package alexmog.rulemastersworld.scenes.mainscene;

import alexmog.rulemastersworld.datas.Translator;
import alexmog.rulemastersworld.scenes.MainScene;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DialogLayout;

public class MenuWidget extends DialogLayout {
    private final MainScene mScene;
    
    public MenuWidget(MainScene mainScene) {
        mScene = mainScene;
        setTheme("loginscenewidget");
        
        Button play = new Button(Translator.getInstance().getTranslated("MainScreen.play"));
        play.addCallback(new Runnable() {
            
            public void run() {
                mScene.getGame().enterState(6);
            }
        });
        
        Button options = new Button(Translator.getInstance().getTranslated("MainScreen.options"));
        options.addCallback(new Runnable() {
            
            public void run() {
                mScene.getGame().enterState(3);
            }
        });
        
        Button quit = new Button(Translator.getInstance().getTranslated("MainScreen.exit"));
        quit.addCallback(new Runnable() {
            
            public void run() {
                System.exit(0);
            }
        });
        
        setHorizontalGroup(createParallelGroup()
                .addWidget(play)
                .addWidget(options)
                .addWidget(quit));
        setVerticalGroup(createSequentialGroup()
                .addWidget(play)
                .addWidget(options)
                .addWidget(quit));
    }
    
    @Override
    protected void layout() {
        // login panel is centered
        adjustSize();
        setPosition((getParent().getWidth() - getWidth()) / 2,
                (getParent().getHeight() - getHeight()) / 2);
    }
}
