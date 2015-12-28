package alexmog.rulemastersworld.entity;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.datas.EntityTemplate;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class EntityFactory {
    public static EntityTemplate getEntityTempalte(int id) {
        return getEntityTemplate(id, null);
    }
    
    public static EntityTemplate getEntityTemplate(String name) {
        return getEntityTemplate(-1, name);
    }
    
    private static EntityTemplate getEntityTemplate(int id, String name) {
        for (EntityTemplate e : DatasManager.getInstance().getEntityTemplates().values()) {
            if ((name != null && e.getName().equalsIgnoreCase(name))
                    || id == e.getId()) {
                return e;
            }
        }
        return null;
    }
    
    public static ScriptedEntity createEntity(GameMode gameMode, int id, Vector2f position, Vector2f size) {
        return createEntity(gameMode, id, null, position, size);
    }
    
    public static ScriptedEntity createEntity(GameMode gameMode, String name, Vector2f position, Vector2f size) {
        return createEntity(gameMode, -1, name, position, size);
    }
    
    private static ScriptedEntity createEntity(GameMode gameMode, int id, String name, Vector2f position, Vector2f size) {
        EntityTemplate template = null;
        if (name == null) {
            template = getEntityTempalte(id);
        } else {
            template = getEntityTemplate(name);
        }
        return (template == null ? null : new ScriptedEntity(position, size, gameMode, template));
    }
}
