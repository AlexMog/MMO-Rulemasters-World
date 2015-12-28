package alexmog.rulemastersworld.scenes.debug;

import alexmog.rulemastersworld.gui.FadeFrame;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Graph;
import de.matthiasmann.twl.model.SimpleGraphLineModel;
import de.matthiasmann.twl.model.SimpleGraphModel;

public class MsGraph  extends FadeFrame {

    private SimpleGraphLineModel gmMsPerFrame;
    private long lastTime = System.nanoTime();

    public MsGraph() {
        gmMsPerFrame = new SimpleGraphLineModel("default", 100, 0, 30);
        
        Graph graph = new Graph(new SimpleGraphModel(gmMsPerFrame));
        graph.setTheme("/graph");
        setPosition(0, 150);

        setTheme("resizableframe-title");
        setTitle("MS per frame");
        add(graph);
    }

    @Override
    protected void paint(GUI gui) {
        long time = System.nanoTime();
        gmMsPerFrame.addPoint((float)(time - lastTime) * 1e-6f);
        lastTime = time;

        super.paint(gui);
    }
    
}
