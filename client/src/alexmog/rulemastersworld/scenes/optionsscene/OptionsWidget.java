package alexmog.rulemastersworld.scenes.optionsscene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.datas.ConfigType;
import alexmog.rulemastersworld.datas.ConfigsManager;
import alexmog.rulemastersworld.datas.Translator;
import alexmog.rulemastersworld.scenes.OptionsScene;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ToggleButton;
import de.matthiasmann.twl.ValueAdjusterInt;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleIntegerModel;

public class OptionsWidget extends DialogLayout {
    private final OptionsScene mScene;
    private final class OptionsContainer {
    	public OptionsContainer(String key, Widget widget, ConfigType type) {
    		this.key = key;
    		this.widget = widget;
    		this.type = type;
    	}
    	
    	public String key;
    	public Widget widget;
    	public ConfigType type;
    }
    
    private List<OptionsContainer> mOptions = new ArrayList<OptionsContainer>();
    
    public OptionsWidget(OptionsScene optionsScene) {
        mScene = optionsScene;
        mOptions.clear();
        setTheme("loginscenewidget");
        
        Button save = new Button("Save");
        save.addCallback(new Runnable() {
			
			public void run() {
				saveConfigs();
			}
		});
        Button back = new Button("Back");
        back.addCallback(new Runnable() {
            
            public void run() {
                mScene.getGame().enterState(4);
            }
        });
        
        Group sg = createSequentialGroup();
        Group labels = createParallelGroup();
        Group fields = createParallelGroup();
        for (Entry<Object, Object> e : ConfigsManager.getInstance().getProperties().entrySet()) {
        	ConfigType type = ConfigsManager.getConfigType((String)e.getValue());
        	Log.debug("ConfigOption: " + e.getKey() + " : " + e.getValue() + " "
        			+ type);
        	Label label = new Label(Translator.getInstance().getTranslated((String)e.getKey()));
        	Widget val;
        	String value = (String)e.getValue();
        	if (type == ConfigType.Boolean) {
        		val = new ToggleButton();
        		if (value.equalsIgnoreCase("true")) {
        			((ToggleButton)val).setActive(true);
        		}
        	} else if (type == ConfigType.Integer) {
        		val = new ValueAdjusterInt(new SimpleIntegerModel(0, 100, Integer.parseInt((String)e.getValue())));
        	} else if (type == ConfigType.Key) {
        		val = new EditField(); // TODO change this?
        		((EditField)val).setText(value);
        	} else {
        		val = new EditField();
        		((EditField)val).setText(value);
        	}
        	labels.addWidget(label);
        	fields.addWidget(val);
        	mOptions.add(new OptionsContainer((String)e.getKey(), val, type));
        	sg.addGroup(createParallelGroup(label, val));
        }
        setHorizontalGroup(createParallelGroup()
        		.addGroup(createSequentialGroup(labels, fields))
        		.addWidget(save)
        		.addWidget(back));
        setVerticalGroup(sg
        		.addWidget(save)
        		.addWidget(back));
    }
    
    @Override
    protected void layout() {
        // login panel is centered
        adjustSize();
        setPosition((getParent().getWidth() - getWidth()) / 2,
                (getParent().getHeight() - getHeight()) / 2);
    }
    
    private void saveConfigs() {
    	for (OptionsContainer o : mOptions) {
    		String str = "";
    		if (o.type == ConfigType.Boolean) {
    			str = "" + ((ToggleButton)o.widget).isActive();
        	} else if (o.type == ConfigType.Integer) {
        		str = "" + ((ValueAdjusterInt)o.widget).getValue();
        	} else if (o.type == ConfigType.Key) {
        		str = ((EditField)o.widget).getText();
        	} else {
        		str = ((EditField)o.widget).getText();
        	}
    		ConfigsManager.getInstance().getProperties()
				.setProperty(o.key, str);
    	}
    	try {
			ConfigsManager.getInstance().saveConfigs();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
