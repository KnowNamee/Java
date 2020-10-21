package graphics;

import containers.Backpack;
import data.Parallelepiped;
import data.Pyramid;
import data.Shape;
import data.Sphere;
import exceptions.MyException;
import org.xml.sax.SAXException;
import util.XMLShapesHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public final class Window extends JFrame {

    private Backpack<Shape> backpack;

    private DefaultTableModel tableModel;

    private XMLShapesHandler xmlHandler = new XMLShapesHandler();

    private static final int screenWidth =
            Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int screenHeight =
            Toolkit.getDefaultToolkit().getScreenSize().height;
    private static final String[] columnNames = new String[] {"Shape", "Volume"};

    private Window() throws ParserConfigurationException { }

    public Window(Backpack<Shape> backpack) throws ParserConfigurationException {
        this.backpack = backpack;
        setDefaultSettings();
        addWidgets();
        addMenuBar();
    }

    private void setDefaultSettings() {
        setTitle("Backpacker");
        setSize(screenWidth / 3, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);
        setVisible(true);
    }

    private void drawTable() {
        for (int row = tableModel.getRowCount()-1; row >= 0 ; --row) {
            tableModel.removeRow(row);
        }
        String[][] newData = backpack.getData();
        for (int row = 0; row < newData.length; row++) {
            tableModel.addRow(newData[row]);
        }
    }

    private void addWidgets() {
        JButton btnAdd = new JButton("Add Shape");
        JButton btnDel = new JButton("Delete Shape");

        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn(columnNames[0]);
        tableModel.addColumn(columnNames[1]);
        table.setFocusable(false);
        table.setShowGrid(false);
        table.setSize(screenWidth / 2, screenHeight / 2);

        Container c = new Container();
        GridLayout l = new GridLayout(2, 1);
        c.setLayout(l);
        c.add(btnAdd);
        c.add(btnDel);

        btnAdd.addActionListener(e -> {
            AddShapeDialog dialogue = new AddShapeDialog(new String[] {
                    Parallelepiped.SHAPE_NAME,
                    Sphere.SHAPE_NAME,
                    Pyramid.SHAPE_NAME
            });
            ArrayList<String> data = dialogue.show();
            try {
                addShape(data);
                drawTable();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(
                        null,
                        exception.getMessage(),
                        "Warning!!!",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        btnDel.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                backpack.removeShape(selectedRow);
                drawTable();
            }
        });

        JScrollPane scroll = new JScrollPane(table);

        getContentPane().setLayout(new GridLayout(1, 2));
        add(scroll);
        add(c);

        revalidate();
        repaint();
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openXML = new JMenuItem("Open XML");
        JMenuItem saveXML = new JMenuItem("Save XML");

        openXML.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(
                    new File(System.getProperty("user.dir")));
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    setBackpack(xmlHandler.load(chooser.getSelectedFile()));
                    drawTable();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(
                            null,
                            exception.getMessage(),
                            "Warning!!!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            revalidate();
            repaint();
        });
        saveXML.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(
                    new File(System.getProperty("user.dir")));
            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    xmlHandler.save(chooser.getSelectedFile(), getBackpack());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(
                            null,
                            exception.getMessage(),
                            "Warning!!!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        fileMenu.add(openXML);
        fileMenu.add(saveXML);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        revalidate();
    }

    private void addShape(ArrayList<String> data) throws Exception {
        if (data.isEmpty()) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isEmpty()) {
                data.set(i, "0");
            }
        }
        String shapeName = data.get(0);
        Shape shape;
        switch (shapeName) {
            case Parallelepiped.SHAPE_NAME -> {
                shape = new Parallelepiped(
                        parseDouble(data.get(1)),
                        parseDouble(data.get(2)),
                        parseDouble(data.get(3)));
                backpack.addShape(shape);
            }
            case Sphere.SHAPE_NAME -> {
                shape = new Sphere(parseDouble(data.get(1)));
                backpack.addShape(shape);
            }
            case Pyramid.SHAPE_NAME -> {
                shape = new Pyramid(
                        parseDouble(data.get(1)),
                        parseDouble(data.get(2)));
                backpack.addShape(shape);
            }
        }
    }

    public Backpack<Shape> getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack<Shape> backpack) {
        this.backpack = backpack;
    }
}

