package alexmog.rulemastersworld.tchat.commands;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.managers.MapInstancesManager;
import alexmog.rulemastersworld.map.MapInstance;

public class InfoCommand implements TchatCommandRunner {

    @Override
    public String run(AccountConnection account, String args) {
        String status = "Server stats:<br>";
        if (account != null) {
            GameMode currentGameMode = account.getPlayer().getMapInstance().getGameInstance().getGameMode();
            status += "Total Entities on this map: " + currentGameMode.getEntityManager().getEntityCount() + "<br>";
            status += "Total collisions on this map: " + currentGameMode.getEntityManager().getCollisionsCheckNumber() + "<br>";
        }
        
        int totalEntities = 0;
        int totalCollisions = 0;
        
        for (MapInstance i : MapInstancesManager.getInstance().getMapInstances().values()) {
            totalEntities += i.getGameInstance().getGameMode().getEntityManager().getEntityCount();
            totalCollisions += i.getGameInstance().getGameMode().getEntityManager().getCollisionsCheckNumber();
        }
        
        status += "Total world threads: " + MapInstancesManager.getInstance().getInstanceNumber() + "<br>";
        status += "Total Entities on the server: " + totalEntities + "<br>";
        status += "Total Collisions checked on the server: " + totalCollisions + "<br>";
        status += "Total players connected: " + Main.clientList.size();
        return status;
    }

    @Override
    public int requiredLevel() {
        return 1;
    }

    @Override
    public String help() {
        return "/info<br>  Display the server state";
    }

}
