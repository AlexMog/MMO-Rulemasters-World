package alexmog.rulemastersworld.scenes.debug;

import alexmog.rulemastersworld.gui.FadeFrame;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Graph;
import de.matthiasmann.twl.model.SimpleGraphLineModel;
import de.matthiasmann.twl.model.SimpleGraphModel;

public class RamGraph  extends FadeFrame {

    private SimpleGraphLineModel gmRamPerFrame;
    private Runtime mRuntime = Runtime.getRuntime();

    public RamGraph() {
        gmRamPerFrame = new SimpleGraphLineModel("default", 100, 0, 30);
        

    	gmRamPerFrame.setMinValue(0);
        Graph graph = new Graph(new SimpleGraphModel(gmRamPerFrame));
        graph.setTheme("/graph");
        setPosition(0, 300);

        setTheme("resizableframe-title");
        setTitle("Memory Usage");
        add(graph);
    }

    @Override
    protected void paint(GUI gui) {
    	gmRamPerFrame.setMaxValue(mRuntime.totalMemory());
        gmRamPerFrame.addPoint((float)(mRuntime.totalMemory() - mRuntime.freeMemory()));

        super.paint(gui);
    }
    
}
