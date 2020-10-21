package util;

import containers.Backpack;
import data.Parallelepiped;
import data.Pyramid;
import data.Shape;
import data.Sphere;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.crypto.dsig.XMLValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLShapesHandler {

    private static final String XSD_PATH = "template.xsd";

    public XMLShapesHandler() {}

    private Shape createShape(String xmlShapeName,
                              Double[] xmlParamValues) {
        return switch (xmlShapeName) {
            case Parallelepiped.SHAPE_NAME -> new Parallelepiped(xmlParamValues);
            case Sphere.SHAPE_NAME -> new Sphere(xmlParamValues);
            case Pyramid.SHAPE_NAME -> new Pyramid(xmlParamValues);
            default -> null;
        };
    }

    private void validate(Document document) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(XSD_PATH));
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }

    private Shape extractShape(Node xmlShape) {
        String xmlShapeName =
                xmlShape.getNodeName();
        NodeList xmlParams = xmlShape.getChildNodes();
        int size = xmlParams.getLength();
        Double[] xmlParamValues = new Double[size];
        for (int idx = 0; idx < xmlParams.getLength(); idx++) {
            xmlParamValues[idx] = Double.parseDouble(xmlParams.item(idx).getTextContent());
        }
        return createShape(xmlShapeName, xmlParamValues);
    }

    private Backpack<Shape> extractShapes(Document document) throws Exception {
        Node xmlBackpack = document.getFirstChild();
        double capacity = Double.parseDouble(xmlBackpack.getAttributes().item(0).getNodeValue());
        Backpack<Shape> backpack = new Backpack<>(capacity);
        NodeList xmlShapes = xmlBackpack.getChildNodes();
        for (int idx = 0; idx < xmlShapes.getLength(); idx++) {
            backpack.addShape(extractShape(xmlShapes.item(idx)));
        }
        return backpack;
    }

    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory =
                DocumentBuilderFactory.newInstance();
        return documentFactory.newDocumentBuilder();
    }

    public Backpack<Shape> load(File xmlFile) throws Exception {
        DocumentBuilder documentBuilder = getDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        validate(document);
        return extractShapes(document);
    }

    private Element fillXMLShape(Document document, Element element,
                                 String[] paramNames, Double[] paramValues) {
        for (int idx = 0; idx < paramNames.length; idx++) {
            Element child = document.createElement(paramNames[idx]);
            child.setTextContent(String.valueOf(paramValues[idx]));
            element.appendChild(child);
        }
        return element;
    }

    private Element makeXMLShape(Shape shape, Document document) {
        String shapeName = shape.getShapeName();
        Element element = document.createElement(shapeName);
        switch (shapeName) {
            case Parallelepiped.SHAPE_NAME -> {
                Parallelepiped s = (Parallelepiped) shape;
                return fillXMLShape(document, element,
                        Parallelepiped.PARAM_NAMES, s.getParamValues());
            }
            case Sphere.SHAPE_NAME -> {
                Sphere s = (Sphere) shape;
                return fillXMLShape(document, element,
                        Sphere.PARAM_NAMES, s.getParamValues());
            }
            case Pyramid.SHAPE_NAME -> {
                Pyramid s = (Pyramid) shape;
                return fillXMLShape(document, element,
                        Pyramid.PARAM_NAMES, s.getParamValues());
            }
        }
        return null;
    }

    public void save(File xmlFile, Backpack<Shape> backpack) throws TransformerException,
            ParserConfigurationException {
        DocumentBuilder documentBuilder = getDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("backpack");
        root.setAttribute("capacity", String.valueOf(backpack.getCapacity()));
        document.appendChild(root);

        for (Shape shape : backpack.getShapes()) {
            root.appendChild(makeXMLShape(shape, document));
        }

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer =
                transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(xmlFile);
        transformer.transform(domSource, streamResult);
    }
}
