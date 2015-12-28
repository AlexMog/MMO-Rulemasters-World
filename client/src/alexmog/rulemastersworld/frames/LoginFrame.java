package alexmog.rulemastersworld.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.newdawn.slick.AppGameContainer;

import alexmog.rulemastersworld.packets.Network;
import alexmog.rulemastersworld.utils.log.formatters.SimpleFormatter;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.beans.TokenResponse;
import alexmog.rulemastersworld.client.ClientListener;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.google.gson.Gson;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = -8821225989386753062L;
    private static final String TOKEN_LINK = "http://mog-creations.com/api/account/gettoken";
    private JTextField txtLogin;
    private JPasswordField pwdPassword;
    private JLabel lblStatus;
    private JButton btnSeConnecter;
    public static String token;
    public final static UncaughtExceptionHandler uncaughtExceptionHandler = new UncaughtExceptionHandler() {
        
        public void uncaughtException(Thread t, Throwable e) {
            e.printStackTrace();
            Runtime runtime = Runtime.getRuntime();
            String trace = "Crash catched on " + new Date(System.currentTimeMillis()) + "\n";
            trace += "================ JVM Infos ================\n";
            trace += "Java version = " + System.getProperty("java.version") + "\n";
            trace += "Free memory = " + runtime.freeMemory() + "\n";
            trace += "Total memory = " + runtime.totalMemory() + "\n";
            trace += "Max memory = " + runtime.maxMemory() + "\n";
            trace += "Available processors = " + runtime.availableProcessors() + "\n";
            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
            
            Map<String, String> systemProperties = runtimeBean.getSystemProperties();
            Set<String> keys = systemProperties.keySet();
            for (String key : keys) {
                String value = systemProperties.get(key);
                trace += (key + " = " + value + "\n");
            }
            trace += "============= Exception Message ===========\n";
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.close();
            trace += sw.toString();
            Main.crashReporter.setTrace(trace);
            Main.crashReporter.setVisible(true);
        }
    };
    
    public static void main(String[] arg) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        FileHandler fh = new FileHandler("logs/%u.%g.log",
                1024 * 1024, 10, false);
        fh.setFormatter(new SimpleFormatter());
        Log.getLogger().getLogger().addHandler(fh);
        new LoginFrame().setVisible(true);
    }

    public LoginFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Connection");
        setSize(450, 250);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JLabel lblConnexion = new JLabel("Connection");
        lblConnexion.setHorizontalAlignment(SwingConstants.CENTER);
        lblConnexion.setBounds(12, 13, 408, 16);
        getContentPane().add(lblConnexion);
        
        JLabel lblPseudo = new JLabel("Pseudo");
        lblPseudo.setHorizontalAlignment(SwingConstants.CENTER);
        lblPseudo.setBounds(12, 31, 408, 16);
        getContentPane().add(lblPseudo);
        
        txtLogin = new JTextField();
        txtLogin.setBounds(12, 50, 408, 22);
        getContentPane().add(txtLogin);
        txtLogin.setColumns(10);
        
        JLabel lblMotDePasse = new JLabel("Mot de passe");
        lblMotDePasse.setHorizontalAlignment(SwingConstants.CENTER);
        lblMotDePasse.setBounds(12, 76, 408, 16);
        getContentPane().add(lblMotDePasse);
        
        pwdPassword = new JPasswordField();
        pwdPassword.setBounds(12, 93, 408, 22);
        getContentPane().add(pwdPassword);
        
        btnSeConnecter = new JButton("Se connecter");
        btnSeConnecter.setBounds(12, 128, 408, 32);
        getContentPane().add(btnSeConnecter);
        
        lblStatus = new JLabel("");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setBounds(12, 173, 408, 16);
        getContentPane().add(lblStatus);
        btnSeConnecter.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                btnSeConnecter.setEnabled(false);
                new Thread("Connect") {
                    @Override
                    public void run() {
                        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
                        lblStatus.setText("Connection en cours...");

//                        Log.set(Log.LEVEL_DEBUG);
                        
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
                            wr.writeBytes("username=" + txtLogin.getText() + "&password="
                                    + new String(pwdPassword.getPassword()) + "&gameid=" + Main.gameId);
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
                                JOptionPane.showMessageDialog(null, r.msg, "Authentication error", 0);
                                btnSeConnecter.setEnabled(true);
                                return;
                            }
                            token = r.token;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Cannot get connexion token.", "Network Error", 0);
                            System.exit(1);
                        }
                        
                        Main.client = new Client();
//                        Main.hubClient = new Client();
                        
                        Network.register(Main.client);
//                        HubNetwork.register(Main.hubClient);
                        
                        Main.client.addListener(new ClientListener(Main.client));
//                        Main.hubClient.addListener(new ClientListener(Main.hubClient, new HubPacketsInterpretator(Main.hubClient)));
                        try {
                            Main.client.start();
                            //Main.hubClient.start();
                            Main.client.connect(5000, Network.host, Network.port);
                            //Main.hubClient.connect(5000, HubNetwork.host, HubNetwork.port);
                            lblStatus.setText("");
                            
                            setVisible(false);
                            //new LobbyFrame().setVisible(true);
                            AppGameContainer app;
                            try {
                                app = new AppGameContainer(Main.game);
                                app.setDisplayMode(Main.WIDTH, Main.HEIGHT, false);
                                app.setTargetFrameRate(Main.FPS);
                                app.setShowFPS(true);
                                app.setAlwaysRender(true);
                                app.setUpdateOnlyWhenVisible(false);
                                app.setForceExit(false);
                                app.start();
                                app.destroy();
                                setVisible(true);
                                btnSeConnecter.setEnabled(true);
                            } catch (Exception e) {
                                uncaughtExceptionHandler.uncaughtException(null, e);
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            Log.error("Server error", e1);
                            lblStatus.setText("Impossible de se connecter au serveur");
                            JOptionPane.showMessageDialog(null, "Connection impossible avec le serveur.", "Network Error", 0);
                            btnSeConnecter.setEnabled(true);
                        }
                    }
                }.start();
            }
        });
    }
    
    public void enableLogin() {
        btnSeConnecter.setEnabled(true);
    }
    
    public void setStatus(String status) {
        lblStatus.setText(status);
    }
}
