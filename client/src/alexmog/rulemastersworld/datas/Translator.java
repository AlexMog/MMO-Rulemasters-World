package alexmog.rulemastersworld.datas;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {
    private static Translator instance = new Translator();
    private ResourceBundle mBundle;
    
    public static Translator getInstance() {
        return instance;
    }
    
    public void initBundle(Locale locale, String path) throws MalformedURLException {
        mBundle = ResourceBundle.getBundle("lang", locale,
                new URLClassLoader(new URL[] {new File(path).toURI().toURL()}));
    }
    
    public String getTranslated(String key) {
        try {
            String ret = mBundle.getString(key);
            if (ret == null) {
                return key;
            }
            return ret;
        } catch (Exception e) {
            return key;
        }
    }
}
