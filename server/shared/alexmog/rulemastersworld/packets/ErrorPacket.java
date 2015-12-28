package alexmog.rulemastersworld.packets;

public class ErrorPacket {
    public enum ErrorType {
        AUTHENTICATION_FAILED,
        BAD_VERSION,
        SERVER_ERROR,
        NOT_AUTHENTICATED,
        ALREADY_IN_LOBBY,
        MAP_NOT_FOUND,
        LOBBY_NOT_FOUND,
        LOBBY_FULL,
        SPELL_NOT_LEARNED
    }
    public ErrorType status;
    public String message;
}
