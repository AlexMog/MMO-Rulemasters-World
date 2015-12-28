package alexmog.rulemastersworld.scenes;

import TWLSlick.RootPane;
import alexmog.rulemastersworld.scenes.optionsscene.OptionsWidget;

public class OptionsScene extends BackgroundPlanetScene {
	@Override
    protected RootPane createRootPane() {
        RootPane rp = super.createRootPane();
        rp.add(new OptionsWidget(this));
        
        return rp;
    }

    @Override
    public int getID() {
        return 3;
    }

}
