package alexmog.rulemastersworld.tchat.commands;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.effects.EffectFactory;
import alexmog.rulemastersworld.effects.ScriptedEffect;

public class EffectCommand implements TchatCommandRunner {

	@Override
	public String run(AccountConnection account, String args) {
		String[] cuttedArgs = args.split(" ");
		
		if (cuttedArgs.length >= 2) {
			synchronized(Main.clientList) {
	            for (AccountConnection a : Main.clientList) {
	                if (a.getPlayer().getName().equalsIgnoreCase(cuttedArgs[0])) {
	                    ScriptedEffect effect = EffectFactory.createEffect(
	                            a.getPlayer().getMapInstance().getGameInstance().getGameMode(),
	                            cuttedArgs[1]);
	                    if (effect == null) return "Effect not found.";
                        a.getPlayer().addEffect(effect);
	                	return "Effect added.";
	                }
	            }
	        }
            return "User not found.";
		}
		return "Need more arguments.";
	}

	@Override
	public int requiredLevel() {
		return 1; // Operator level
	}

	@Override
	public String help() {
		return "/effect <username> <effectname><br>"
		        + "  Add an effect to an entity";
	}

}
