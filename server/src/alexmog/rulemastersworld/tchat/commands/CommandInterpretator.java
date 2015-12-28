package alexmog.rulemastersworld.tchat.commands;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.datacontainers.TchatCommands;
import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;

public class CommandInterpretator {
    public static Map<TchatCommands, TchatCommandRunner> mCommands = new HashMap<TchatCommands, TchatCommandRunner>();
    private HelpCommand mHelpRunner = new HelpCommand();
    
    public CommandInterpretator() {
        mCommands.put(TchatCommands.KICK, new KickCommand());
        mCommands.put(TchatCommands.INVOKE, new InvokeCommand());
        mCommands.put(TchatCommands.EFFECT, new EffectCommand());
        mCommands.put(TchatCommands.INFO, new InfoCommand());
    }
    
    public void interpretate(TchatCommands command, AccountConnection ac, String args) {
        Log.info(ac.getPlayer().getName() + "@" + ac.getRemoteAddressTCP() + " using command '" + command.name() + "' with args: " + args);
        TchatCommandRunner runner = null;
        if (command == TchatCommands.HELP) {
            runner = mHelpRunner;
        } else {
            runner = mCommands.get(command);
        }
        TchatMsgPacket ret = new TchatMsgPacket();
        ret.username = "Server";
        if (runner != null) {
            if (runner.requiredLevel() <= ac.getPlayer().getPermissionsLvl()) {
                ret.msg = runner.run(ac, args);
            } else {
                ret.msg = "You need more permissions to do this.";
            }
        } else {
            ret.msg = "Command not found.";
        }
        ac.sendTCP(ret);
    }
    
    public void interpretateConsole(String command, String args) {
        try {
            TchatCommandRunner runner = null;
            if (command.toLowerCase().equals("help")) {
                runner = mHelpRunner;
            } else {
                runner = mCommands.get(TchatCommands.valueOf(command.toUpperCase()));
            }
            if (runner != null) {
                try {
                    Log.info("Using command: " + command);
                    Log.info(runner.run(null, args).replace("<br>", "\n"));
                } catch (Exception e) {
                    Log.info("CommandError", e);
                }
            } else {
                Log.info("Command not found.");
            }
        } catch (Exception e) {
            Log.info("Command not found.");
        }
    }
}
