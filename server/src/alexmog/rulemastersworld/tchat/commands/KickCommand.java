package alexmog.rulemastersworld.tchat.commands;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Main;

public class KickCommand implements TchatCommandRunner {

	public String run(AccountConnection account, String args) {
		String ret = null;
		if (args != null && args.length() > 0) {
			synchronized(Main.clientList) {
	            for (AccountConnection a : Main.clientList) {
	                if (a.getPlayer().getName().equalsIgnoreCase(args)) {
	                	a.close();
	                	ret = "User kicked.";
	                	break;
	                }
	            }
	        }
			if (ret == null) {
				ret = "User not found.";
			}
		} else {
			ret = "Need more arguments.";
		}
		return ret;
	}

	public int requiredLevel() {
		return 2;
	}

	@Override
	public String help() {
		return "/kick <username><br>  Kick a player";
	}

}
