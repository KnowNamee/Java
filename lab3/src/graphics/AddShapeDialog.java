package graphics;

import data.Parallelepiped;
import data.Pyramid;
import data.Sphere;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class AddShapeDialog extends CustomDialog {

    private JPanel panel;
    private String selectedItem = Parallelepiped.SHAPE_NAME;
    private JComboBox<String> comboBox;

    public AddShapeDialog(String[] shapes) {
        setDialogSettings("Add Shape",
                JOptionPane.PLAIN_MESSAGE,null, new String[]{"Add", "Cancel"},0);

        components = new ArrayList<>();
        comboBox = new JComboBox<>(shapes);
        panel = new JPanel();

        comboBox.addActionListener(e -> {
            selectedItem = (String)comboBox.getSelectedItem();
            setPanel();
        });

        addComponent(comboBox);
        addComponent(panel);

        setPanel();
    }

    private void setPanel() {
        switch (selectedItem) {
            case Parallelepiped.SHAPE_NAME -> setParallelepipedPanel();
            case Sphere.SHAPE_NAME -> setSpherePanel();
            case Pyramid.SHAPE_NAME -> setPyramidPanel();
        }
    }

    private JTextField getFormattedJTextField() {
        JTextField field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!('0' <= c && c <= '9') &&
                        c != KeyEvent.VK_BACK_SPACE &&
                        c != '.') {
                    e.consume();
                }
            }
        });
        return field;
    }

    private void setSpherePanel() {
        panel.removeAll();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(new JLabel("Enter sphere radius : "));
        panel.add(getFormattedJTextField());
        panel.revalidate();
        panel.repaint();
    }

    private void setPyramidPanel() {
        panel.removeAll();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(new JLabel("Enter pyramid height : "));
        panel.add(getFormattedJTextField());
        panel.add(new JLabel("Enter pyramid base area : "));
        panel.add(getFormattedJTextField());
        panel.revalidate();
        panel.repaint();
    }

    private void setParallelepipedPanel() {
        panel.removeAll();
        panel.setLayout(new GridLayout(6, 1));
        panel.add(new JLabel("Enter parallelepiped height : "));
        panel.add(getFormattedJTextField());
        panel.add(new JLabel("Enter parallelepiped width  : "));
        panel.add(getFormattedJTextField());
        panel.add(new JLabel("Enter parallelepiped length : "));
        panel.add(getFormattedJTextField());
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public ArrayList<String> show() {
        int optionType = JOptionPane.OK_CANCEL_OPTION;
        Object optionSelection = null;

        if(options.length != 0) {
            optionSelection = options[optionIndex];
        }

        int selection = JOptionPane.showOptionDialog(rootPane,
                components.toArray(), title, optionType, messageType, null,
                options, optionSelection);

        if (selection == 0) {
            ArrayList<String> data = new ArrayList<>();
            data.add((String)comboBox.getSelectedItem());
            for (Component c : panel.getComponents()) {
                if (c instanceof JTextField) {
                    JTextField field = (JTextField)c;
                    data.add(field.getText());
                }
            }
            return data;
        }
        return null;
    }
}