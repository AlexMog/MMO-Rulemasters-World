package alexmog.rulemastersworld.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.beans.TokenResponse;
import alexmog.rulemastersworld.frames.LoginFrame;

import com.google.gson.Gson;

public class LoginHelper {
    private static final String TOKEN_LINK = "http://mog-creations.com/api/account/gettoken";
    
    public static boolean login(String username, String password, StringBuffer error) {
        Thread.setDefaultUncaughtExceptionHandler(LoginFrame.uncaughtExceptionHandler);

        // Get the con token
        try {
            URL url = new URL(TOKEN_LINK);
            HttpURLConnection con;
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "EBA Client");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("username=" + username + "&password="
                    + password + "&gameid=" + Main.gameId);
            wr.flush();
            wr.close();
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
     
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            Gson gson = new Gson();
            TokenResponse r = gson.fromJson(response.toString(), TokenResponse.class);
            if (r.status == null || !r.status.equals("ok")) {
                error.append(r.msg);
                return false;
            }
            LoginFrame.token =  r.token;
        } catch (IOException e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot get connexion token.", "Network Error", 0);
        }
        return true;
    }
}
