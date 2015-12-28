package alexmog.rulemastersworld.packets.hub;

public class GameLobbyQuitPacket {
    public enum ReasonType {
        QUIT,
        DESTROYED
    }
    
    public ReasonType reason;
    public int lobbyId, id;
    public String message;
}
