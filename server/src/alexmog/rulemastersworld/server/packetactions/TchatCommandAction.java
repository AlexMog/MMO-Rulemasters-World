package alexmog.rulemastersworld.server.packetactions;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.packets.tchat.TchatCommandPacket;
import alexmog.rulemastersworld.server.PacketAction;
import alexmog.rulemastersworld.tchat.commands.CommandInterpretator;

public class TchatCommandAction implements PacketAction {
    private CommandInterpretator mInterpretator = new CommandInterpretator();

	public void run(AccountConnection connection, Object packet)
			throws Exception {
		TchatCommandPacket p = (TchatCommandPacket)packet;
		mInterpretator.interpretate(p.command, connection, p.args);
	}

	public boolean needLoggedIn() {
		return true;
	}

}
