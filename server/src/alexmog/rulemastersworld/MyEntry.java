package alexmog.rulemastersworld;

public class MyEntry {
    private AccountConnection mConn;
    private Object mObject;

    public MyEntry(AccountConnection c, Object o) {
        mConn = c;
        mObject = o;
    }
    
    public AccountConnection getConnection() {
        return mConn;
    }
    
    public Object getPacket() {
        return mObject;
    }
}
