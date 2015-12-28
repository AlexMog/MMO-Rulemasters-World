package alexmog.rulemastersworld.scenes.debug;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.gui.FadeFrame;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Graph;
import de.matthiasmann.twl.model.SimpleGraphLineModel;
import de.matthiasmann.twl.model.SimpleGraphModel;

public class CollisionsGraph  extends FadeFrame {

    private SimpleGraphLineModel gmRamPerFrame;

    public CollisionsGraph() {
        gmRamPerFrame = new SimpleGraphLineModel("default", 100, 0, 30);
        

    	gmRamPerFrame.setMinValue(0);
        Graph graph = new Graph(new SimpleGraphModel(gmRamPerFrame));
        graph.setTheme("/graph");
        setPosition(240, 300);

        setTheme("resizableframe-title");
        setTitle("Collisions");
        add(graph);
    }

    @Override
    protected void paint(GUI gui) {
        float max = 0;
        for (int i = 0; i < gmRamPerFrame.getNumPoints(); ++i) {
            float point = gmRamPerFrame.getPoint(i);
            if (max < point) {
                max = point;
            }
        }
    	gmRamPerFrame.setMaxValue(max);
        gmRamPerFrame.addPoint(Main.game.getGameMode().getEntitiesManager().getCollisionsCheckNumber());

        super.paint(gui);
    }
    
}
