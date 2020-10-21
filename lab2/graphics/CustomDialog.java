package graphics;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CustomDialog {
    protected List<JComponent> components;

    protected String title = "Default title";
    protected int messageType = JOptionPane.PLAIN_MESSAGE;
    protected JRootPane rootPane = null;
    protected String[] options = new String[] {"OK"};
    protected int optionIndex = 0;

    public CustomDialog() {}

    void setDialogSettings(String title,
                           int messageType,
                           JRootPane rootPane,
                           String[] options,
                           int optionIndex) {
        this.title = title;
        this.messageType = messageType;
        this.rootPane = rootPane;
        this.options = options;
        this.optionIndex = optionIndex;
    }

    public void addComponent(JComponent component) {
        components.add(component);
    }

    public void addMessageText(String messageText) {
        JLabel label = new JLabel("<html>" + messageText + "</html>");
        components.add(label);
    }

    public static String getLineBreak() {
        return "<br>";
    }

    abstract public ArrayList<String> show();

}
