package alexmog.rulemastersworld.scenes;

import TWLSlick.RootPane;
import alexmog.rulemastersworld.scenes.mainscene.MenuWidget;

public class MainScene extends BackgroundPlanetScene {
    @Override
    protected RootPane createRootPane() {
        RootPane rp = super.createRootPane();
        rp.add(new MenuWidget(this));
        
        return rp;
    }

    @Override
    public int getID() {
        return 4;
    }

}
