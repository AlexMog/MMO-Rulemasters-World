package alexmog.rulemastersworld.scenes.loginscene;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import alexmog.rulemastersworld.datas.Translator;
import alexmog.rulemastersworld.scenes.LoginScene;
import alexmog.rulemastersworld.util.LoginHelper;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.CallbackWithReason;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Label.CallbackReason;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;

public class LoginSceneWidget extends DialogLayout {
    private final DialogLayout loginPanel;
    private final EditField efName;
    private final EditField efPassword;
    private final Button btnLogin;
    private final Label lblStatus;
    private final LoginScene mLoginScene;
    private boolean mLogged = false;

    public LoginSceneWidget(LoginScene loginScene) {
        mLoginScene = loginScene;
        loginPanel = this;
        loginPanel.setTheme("loginscenewidget");
        
        lblStatus = new Label("");
        
        efName = new EditField();
        efName.addCallback(new Callback() {
            public void callback(int key) {
                if(key == Event.KEY_RETURN) {
                    efPassword.requestKeyboardFocus();
                }
            }
        });
        
        efPassword = new EditField();
        efPassword.setPasswordMasking(true);
        efPassword.addCallback(new Callback() {
            public void callback(int key) {
                if(key == Event.KEY_RETURN) {
                    emulateLogin();
                }
            }
        });
        
        Label lName = new Label(Translator.getInstance().getTranslated("LoginScreen.username"));
        lName.setLabelFor(efName);
        
        Label lPassword = new Label(Translator.getInstance().getTranslated("LoginScreen.password"));
        lPassword.setLabelFor(efPassword);
        
        btnLogin = new Button(Translator.getInstance().getTranslated("LoginScreen.login"));
        btnLogin.addCallback(new Runnable() {
            public void run() {
                emulateLogin();
            }
        });
        
        Label lWebsite = new Label(Translator.getInstance().getTranslated("LoginScreen.registernow"));
        lWebsite.addCallback(new CallbackWithReason<Label.CallbackReason>() {
            
            public void callback(CallbackReason arg0) {
                try {
                    Desktop.getDesktop().browse(new URI("http://mog-creations.com/register"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        
        DialogLayout.Group hLabels = loginPanel.createParallelGroup(lName, lPassword);
        DialogLayout.Group hFields = loginPanel.createParallelGroup(efName, efPassword);
        DialogLayout.Group hBtn = loginPanel.createSequentialGroup()
                .addGap()   // right align the button by using a variable gap
                .addWidget(btnLogin);
        DialogLayout.Group hStatus = loginPanel.createSequentialGroup()
                .addGap()
                .addWidget(lblStatus);
        DialogLayout.Group hWebsite = loginPanel.createSequentialGroup()
                .addGap()
                .addWidget(lWebsite);
        
        loginPanel.setHorizontalGroup(loginPanel.createParallelGroup()
                .addGroup(loginPanel.createSequentialGroup(hLabels, hFields))
                .addGroup(hStatus)
                .addGroup(hWebsite)
                .addGroup(hBtn));
        loginPanel.setVerticalGroup(loginPanel.createSequentialGroup()
                .addGroup(loginPanel.createParallelGroup(lName, efName))
                .addGroup(loginPanel.createParallelGroup(lPassword, efPassword))
                .addWidget(lblStatus)
                .addWidget(lWebsite)
                .addWidget(btnLogin));
        
//        add(loginPanel);
    }

    @Override
    protected void layout() {
        if (mLogged) {
            lblStatus.setText("");
            mLoginScene.getGame().enterState(4);
            mLogged = false;
        }
        // login panel is centered
        loginPanel.adjustSize();
        loginPanel.setPosition((getParent().getWidth() - loginPanel.getWidth()) / 2,
                (getParent().getHeight() - loginPanel.getHeight()) / 2);
    }
    
    void emulateLogin() {
        GUI gui = getGUI();
        if(gui != null) {
            new Thread(new Runnable() {
                
                public void run() {
                    lblStatus.setText(Translator.getInstance().getTranslated("LoginScreen.connecting"));
                    // step 1: disable all controls
                    efName.setEnabled(false);
                    efPassword.setEnabled(false);
                    btnLogin.setEnabled(false);
                    
                    // step 2: get name & password from UI
                    String username = efName.getText();
                    String password = efPassword.getText();
                    
                    StringBuffer error = new StringBuffer();
                    boolean result = LoginHelper.login(username, password, error);
                    
                    if (!result) {
                        lblStatus.setText("ERROR: " + error);                        
                    } else {
                        mLogged = true;
                    }
                    
                    lblStatus.getParent().invalidateLayout();
                    efName.setEnabled(true);
                    efPassword.setEnabled(true);
                    efPassword.setText("");
                    efPassword.requestKeyboardFocus();
                    btnLogin.setEnabled(true);
                }
            }).start();
        }
    }
}

