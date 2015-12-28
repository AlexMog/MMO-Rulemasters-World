package alexmog.rulemastersworld.datas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import org.apache.commons.dbcp2.DelegatingPreparedStatement;

import com.esotericsoftware.minlog.Log;
import com.google.gson.Gson;

import alexmog.rulemastersworld.Main;

public class DBManager {
    private static DBManager instance = new DBManager();
    
    public static DBManager getInstance() {
        return instance;
    }
    
    private DBManager() {}
    
    public void initScripts() throws Exception {
        Log.info("Loading scripts from shared db...");
        try (
            Connection conn = Main.sharedDb.getConnection();
            DelegatingPreparedStatement statement = (DelegatingPreparedStatement) conn.prepareStatement("SELECT * FROM scripts WHERE istemplate = 0");
            ResultSet result = statement.executeQuery();
        ) {
            while (result.next()) {
                String name = result.getString("name");
                Log.info("Loading script ' " + name + " '...");
                String description = result.getString("description");
                String script = result.getString("script");
                String type = result.getString("type");
                int authorId = result.getInt("author_id");
                int id = result.getInt("id");
                boolean isTemplate = result.getBoolean("istemplate");
                String jsonVars = result.getString("script_vars");
                ScriptBean bean = new ScriptBean(id, script, name, description, isTemplate, authorId, type);
                
                if (jsonVars.startsWith("[")) {
                    Gson gson = new Gson();
                    JsonVarBean[] array = gson.fromJson(jsonVars, JsonVarBean[].class);
                    
                    for (JsonVarBean e : array) {
                        CustomVarType varType = null;
                        try {
                            varType = CustomVarType.valueOf(e.type.toUpperCase());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        
                        bean.addCustomVar(new CustomVarBean(e.name,
                                e.value, varType));
                    }
                }
                
                DatasManager.getInstance().getScripts().put(id,
                        bean);
            }
        }
        Log.info("Scripts loaded.");
    }
    
    public void initSkills() throws Exception {
        Log.info("Loading skills from shared db...");
        try (
            Connection conn = Main.sharedDb.getConnection();
            DelegatingPreparedStatement statement = (DelegatingPreparedStatement) conn.prepareStatement("SELECT * FROM skills");
            ResultSet result = statement.executeQuery();
        ) {
            while (result.next()) {
                String name = result.getString("name");
                Log.info("Loading skill '" + name + "'...");
                String description = result.getString("description");
                boolean passive = result.getBoolean("passive");
                int iconId = result.getInt("icon_id");
                int id = result.getInt("id");
                int range = result.getInt("range");
                String effectsList = result.getString("effects_list");
                int scriptId = result.getInt("script_id");
                StringTokenizer tokens = new StringTokenizer(effectsList, ",");
                SkillBean sb = new SkillBean(id, iconId, range, scriptId, name, description, passive);
                while (tokens.hasMoreTokens()) {
                    String token = tokens.nextToken();
                    try {
                        sb.getEffectList().add(Integer.parseInt(token));
                    } catch (Exception e) {
                        Log.info("Cannot add effect to skillbean" + token);
                    }
                }
                DatasManager.getInstance().getSkillBeans().put(id,
                        sb);
            }
        }
        Log.info("Skills loaded.");
    }
    
    public void initEntityTemplates() throws Exception {
        Log.info("Loading entity templates from shared db...");
        try (
                Connection conn = Main.sharedDb.getConnection();
                DelegatingPreparedStatement statement = (DelegatingPreparedStatement) conn.prepareStatement("SELECT * FROM livingentities_templates");
                ResultSet result = statement.executeQuery();
            ) {
            while (result.next()) {
                String name = result.getString("name");
                Log.info("Loading entity template '" + name + "'...");
                String description = result.getString("description");
                int id = result.getInt("id");
                int scriptId = result.getInt("script_id");
                int str = result.getInt("strength");
                int agi = result.getInt("agility");
                int intel = result.getInt("intelligence");
                int end = result.getInt("endurence");
                int armor = result.getInt("armor");
                float speed = result.getFloat("speed");
                int skinId = result.getInt("skin_id");
                boolean useAstar = result.getBoolean("use_astar");
                DatasManager.getInstance().getEntityTemplates().put(id,
                        new EntityTemplate(id, name, description, str, agi, intel, end, armor, speed, scriptId, skinId, useAstar));
            }
        }
        Log.info("Entity templates loaded.");
    }
    
    public void initEffectTemplates() throws Exception {
        Log.info("Loading effect templates from shared db...");
        try (
                Connection conn = Main.sharedDb.getConnection();
                DelegatingPreparedStatement statement = (DelegatingPreparedStatement) conn.prepareStatement("SELECT * FROM effects_templates");
                ResultSet result = statement.executeQuery();
            ) {
            while (result.next()) {
                String name = result.getString("name");
                Log.info("Loading effect template '" + name + "'...");
                String description = result.getString("description");
                int id = result.getInt("id");
                int scriptId = result.getInt("script_id");
                DatasManager.getInstance().getEffectTemplates().put(id,
                        new EffectTemplate(id, scriptId, name, description));
            }
        }
        Log.info("Effect templates loaded.");
    }
    
    public void initEffects() throws Exception {
        Log.info("Loading effects from shared db...");
        try (
                Connection conn = Main.sharedDb.getConnection();
                DelegatingPreparedStatement statement = (DelegatingPreparedStatement) conn.prepareStatement("SELECT * FROM effects");
                ResultSet result = statement.executeQuery();
            ) {
            while (result.next()) {
                String name = result.getString("name");
                Log.info("Loading effect '" + name + "'...");
                String description = result.getString("description");
                int id = result.getInt("id");
                int templateId = result.getInt("template_id");
                long duration = result.getLong("duration");
                boolean stackable = result.getInt("stackable") == 1;
                int iconId = result.getInt("icon_id");
                int ticTime = result.getInt("tic_time");
                boolean infinite = result.getBoolean("infinite");
                String jsonVars = result.getString("script_vars");
                EffectBean bean = new EffectBean(id, templateId, duration, name, description, stackable, iconId, ticTime, infinite);

                if (jsonVars.startsWith("[")) {
                    Gson gson = new Gson();
                    JsonVarBean[] array = gson.fromJson(jsonVars, JsonVarBean[].class);
                    
                    for (JsonVarBean e : array) {
                        bean.addCustomVar(e.name, e.value);
                    }
                }
                DatasManager.getInstance().getEffectBeans().put(id, bean);
            }
        }
        Log.info("Effects loaded.");
    }
}
