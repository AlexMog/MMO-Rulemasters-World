package alexmog.rulemastersworld.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import alexmog.rulemastersworld.frames.LoginFrame;
import alexmog.rulemastersworld.scenes.serverlist.ServerListWidget;

public class HttpUtils {
    private static final String GAMESERVERS_LINK = "http://mog-creations.com/api/game/getgameservers";
    
    public static String makeUrlRequest(String surl, String data, String method) throws IOException {
        URL url = new URL(surl);
        HttpURLConnection con;
        con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", "EBA Client");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
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
        return response.toString();
    }
    
    public static ServerListWidget.ServerContainer[] getServersList() throws IOException {
        String readed = makeUrlRequest(GAMESERVERS_LINK,
                "token=" + LoginFrame.token, "POST");
        Gson gson = new Gson();
        return gson.fromJson(readed, ServerListWidget.ServerContainer[].class);
    }
}
