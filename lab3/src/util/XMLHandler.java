package util;

import data.Parallelepiped;
import data.Pyramid;
import data.Shape;
import data.Sphere;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLHandler {

    private Document document;

    private Element root;
    private final String rootName = "shapes";
    private final String nodeName = "shape";

    public XMLHandler() throws
            ParserConfigurationException {
        DocumentBuilderFactory documentFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder =
                documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
        root = document.createElement(rootName);
        document.appendChild(root);
    }

    public XMLHandler(File xmlFile) throws
            ParserConfigurationException,
            IOException,
            SAXException {
        load(xmlFile);
    }

    private Shape getShape(Node e) {
        String shapeType = e.getAttributes().item(0).getNodeValue();
        switch (shapeType) {
            case Parallelepiped.SHAPE_NAME -> {
                Node child = e.getFirstChild();
                double lenX = Double.parseDouble(child.getTextContent());
                child = child.getNextSibling();
                double lenY = Double.parseDouble(child.getTextContent());
                child = child.getNextSibling();
                double lenZ = Double.parseDouble(child.getTextContent());
                return new Parallelepiped(lenX, lenY, lenZ);
            }
            case Sphere.SHAPE_NAME -> {
                Node child = e.getFirstChild();
                double r = Double.parseDouble(child.getTextContent());
                return new Sphere(r);
            }
            case Pyramid.SHAPE_NAME -> {
                Node child = e.getFirstChild();
                double height = Double.parseDouble(child.getTextContent());
                child = child.getNextSibling();
                double square = Double.parseDouble(child.getTextContent());
                return new Pyramid(square, height);
            }
        }
        return null;
    }

    public ArrayList<Shape> getShapes() {
        ArrayList<Shape> shapes = new ArrayList<>();
        Node shape = root.getFirstChild().getFirstChild();
        System.out.println(shape);
        while (shape.getNextSibling() != null)
            shapes.add(getShape(shape));
        }
    }

    public void addShapes(ArrayList<Shape> shapes) {
        for (Shape shape : shapes) {
            addShape(shape);
        }
    }

    private void addShape(Shape shape) {
        String shapeName = shape.getShapeName();
        Element e = document.createElement(nodeName);
        e.setAttribute(nodeName, shape.getShapeName());
        switch (shapeName) {
            case Parallelepiped.SHAPE_NAME -> {
                Element width = document.createElement("width");
                Element height = document.createElement("height");
                Element length = document.createElement("length");
                width.setTextContent(String.valueOf(((Parallelepiped) shape).getLenX()));
                height.setTextContent(String.valueOf(((Parallelepiped) shape).getLenY()));
                length.setTextContent(String.valueOf(((Parallelepiped) shape).getLenZ()));
                e.appendChild(width);
                e.appendChild(height);
                e.appendChild(length);
            }
            case Sphere.SHAPE_NAME -> {
                Element radius = document.createElement("radius");
                radius.setTextContent(String.valueOf(((Sphere) shape).getR()));
                e.appendChild(radius);
            }
            case Pyramid.SHAPE_NAME -> {
                Element height = document.createElement("height");
                Element square = document.createElement("square");
                height.setTextContent(String.valueOf(((Pyramid) shape).getH()));
                square.setTextContent(String.valueOf(((Pyramid) shape).getS()));
                e.appendChild(height);
                e.appendChild(square);
            }
        }
        root.appendChild(e);
    }

    public void load(File xmlFile) throws
            ParserConfigurationException,
            IOException,
            SAXException {
        DocumentBuilderFactory documentFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder =
                documentFactory.newDocumentBuilder();
        document = documentBuilder.parse(xmlFile);
    }

    public void save(File xmlFile) throws TransformerException {
        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer =
                transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(xmlFile);
        transformer.transform(domSource, streamResult);
        clear();
    }

    private void clear() {
        document.removeChild(root);
        root = document.createElement(rootName);
        document.appendChild(root);
    }

}
