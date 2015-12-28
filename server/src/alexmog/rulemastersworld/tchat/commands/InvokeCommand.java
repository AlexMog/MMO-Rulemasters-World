package alexmog.rulemastersworld.tchat.commands;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.entity.EntityFactory;
import alexmog.rulemastersworld.entity.ScriptedEntity;

public class InvokeCommand implements TchatCommandRunner {
	public InvokeCommand() {
	}

	@Override
	public String run(AccountConnection account, String args) {
		String[] cuttedArgs = args.split(" ");
		String ret = null;
		int id = -1;
		if (cuttedArgs.length >= 4) {
			try {
				int posX = Integer.parseInt(cuttedArgs[2]);
				int posY = Integer.parseInt(cuttedArgs[3]);
				int size = Integer.parseInt(cuttedArgs[1]);
				
				try {
				    id = Integer.parseInt(cuttedArgs[0]);
				} catch (Exception e) {}
				
				ScriptedEntity entity = null;
				if (id != -1) {
				    entity = EntityFactory.createEntity(
        				        account.getPlayer().getMapInstance().getGameInstance().getGameMode(),
        				        id,
        				        new Vector2f(posX, posY),
        				        new Vector2f(account.getPlayer().getMapInstance().getMap().getTmxMap().getTilewidth() * size,
                                        account.getPlayer().getMapInstance().getMap().getTmxMap().getTileheight() * size)
				            );
				} else {
		            entity = EntityFactory.createEntity(
                            account.getPlayer().getMapInstance().getGameInstance().getGameMode(),
                            cuttedArgs[0],
                            new Vector2f(posX, posY),
                            new Vector2f(account.getPlayer().getMapInstance().getMap().getTmxMap().getTilewidth() * size,
                                    account.getPlayer().getMapInstance().getMap().getTmxMap().getTileheight() * size)
                        );
				}
				if (entity != null) {
					account.getPlayer().getMapInstance().getGameInstance().getGameMode()
					        .getEntityManager().addEntity(entity);
					ret = "Entity created.";
				} else {
					ret = "Entity not found.";
				}
			} catch (Exception e) {
				ret = "Args 2 and 3 incorrect. They myst be numbers.";
			}
		} else {
			ret = "Need more arguments.";
		}
		return ret;
	}

	@Override
	public int requiredLevel() {
		return 4;
	}

	@Override
	public String help() {
		return "/invoke <entityname or id> <size> <posX> <posY><br>  Invokes an entity";
	}

}
