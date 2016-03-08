package com.orasi.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.orasi.api.soapServices.core.SoapService;

public class XMLTools extends SoapService {

    public static Document addAttribute(Document doc, String nodeName,
	    String xpath) {
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;
	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException xpe) {
	    throw new RuntimeException("Xpath evaluation failed with xpath [ "
		    + xpath + " ] ", xpe.getCause());
	}

	// Ensure an element was found, if not then throw error and fail
	if (nList.item(0) == null)
	    throw new RuntimeException("No xpath was found with the path [ "
		    + xpath + " ] ");

	// Create new XML document based on XPath
	Element element = (Element) nList.item(0);
	element.setAttribute(nodeName, "");

	return doc;
    }

    public static Document addNamespace(Document doc, String namespace,
	    String xpath) {
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;
	String[] values = namespace.split(",");
	String namespaceName = values[0];
	String namespaceURL = values[1];
	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException xpe) {
	    throw new RuntimeException("Xpath evaluation failed with xpath [ "
		    + xpath + " ] ", xpe.getCause());
	}

	// Ensure an element was found, if not then throw error and fail
	if (nList.item(0) == null)
	    throw new RuntimeException("No xpath was found with the path [ "
		    + xpath + " ] ");

	// Create new XML document based on XPath
	Element element = (Element) nList.item(0);
	element.setAttributeNS("http://www.w3.org/2000/xmlns/", namespaceName,
		namespaceURL);

	return doc;
    }

    public static Document enterWhitepace(Document doc, String xpath) {
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;
	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException xpe) {
	    throw new RuntimeException("Xpath evaluation failed with xpath [ "
		    + xpath + " ] ", xpe.getCause());
	}

	// Ensure an element was found, if not then throw error and fail
	if (nList.item(0) == null)
	    throw new RuntimeException("No xpath was found with the path [ "
		    + xpath + " ] ");

	nList.item(0).setTextContent( "");

	return doc;
    }

    /**
     * @summary Takes an xpath and adds a node to the location of the xpath and
     *          name of tagName
     * @author Justin Phlegar
     * @version Created: 08/28/2014
     * @param doc
     *            Document: XML Document that will be updated
     * @param nodeName
     *            String: Name of the node to add to the XML Document
     * @param xpath
     *            String: Path in the XML to add the node
     * @throws XPathExpressionException
     *             Could not match evaluate xPath
     * @throws RuntimeException
     *             Could not match xPath to a node, element or attribute
     */
    public static Document addNode(Document doc, String nodeName, String xpath) {
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;

	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException xpe) {
	    throw new RuntimeException("Xpath evaluation failed with xpath [ "
		    + xpath + " ] ", xpe.getCause());
	}

	// Ensure an element was found, if not then throw error and fail
	if (nList.item(0) == null)
	    throw new RuntimeException("No xpath was found with the path [ "
		    + xpath + " ] ");

	// Create new XML document based on XPath
	Document dom = nList.item(0).getOwnerDocument();

	// Create a new Node with the given tag name
	Node node = dom.createElement(nodeName);

	// Add the new node structure to the previous parent node
	nList.item(0).appendChild(node);
	return doc;
    }

    /**
     * @summary Takes an xpath and return the value found
     * @author Justin Phlegar
     * @version Created: 08/28/2014
     * @param xpath
     *            String: xpath to evaluate
     * @throws XPathExpressionException
     *             Could not match xPath during evaluation
     * @throws RuntimeException
     *             Could not match xPath to a node, element or attribute
     */
    public static String getValueByXpath(Document doc, String xpath) {

	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;

	// Evaluate the xpath
	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException xpe) {
	    throw new RuntimeException("Xpath evaluation failed with xpath [ "
		    + xpath + " ] ", xpe.getCause());
	}

	// Ensure an element was found, if not then throw error and fail
	if (nList.item(0) == null)
	    throw new RuntimeException("No xpath was found with the path [ "
		    + xpath + " ] ");

	// If no errors, then return the value found
	return nList.item(0).getTextContent();
    }

    /**
     * @summary Load and XML file from an external location
     * @author Justin Phlegar
     * @version Created: 08/28/2014
     * @param xpath
     *            String: xpath to evaluate
     */
    public static Document loadXML(String inFile) {
	SOAPMessage soapMessage = null;
	MessageFactory messageFactory = null;
	try {
	    messageFactory = MessageFactory
		    .newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

	    soapMessage = messageFactory.createMessage(new MimeHeaders(),
		    new ByteArrayInputStream(inFile.getBytes()));
	} catch (SOAPException se) {
	    throw new RuntimeException("Failed to create a SOAP message",
		    se.getCause());
	} catch (IOException ioe) {
	    throw new RuntimeException("Could not find a file located at [ "
		    + inFile + " ]", ioe.getCause());
	}
	return makeXMLDocument(soapMessage);
    }

    public static Document loadXMLFromProject(String file) {
	SOAPMessage soapMessage = null;
	MessageFactory messageFactory = null;

	try {

	    URL fileURL = XMLTools.class.getResource(file);
	    messageFactory = MessageFactory
		    .newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
	    soapMessage = messageFactory.createMessage(new MimeHeaders(),
		    new FileInputStream(fileURL.getPath()));
	} catch (SOAPException se) {
	    throw new RuntimeException("Failed to create a SOAP message",
		    se.getCause());
	} catch (IOException ioe) {
	    throw new RuntimeException("Could not find a file located at [ "
		    + file + " ]", ioe.getCause());
	}

	return makeXMLDocument(soapMessage);
    }

    public static Document makeXMLDocument(SOAPMessage soapXML) {

	Document doc = null;

	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	try {
	    soapXML.writeTo(outputStream);
	    DocumentBuilderFactory factory = DocumentBuilderFactory
		    .newInstance();
	    factory.setNamespaceAware(false);
	    factory.setIgnoringElementContentWhitespace(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    doc = builder.parse(new ByteArrayInputStream(outputStream
		    .toByteArray()));
	} catch (ParserConfigurationException pce) {
	    throw new RuntimeException(
		    "Failed to create a Document Builder Factory",
		    pce.getCause());
	} catch (SAXException saxe) {
	    throw new RuntimeException("Failed to parse the xml",
		    saxe.getCause());
	} catch (IOException ioe) {
	    throw new RuntimeException("Failed to find the source XML",
		    ioe.getCause());
	} catch (SOAPException soape) {
	    throw new RuntimeException("Failed to SOAP Message to XML",
		    soape.getCause());
	}

	return doc;
    }

    public static Document makeXMLDocument(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			InputSource source = new InputSource(new ByteArrayInputStream(xml
					.toString().getBytes()));
			doc = builder.parse(source);
		} catch (ParserConfigurationException pce) {
			throw new RuntimeException(
					"Failed to create a Document Builder Factory",
					pce.getCause());
		} catch (SAXException saxe) {
			throw new RuntimeException("Failed to parse the xml",
					saxe.getCause());
		} catch (IOException ioe) {
			throw new RuntimeException("Failed to find the source XML",
					ioe.getCause());
		}

		doc.getDocumentElement().normalize();
		return doc;

    }

    public static Document removeNode(Document doc, String xpath) {
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression expr;
		NodeList nList = null;

		try {
			expr = xPath.compile(xpath);
			nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException xpe) {
			throw new RuntimeException("Failed remove node from XML",
					xpe.getCause());
		}

		Element element = (Element) nList.item(0);
		element.getParentNode().removeChild(element);

		doc.normalize();

		return doc;
    }

    public static Node removeComments(Node node) {
	if (node.getNodeType() == Node.COMMENT_NODE) {
	    node.getParentNode().removeChild(node);
	} else {
	    // check the children recursively
	    NodeList list = node.getChildNodes();
	    for (int i = 0; i < list.getLength(); i++) {
		removeComments(list.item(i));
	    }
	}
	node.normalize();
	return node;
    }

    public static Document removeWhiteSpace(Document doc) {
	XPath xp = XPathFactory.newInstance().newXPath();
	NodeList nl = null;
	try {
	    nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc,
		    XPathConstants.NODESET);
	} catch (XPathExpressionException xpe) {
	    throw new RuntimeException("Failed remove node from XML",
		    xpe.getCause());
	}

	for (int i = 0; i < nl.getLength(); ++i) {
	    Node node = nl.item(i);
	    node.getParentNode().removeChild(node);
	}
	return doc;
    }

    public static boolean validateXMLSchema(String uri, Document doc) {

	try {
	    SchemaFactory factory = SchemaFactory
		    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    Schema schema = factory.newSchema(new URL(uri));
	    Validator validator = schema.newValidator();
	    validator.validate(new DOMSource(doc));
	} catch (IOException | SAXException e) {
	    System.out.println("Exception: " + e.getMessage());
	    return false;
	}
	return true;
    }

    /**
     * @summary Main validation function that validates and reports findings
     * @author Justin Phlegar
     * @version Created: 08/28/2014
     * @param doc
     *            Document: XML Document to evalute
     * @param xpath
     *            String: xpath to evaluate
     * @param value
     *            String: Depending on value given, will validate the xpath node
     *            or attribute value, <br>
     * <br>
     *            <b>Value syntax expressions:</b> <br>
     *            <b>value="abc"</b> -- Indirectly states that the node value
     *            will be validated and expected to be "abc" <br>
     *            <b>value="value:abc"</b> -- Directly states that the node
     *            value will be validated and expected to be "abc" <br>
     *            <b>value="attribute:attrName,abc"</b> -- Directly states that
     *            the node attribute "attrName" will be validated and expected
     *            to be "abc"
     * 
     */
    public static boolean validateNodeValueByXPath(Document doc, String xpath,
	    String regexValue) {
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;
	String xPathValue = "";
	String errorMessage = "";

	// Find the node based on xpath expression
	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException xpe) {
	    errorMessage = "Failed to build xpath [ " + xpath
		    + " ]. Please check format.";
	    // throw new
	    // RuntimeException("Xpath evaluation failed with xpath [ " + xpath
	    // + " ] ", xpe.getCause());
	}

	// Ensure an element was found, if not then throw error and fail
	if (nList.item(0) == null && errorMessage.isEmpty()) {
	    errorMessage = "No xpath was found with the path [ " + xpath
		    + " ] ";
	    // throw new RuntimeException("No xpath was found with the path [ "
	    // + xpath + " ] ");
	}

	if (errorMessage.isEmpty()) {
	    // Handle prefix types
	    if (regexValue.trim().toLowerCase().contains("value:")) {

		// Node value was specifically stated. Find value of node to
		// validate based on xpath
		regexValue = regexValue.substring(regexValue.indexOf(":") + 1,
			regexValue.length()).trim();
		xPathValue = nList.item(0).getTextContent();
	    } else if (regexValue.trim().toLowerCase().contains("attribute")) {
		// Node attribute was specifically stated. Find attribute of
		// node to validate based on xpath and attribute name
		regexValue = regexValue.substring(0,
			regexValue.indexOf(":") + 1).trim();
		String[] attributeParams = regexValue.split(",");
		NamedNodeMap attr = nList.item(0).getAttributes();
		Node nodeAttr = attr.getNamedItem(attributeParams[0]);
		xPathValue = nodeAttr.getTextContent();
	    } else {
		// Default path. Get node value based on xpath
		xPathValue = nList.item(0).getTextContent();
	    }
	}

	Regex regex = new Regex();

	// Validate expected value with actual value and report in html table
	if (!errorMessage.isEmpty()) {
	    buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
		    + xpath + "</td>");
	    buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
		    + regexValue + "</td>");
	    buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
		    + errorMessage + "</td>");
	    buffer.append("<td style='width: 100px; color: red; text-align: center;'><b>Fail</b></td></tr>");
	} else if (regex.match(regexValue, xPathValue)) {
	    buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
		    + xpath + "</td>");
	    buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
		    + regexValue + "</td>");
	    buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
		    + xPathValue + "</td>");
	    buffer.append("<td style='width: 100px; color: green; text-align: center;'><b>Pass</b></td></tr>");
	} else {
	    buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
		    + xpath + "</td>");
	    buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
		    + regexValue + "</td>");
	    buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
		    + xPathValue + "</td>");
	    buffer.append("<td style='width: 100px; color: red; text-align: center;'><b>Fail</b></td></tr>");
	}
	// return boolean
	return regex.match(regexValue, xPathValue);
    }

    /**
     * @summary Validate XML Repsonse and reports findings
     * @author Justin Phlegar
     * @version Created: 08/28/2014
     * @param doc
     *            Document: XML Document to evalute
     * @param xpath
     *            String: xpath to evaluate
     * @param value
     *            String: Depending on value given, will validate the xpath node
     *            or attribute value, <br>
     * <br>
     *            <b>Value syntax expressions:</b> <br>
     *            <b>value="abc"</b> -- Indirectly states that the node value
     *            will be validated and expected to be "abc" <br>
     *            <b>value="value:abc"</b> -- Directly states that the node
     *            value will be validated and expected to be "abc" <br>
     *            <b>value="attribute:attrName,abc"</b> -- Directly states that
     *            the node attribute "attrName" will be validated and expected
     *            to be "abc"
     * 
     */
    @Override
    public boolean validateNodeValueByXPath(Document doc, Object[][] scenarios) {
	boolean status = true;
	buffer.append("<table border='1' width='100%'>");
	buffer.append("<tr><td style='width: 100px; color: black; text-align: center;'><b>XPath</b></td>");
	buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Regex</b></td>");
	buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Value</b></td>");
	buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Status</b></td></tr>");
	for (int x = 0; x < scenarios.length; x++) {
	    if (!validateNodeValueByXPath(doc, scenarios[x][0].toString(),
		    scenarios[x][1].toString())) {
		status = false;
	    }
	}
	buffer.append("</table>");
	Reporter.log(buffer.toString());
	return status;
    }

    /**
     * @summary Update an XPath node or attribute based on the value. The value
     *          is not limited to simple values, but may also call various
     *          functions by adding "fx:" as a prefix. Please see
     *          {@link #handleValueFunction} for more information
     * @author Justin Phlegar
     * @version Created: 08/28/2014
     * @param xpath
     *            String: xpath to evaluate
     * @param value
     *            String: Depending on value given, will update the xpath value,
     *            attribute, or call a separate function. <br>
     * <br>
     *            <b>Value syntax expressions:</b> <br>
     *            <b>value="abc"</b> -- Indirectly states that the node value
     *            will be set as "abc" <br>
     *            <b>value="value:abc"</b> -- Directly states that the node
     *            value will be set as "abc" <br>
     *            <b>value="attribute:attrName,abc"</b> -- Directly states that
     *            the node attribute "attrName" will be set as "abc" <br>
     *            <b>value="fx:funcName"</b> -- Will call the function
     *            "funcName" to be handled in {@link #handleValueFunction}
     * @throws XPathExpressionException
     *             Could not match evaluate xPath
     * @throws RuntimeException
     *             Could not match xPath to a node, element or attribute
     */
    public static void setRequestNodeValueByXPath(Document doc, String xpath,
	    String value) {
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;
	// Document doc = getRequestDocument();
	// Element element = (Element) doc.getElementsByTagName("pmtInfo");
	// Find the node based on xpath expression
	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException xpe) {
	    throw new RuntimeException("Xpath evaluation failed with xpath [ "
		    + xpath + " ] ", xpe.getCause());
	}

	// Ensure an element was found, if not then throw error and fail
	if (nList.item(0) == null) {
	    throw new RuntimeException("No xpath was found with the path [ "
		    + xpath + " ] ");
	}

	System.out.println("XPATH: " + xpath + " || VALUE: " + value);

	if (value.isEmpty() || value == null) {
	    throw new RuntimeException(
		    "The value for the following xpath was blank: " + xpath);
	}

	// Handle prefix types
	if (value.trim().toLowerCase().contains("value:")) {

	    // Node value was specifically stated. Update node value
	    value = value.substring(value.indexOf(":") + 1, value.length())
		    .trim();

	    // Handle function if necessary
	    if (value.contains("fx:")) {
		value = handleValueFunction(doc, value, xpath);
	    }

	    // If a prior function call previous updated the XML, nothing more
	    // is needed.
	    if (!value.equalsIgnoreCase("XMLUpdated")) {
		nList.item(0).setTextContent(value);
	    }

	} else if (value.trim().toLowerCase().contains("attribute")) {

	    // Node attribute was specifically stated. Determine the attribute
	    // to find, then update the attribute
	    // value = value.substring(value.indexOf(":") +
	    // 1,value.length()).trim();
	    // String[] attributeParams = value.split(";");

	    // Handle function if necessary
	    if (value.contains("fx:")) {
		value = handleValueFunction(doc, value, xpath);
	    }

	    // If a prior function call previous updated the XML, nothing more
	    // is needed.
	    if (value.equalsIgnoreCase("XMLUpdated")) {
		// Find the attribute and set for editting
		NamedNodeMap attr = nList.item(0).getAttributes();
		Node nodeAttr = attr.getNamedItem(value);

		if (!value.equalsIgnoreCase("XMLUpdated")) {
		    // Update attribute
		    nodeAttr.setTextContent(value);
		}
	    }
	} else {
	    // Default path. Update node value based on xpath
	    // Handle function if necessary
	    if (value.contains("fx:")) {
		value = handleValueFunction(doc, value, xpath);
	    }

	    // If a prior function call previous updated the XML, nothing more
	    // is needed.
	    if (!value.equalsIgnoreCase("XMLUpdated")) {
		nList.item(0).setTextContent(value);
	    }
	}

	// Store changes
	setRequestDocument(doc);
    }

    /**
     * @summary Call functions during setting of the xpath
     * @author Justin Phlegar
     * @version Created: 08/28/2014
     * @param xpath
     *            String: Xpath to run the function on
     * @param function
     *            String: function to call <br>
     * <br>
     *            <b>Supported Functions:</b> <br>
     *            <b>value="fx:addnode"</b> -- Add a new node at Xpath position.
     *            Expects "Node:nodeName" where nodeName will be the name given
     *            to the node <br>
     *            <b>value="fx:getdatetime"</b> -- Set date and time in a format
     *            accepted by XML. Expects "DaysOut:x" where x is the number of
     *            days out <br>
     *            <b>value="fx:getdate"</b> -- Set date in a format accepted by
     *            XML. Expects "DaysOut:x" where x is the number of days out <br>
     *            <b>value="fx:removenode"</b> -- Remove a node at Xpath
     *            position. <br>
     *            <b>value="fx:randomnumber"</b> -- Set a string of random
     *            numbers. Expects "Node:x" where x is the length of the string
     *            to output <br>
     *            <b>value="fx:randomstring"</b> -- Set a string of random
     *            characters. Expects "Node:x" where x is the length of the
     *            string to output <br>
     *            <b>value="fx:randomalphanumeric"</b> -- Set a string of random
     *            numbers and characters. Expects "Node:x" where x is the length
     *            of the string to output
     */
    private static String handleValueFunction(Document doc, String function,
	    String xpath) {
	String[] params = function.split(";");
	String command = params[0];
	String[] length = new String[2];
	String[] tagName = new String[2];
	String[] daysOut = new String[2];

	switch (command.toLowerCase()) {
	case "fx:blank":
	case "fx:space":
	case "fx:empty":
	    XMLTools.enterWhitepace(doc, xpath);
	    return "XMLUpdated";
	    
	case "fx:getdatetime":
	    daysOut = params[1].split(":");
	    if (daysOut[0].trim().equalsIgnoreCase("DaysOut")) {
		return Randomness.generateCurrentXMLDatetime(Integer
			.parseInt(daysOut[1]));
	    } else {
		// report error
	    }

	case "fx:getdate":
	    daysOut = params[1].split(":");
	    if (daysOut[0].trim().equalsIgnoreCase("DaysOut")) {
		return Randomness.generateCurrentXMLDatetime(Integer
			.parseInt(daysOut[1]));
	    } else {
		// report error
	    }
	case "fx:addnode":
	    tagName = params[1].split(":");
	    if (tagName[0].trim().equalsIgnoreCase("Node")) {
		setRequestDocument(XMLTools.addNode(doc, tagName[1].trim(),
			xpath));
	    } else {
		// report error
	    }
	    return "XMLUpdated";

	case "fx:addattribute":
	    tagName = params[1].split(":", 2);
	    if (tagName[0].trim().equalsIgnoreCase("attribute")) {
		setRequestDocument(XMLTools.addAttribute(doc,
			tagName[1].trim(), xpath));
	    } else {
		// report error
	    }
	    return "XMLUpdated";

	case "fx:addnamespace":
	    tagName = params[1].split(":", 2);
	    if (tagName[0].trim().equalsIgnoreCase("namespace")) {
		setRequestDocument(XMLTools.addNamespace(doc,
			tagName[1].trim(), xpath));
	    } else {
		// report error
	    }
	    return "XMLUpdated";

	case "fx:removenode":
	    setRequestDocument(XMLTools.removeNode(doc, xpath));
	    return "XMLUpdated";
	    /*
	     * case "fx:dbquery": break;
	     * 
	     * case "fx:dbresult": break;
	     */

	case "fx:randomnumber":
	    length = params[1].split(":");
	    if (length[0].trim().equalsIgnoreCase("Node")) {
		return Randomness.randomNumber(Integer.parseInt(length[1]));
	    } else {
		// report error
	    }

	case "fx:randomstring":
	    length = params[1].split(":");
	    if (length[0].trim().equalsIgnoreCase("Node")) {
		return Randomness.randomString(Integer.parseInt(length[1]));
	    } else {
		// report error
	    }

	case "fx:randomalphanumeric":
	    length = params[1].split(":");
	    if (length[0].trim().equalsIgnoreCase("Node")) {
		return Randomness.randomAlphaNumeric(Integer
			.parseInt(length[1]));
	    } else {
		// report error
	    }

	case "fx:messageid":
	    return Randomness.generateMessageId();

	default:
	    throw new RuntimeException("The command [" + command
		    + " ] is not a valid command");
	}
    }

    public static boolean validateNodeContainsValueByXPath(Document doc,
	    String xpath, String testValue) {
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;
	int element = 0;
	boolean isContained = false;

	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException e1) {
	    throw new RuntimeException("Xpath expression '" + xpath
		    + "' did not exist.");
	}

	// Iterate through all nodes in the list
	do {
	    // Test to see if the test value is found in the same node structure
	    // as the locator value
	    if (nList.item(element).getTextContent().toLowerCase()
		    .contains(testValue.toLowerCase())) {
		isContained = true;
	    }
	    element++;
	    if (element == nList.getLength() && !isContained) {
		Reporter.log("The test value [" + testValue
			+ "] was not contained in any nodes", true);
		throw new RuntimeException("The test value [" + testValue
			+ "] was not contained in any nodes");
	    }
	} while (element < nList.getLength() && !isContained);

	return isContained;
    }

    public static boolean validateNodeContainsValueByXPathAndLocatorValue(
	    Document doc, String xpath, String locatorValue, String testValue) {
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	XPathExpression expr;
	NodeList nList = null;
	int element = 0;
	boolean isContained = false;

	try {
	    expr = xPath.compile(xpath);
	    nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	} catch (XPathExpressionException e1) {
	    throw new RuntimeException("Xpath expression '" + xpath
		    + "' did not exist.");
	}

	// Iterate through all nodes in the list
	do {
	    // Test to see if the locator value is found
	    if (nList.item(element).getTextContent().toLowerCase()
		    .contains(locatorValue.toLowerCase())) {
		// Test to see if the test value is found in the same node
		// structure as the locatro value
		if (nList.item(element).getTextContent().toLowerCase()
			.contains(testValue.toLowerCase())) {
		    isContained = true;
		} else {
		    Reporter.log("The locator value [" + locatorValue
			    + "] was found, but the test value [" + testValue
			    + "] was not contained in the child nodes", true);
		    throw new RuntimeException("The locator value ["
			    + locatorValue
			    + "] was found, but the test value [" + testValue
			    + "] was node contained in the child nodes");
		}
	    }
	    element++;
	} while (element < nList.getLength() && !isContained);

	return isContained;
    }
}
