package alexmog.rulemastersworld.scenes.debug;

import alexmog.rulemastersworld.gui.FadeFrame;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Graph;
import de.matthiasmann.twl.model.SimpleGraphLineModel;
import de.matthiasmann.twl.model.SimpleGraphModel;

public class PingGraph  extends FadeFrame {

    private SimpleGraphLineModel gmPingPerFrame;

    public PingGraph() {
        gmPingPerFrame = new SimpleGraphLineModel("default", 100, 0, 30);
        

    	gmPingPerFrame.setMinValue(0);
        Graph graph = new Graph(new SimpleGraphModel(gmPingPerFrame));
        graph.setTheme("/graph");
        setPosition(350, 300);

        setTheme("resizableframe-title");
        setTitle("Ping");
        add(graph);
    }
    
    public void addPoint(int point) {
        gmPingPerFrame.addPoint(point);
    }

    @Override
    protected void paint(GUI gui) {
        float max = 0;
        for (int i = 0; i < gmPingPerFrame.getNumPoints(); ++i) {
            float point = gmPingPerFrame.getPoint(i);
            if (max < point) {
                max = point;
            }
        }
    	gmPingPerFrame.setMaxValue(max);
        super.paint(gui);
    }
    
}
