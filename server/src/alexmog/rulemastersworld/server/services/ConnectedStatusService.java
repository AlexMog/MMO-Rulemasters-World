package alexmog.rulemastersworld.server.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.DelegatingPreparedStatement;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Main;

public class ConnectedStatusService extends TimerService {

    public ConnectedStatusService(long sleepTime) {
        super(sleepTime);
    }

    @Override
    public void onTimerOut() {
        synchronized(Main.clientList) {
            for (AccountConnection ac : Main.clientList) {
                try (
                    Connection con = Main.accountDb.getConnection();
                    DelegatingPreparedStatement st = (DelegatingPreparedStatement) con
                            .prepareStatement("UPDATE accounts SET last_connected = ? WHERE id = ?");
                ) {
                    st.setLong(1, System.currentTimeMillis());
                    st.setInt(2, ac.getAccountId());
                    st.executeUpdate();
                } catch (SQLException e) {
                    Log.info("AccountConnectedStatusUpdateFailed", e);
                }            
            }
            if (Main.clientList.size() > 0) {
                Log.info("Accounts Connected Status saved.");
            }
        }
    }

}
