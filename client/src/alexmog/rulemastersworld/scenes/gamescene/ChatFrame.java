package alexmog.rulemastersworld.scenes.gamescene;

import org.lwjgl.Sys;

import alexmog.rulemastersworld.datacontainers.TchatCommands;
import alexmog.rulemastersworld.packets.tchat.TchatCommandPacket;
import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.datas.Translator;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.ResizableFrame;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;

public class ChatFrame extends ResizableFrame {
    private final StringBuilder sb;
    private final HTMLTextAreaModel textAreaModel;
    private final TextArea textArea;
    private final EditField editField;
    private final ScrollPane scrollPane;

    public ChatFrame() {
        setTitle("Chat");

        this.sb = new StringBuilder();
        this.textAreaModel = new HTMLTextAreaModel();
        this.textArea = new TextArea(textAreaModel);
        this.editField = new EditField();
        setPosition(10, 525);
        setSize(500, 200);

        editField.addCallback(new EditField.Callback() {
            public void callback(int key) {
                if(key == Event.KEY_RETURN) {
                    // cycle through 3 different colors/font styles
//                    appendRow("color"+curColor, editField.getText());
                	String msg = editField.getText();
                	if (msg.length() == 0) return;
                	if (msg.startsWith("/")) {
                		String cmd;
                		TchatCommandPacket p = new TchatCommandPacket();
                		int spaceIndex = msg.indexOf(' ');
                		if (spaceIndex > 0) {
                			cmd = msg.substring(1, spaceIndex);
                			if (msg.length() > spaceIndex) {
                				p.args = msg.substring(spaceIndex + 1);
                			}
                		} else {
                			cmd = msg.substring(1);
                		}
                		try {
                			p.command = TchatCommands.valueOf(cmd.toUpperCase());
                            Main.client.sendTCP(p);
                		} catch (Exception e) {
                			e.printStackTrace();
                			appendRow("red", "Client: Command not found.");
                		}
                	} else {
	                    TchatMsgPacket p = new TchatMsgPacket();
	                    p.msg = msg;
	                    Main.client.sendTCP(p);
                	}
                    editField.setText("");
                	//                    curColor = (curColor + 1) % 3;
                }
            }
        });

        textArea.addCallback(new TextArea.Callback() {
            public void handleLinkClicked(String href) {
                Sys.openURL(href);
            }
        });

        scrollPane = new ScrollPane(textArea);
        scrollPane.setFixed(ScrollPane.Fixed.HORIZONTAL);

        DialogLayout l = new DialogLayout();
        l.setTheme("content");
        l.setHorizontalGroup(l.createParallelGroup(scrollPane, editField));
        l.setVerticalGroup(l.createSequentialGroup(scrollPane, editField));

        add(l);

        appendRow("default", Translator.getInstance().getTranslated("GameScene.tchatwelcome"));
    }

    public void appendRow(String font, String text) {
        sb.append("<div style=\"word-wrap: break-word; font-family: ").append(font).append("; \">");
        // not efficient but simple
        for(int i=0,l=text.length() ; i<l ; i++) {
            char ch = text.charAt(i);
            switch(ch) {
                case '<':
                    if (text.startsWith("<br>", i)) {
                        sb.append("<br />");
                        i += 3;
                    } else {
                        sb.append("&lt;");
                    } break;
                case '>': sb.append("&gt;"); break;
                case '&': sb.append("&amp;"); break;
                case '"': sb.append("&quot;"); break;
                case ':':
                    if(text.startsWith(":)", i)) {
                        sb.append("<img src=\"smiley\" alt=\":)\"/>");
//                        sb.append(":)");
                        i += 1; // skip one less because of i++ in the for loop
                        break;
                    }
                    sb.append(ch);
                    break;
                case 'h':
                    if(text.startsWith("http://", i)) {
                        int end = i + 7;
                        while(end < l && isURLChar(text.charAt(end))) {
                            end++;
                        }
                        String href = text.substring(i, end);
                        sb.append("<a style=\"font: link\" href=\"").append(href)
                                .append("\" >").append(href)
                                .append("</a>");
                        i = end - 1; // skip one less because of i++ in the for loop
                        break;
                    }
                    // fall through:
                default:
                    sb.append(ch);
            }
        }
        sb.append("</div>");

        boolean isAtEnd = scrollPane.getMaxScrollPosY() == scrollPane.getScrollPositionY();

        textAreaModel.setHtml(sb.toString());

        if(isAtEnd) {
            scrollPane.validateLayout();
            scrollPane.setScrollPositionY(scrollPane.getMaxScrollPosY());
        }
    }

    private boolean isURLChar(char ch) {
        return (ch == '.') || (ch == '/') || (ch == '%') ||
                (ch >= '0' && ch <= '9') ||
                (ch >= 'a' && ch <= 'z') ||
                (ch >= 'A' && ch <= 'Z');
    }
}
