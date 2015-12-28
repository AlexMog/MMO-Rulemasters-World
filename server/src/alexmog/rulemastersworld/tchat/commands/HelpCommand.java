package alexmog.rulemastersworld.tchat.commands;

import alexmog.rulemastersworld.AccountConnection;

public class HelpCommand implements TchatCommandRunner {

    @Override
    public String run(AccountConnection account, String args) {
        String cmds = "Help:<br>";
        for (TchatCommandRunner r : CommandInterpretator.mCommands.values()) {
            cmds += r.help() + "<br>";
        }
        return cmds;
    }

    @Override
    public int requiredLevel() {
        return 0;
    }

    @Override
    public String help() {
        return null;
    }

}
