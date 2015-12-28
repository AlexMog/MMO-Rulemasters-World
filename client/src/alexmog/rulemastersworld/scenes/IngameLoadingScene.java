package alexmog.rulemastersworld.scenes;

import TWLSlick.RootPane;
import alexmog.rulemastersworld.scenes.ingameloading.IngameLoadingWidget;

public class IngameLoadingScene extends BackgroundPlanetScene {
    private IngameLoadingWidget mWidget;

    @Override
    protected RootPane createRootPane() {
        RootPane rp = super.createRootPane();
        mWidget = new IngameLoadingWidget(this);
        rp.add(mWidget);
        
        return rp;
    }
    
    public void setStatus(String status) {
        mWidget.setStatus(status);
    }
    
    public void setLoaded(boolean loaded) {
        mWidget.setLoaded(loaded);
    }
    
    @Override
    public int getID() {
        return 5;
    }

    public void addReturnButton() {
        mWidget.addReturnButton();
    }

    public void reset() {
        if (mWidget != null) {
            mWidget.reset();
        }
    }

}
