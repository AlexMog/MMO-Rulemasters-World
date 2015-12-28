package alexmog.rulemastersworld.tchat.commands;

import alexmog.rulemastersworld.AccountConnection;

public interface TchatCommandRunner {
	public String run(AccountConnection account, String args);
	public int requiredLevel();
	public String help();
}
