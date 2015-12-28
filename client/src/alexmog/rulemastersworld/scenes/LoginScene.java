package alexmog.rulemastersworld.scenes;

import TWLSlick.RootPane;
import alexmog.rulemastersworld.scenes.loginscene.LoginSceneWidget;

public class LoginScene extends BackgroundPlanetScene {
    @Override
    protected RootPane createRootPane() {
        RootPane rp = super.createRootPane();
        rp.add(new LoginSceneWidget(this));
        
        return rp;
    }
    
    @Override
    public int getID() {
        return 1;
    }

}
