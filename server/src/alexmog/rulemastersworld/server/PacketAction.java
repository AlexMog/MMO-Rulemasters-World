package alexmog.rulemastersworld.server;

import alexmog.rulemastersworld.AccountConnection;

public interface PacketAction {
    public void run(AccountConnection connection, Object packet) throws Exception;
    public boolean needLoggedIn();
}
