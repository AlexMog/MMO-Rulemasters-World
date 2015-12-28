package alexmog.rulemastersworld.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.util.HttpUtils;

public class CrashcatchedFrame extends JFrame {
    private static final long serialVersionUID = -420187428664740855L;
    private JTextPane textPane;
    private JPanel panel;
    private static final String REPORT_LINK = "http://mog-creations.com/api/crash/report/";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CrashcatchedFrame frame = new CrashcatchedFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void setTrace(String trace) {
        textPane.setText(trace);
        panel.updateUI();
    }
    
    public String getTrace() {
        return textPane.getText();
    }

    /**
     * Create the frame.
     */
    public CrashcatchedFrame() {
        setTitle("Crash catched");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JLabel lblCrashReport = new JLabel("Crash report");
        lblCrashReport.setHorizontalAlignment(SwingConstants.CENTER);
        lblCrashReport.setBounds(12, 13, 758, 16);
        getContentPane().add(lblCrashReport);
        
        final JButton btnSendBugReport = new JButton("Send crash report");
        btnSendBugReport.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    
                    public void run() {
                        btnSendBugReport.setEnabled(false);
                        try {
                            String resp = HttpUtils.makeUrlRequest(REPORT_LINK + Main.gameId,
                                    "report=" + URLEncoder.encode(textPane.getText(), "UTF-8"), "POST");
                            JOptionPane.showMessageDialog(null, resp, "Thanks",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Report cannot be sent", "Error", 0);
                        }
                        System.exit(1);
                    }
                }).start();
            }
        });
        btnSendBugReport.setBounds(12, 410, 758, 25);
        getContentPane().add(btnSendBugReport);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 42, 758, 355);
        getContentPane().add(scrollPane);
        
        panel = new JPanel();
        scrollPane.setViewportView(panel);
        panel.setLayout(new BorderLayout(0, 0));
        
        textPane = new JTextPane();
        panel.add(textPane, BorderLayout.CENTER);
        textPane.setEditable(false);
    }
}
