package com.orasi.api.soapServices.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.xmlbeans.XmlException;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.support.SoapUIException;
import com.orasi.utils.XMLTools;

public abstract class SoapService{

	private String strEnvironment = null;
	private String strServiceURL = null;
	private String strResponseURI = null;
	private String intResponseStatusCode = null;
	private String responseTemplate = null;
	private static Document requestDocument = null;
	private static Document responseDocument = null;
	protected static StringBuffer buffer = new StringBuffer();

	/*****************************
	 **** Start Gets and Sets ****
	 *****************************/

	/**
	 * @summary Takes the current Request XML Document stored in memory and
	 *          return it as a string for simple output
	 * @precondition Requires XML Document to be loaded by using
	 *               {@link #setRequestDocument}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Will return the current Request XML as a string
	 */
	public String getRequest() {
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException("Failed to create XML Transformer");
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		try {
			transformer.transform(new DOMSource(getRequestDocument()),
					new StreamResult(sw));
		} catch (TransformerException e) {
			throw new RuntimeException(
					"Failed to transform Request XML Document. Ensure XML Document has been successfully loaded.");
		}
		return sw.toString();
	}

	/**
	 * @summary Takes the current Response XML Document stored in memory and
	 *          return it as a string for simple output
	 * @precondition Requires XML Document to be loaded by using
	 *               {@link #setResponseDocument}
	 * @author Justin Phlegar
	 * @version Created 08/28/2014
	 * @return Will return the current Response XML as a string
	 */
	public String getResponse() {
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException("Failed to create XML Transformer");
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		try {
			transformer.transform(new DOMSource(getResponseDocument()),
					new StreamResult(sw));
		} catch (TransformerException e) {
			throw new RuntimeException(
					"Failed to transform Response XML Document. Ensure XML Document has been successfully loaded.");
		}
		return sw.toString();
	}

	/**
	 * @summary Returns the environment under test. Current accepted
	 *          environments are (examples): <br>
	 *          Dev - Developer server or environment <br>
	 *          Test - Integrated Testing Environment <br>
	 *          Stage - Pre-Production Staging Environment
	 * @precondition The environment under test needs to be set by
	 *               {@link #setEnvironment(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the environment under test as a String
	 */
	public String getEnvironment() {
		return strEnvironment;
	}

	/**
	 * @summary After a service request has been sent, if the Status code of the
	 *          response has been stored, then this function can be used to
	 *          retrieve that status code
	 * @precondition The Response Status Code needs to be set by
	 *               {@link #setRepsonseStatusCode(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the Status Code of a response as a String
	 */
	public String getResponseStatusCode() {
		return intResponseStatusCode;
	}

	/**
	 * @summary Return the URL of the service under test
	 * @precondition The Service URL needs to be set by
	 *               {@link #setServiceURL(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the Service URL as a String
	 */
	public String getServiceURL() {
		return strServiceURL;
	}

	/**
	 * @summary This is used to retrieve the current XML Document as it is in
	 *          memory.
	 * @precondition The XML Document needs to be set by
	 *               {@link #setRequestDocument(Document)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the stored Request XML as a Document
	 */
	protected Document getRequestDocument() {
		return requestDocument;
	}

	/**
	 * @summary This is used to retrieve the current Response Document as it is
	 *          in memory
	 * @precondition The Response Document needs to be set by
	 *               {@link #setResponseDocument(Document)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the stored Response XML as a Document object
	 */
	protected static Document getResponseDocument() {
		return responseDocument;
	}

	/**
	 * @summary This is used to retrieve the current Response URI string as it
	 *          is in memory
	 * @precondition Requires XML Document to be loaded by using
	 *               {@link #setResponseBaseURI(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the stored Response URI as a String
	 */
	protected String getResponseBaseURI() {
		return strResponseURI;
	}

	/**
	 * @summary This is used to retrieve the current Response XML Template as it
	 *          is in memory
	 * @precondition Requires XML Document to be loaded by using
	 *               {@link #setResponseTemplate(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Will return the Response XML Template as a String
	 */
	protected String getResponseTemplate() {
		return responseTemplate;
	}

	/**
	 * @summary Sets the environment under test. Current accepted environments
	 *          are(examples): <br>
	 *          Dev - Developer server or environment <br>
	 *          Test - Integrated Testing Environment <br>
	 *          Stage - Pre-Production Staging Environment
	 * <br>
	 *          Can be retrieved by {@link #getEnvironment()}
	 * @precondition The environment under test must be one of the environments
	 *               listed above.
	 * @author Justin Phlegar
	 * @version Created 08/28/2014
	 * @param environment
	 *            String: Environment under test
	 */
	protected void setEnvironment(String environment) {
		strEnvironment = environment;
	}

	/**
	 * @summary Used to store the XML file as a Document object in memory. Can
	 *          be retrieved using {@link #getRequestDocument()}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param doc
	 *            Document XML file of the Request to be stored in memory
	 */
	protected static void setRequestDocument(Document doc) {
		requestDocument = doc;
	}

	/**
	 * @summary Used to store the XML file as a Document object in memory. Can
	 *          be retrieved using {@link #getRequestDocument()}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param code
	 *            String: Response error code
	 */
	protected void setRepsonseStatusCode(String code) {
		intResponseStatusCode = code;
	}

	/**
	 * @summary Set a Response XML Document to be stored in memory to be
	 *          retrieved and edited easily. Retrieve XML Document using
	 *          {@link #getResponseDocument()} or as a String using
	 *          {@link #getResponse()}
	 * @precondition Requires valid XML Document to be sent
	 * @author Justin Phlegar
	 * @version Created 08/28/2014
	 * @param doc
	 *            Document: XML file of the Response to be stored in memory
	 */
	protected void setResponseDocument(Document doc) {
		responseDocument = doc;
	}

	/**
	 * @summary Used to store the XML file of the base Soap Response file as a
	 *          Document object in memory. Can be retrieved using
	 *          {@link #getResponseTemplate()}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param response
	 *            String: Store the base XML response as a String
	 */
	protected void setResponseTemplate(String response) {
		responseTemplate = response;
	}

	/**
	 * @summary Used to store the main Namespace URI for a Response document.
	 *          Can be retrieved using {@link #getRequestDocument()}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param uri
	 *            String: Main Namespace URI of a response.
	 */
	protected void setResponseBaseURI(String uri) {
		strResponseURI = uri;
	}

	/**
	 * @summary Used to store URL of the Service Under Test in memory. Can be
	 *          retrieved using {@link #getServiceURL())}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param url
	 *            String: URL Endpoint of the Service Under Test
	 */
	protected void setServiceURL(String url) {
		strServiceURL = url;
	}

	/***************************
	 **** End Gets and Sets ****
	 ***************************/

	/*************************************
	 ********* Public Methods ************
	 *************************************/

	/**
	 * @summary Lazily check the response and return the value of the first
	 *          matching tag
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param tag
	 *            String: Finds and returns the value of the first tag it finds
	 *            with this tag name
	 */
	public String getFirstNodeValueByTagName(String tag) {
		// Get the response document from memory
		NodeList nList = getResponseDocument().getElementsByTagName(tag);
		return nList.item(0).getTextContent();
	}

	/**
	 * @summary Takes an xpath and return the value if found in the request
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 */
	public String getRequestNodeValueByXPath(String xpath) {
		return XMLTools.getValueByXpath(getRequestDocument(), xpath);
	}

	/**
	 * @summary Takes an xpath and return the value if found in the response
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 */
	public static String getResponseNodeValueByXPath(String xpath) {
		return XMLTools.getValueByXpath(getResponseDocument(), xpath);
	}

	/**
	 * @summary Find and open the excel file sent. If successful, look and find
	 *          the matching scenario name then return its xpath and value data.
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param file
	 *            String: File location either in the project or the hard disk
	 *            path
	 * @param scenario
	 *            String: Name of the scenario to poll data for
	 */
	protected Object[][] getTestScenario(String file, String scenario) {

		String[][] tabArray = null;
		try {

			// Get the file location from the project main/resources folder
			String filePath = getClass().getResource(file).getPath();

			// in case file path has a %20 for a whitespace, replace with actual
			// whitespace
			filePath = filePath.replace("%20", " ");

			// Open excel workbook to work and from file from the path
			Workbook workbook = Workbook.getWorkbook(new File(filePath));
			Sheet sheet = workbook.getSheet(0);
			int startRow, startCol, endRow, endCol, ci, cj;

			// Find the specific cell containing the scenario to run.
			Cell tableStart = sheet.findCell(scenario);
			startRow = tableStart.getRow();
			startCol = tableStart.getColumn();

			// Loop through cell in Regex column to find the first blank cell.
			// This is set as the last row
			for (int i = startRow + 1;; i++) {
				try {
					if (sheet.getCell(startCol + 1, i).getContents().toString()
							.equals("")) {
						endRow = i;
						break;
					}
				} catch (ArrayIndexOutOfBoundsException arrayError) {
					endRow = i;
					break;
				}

			}
			endCol = startCol + 3;

			// Create array based on size of columns and rows found
			tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
			ci = 0;

			// Feed results to the dual array
			for (int i = startRow + 1; i < endRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < endCol; j++, cj++) {
					tabArray[ci][cj] = sheet.getCell(j, i).getContents();
				}
			}
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe.getCause());
		} catch (BiffException be) {
			throw new RuntimeException(be.getCause());
		} catch (IOException ioe) {
			throw new RuntimeException("Unable to open file " + file + "\n"
					+ ioe.getCause());
		}
		return (tabArray);
	}

	/**
	 * @summary Takes the pre-built Request XML in memory and sends to the
	 *          service
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 */
	public SOAPMessage sendRequest() {
		SOAPMessage request = null;
		SOAPMessage response = null;
		SOAPConnectionFactory connectionFactory = null;
		SOAPConnection connection = null;
		SOAPBody responseBody = null;
		MessageFactory messageFactory = null;

		// Get the service endpoint from previously stored URL
		String url = getServiceURL();

		try {
			messageFactory = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			// Convert XML Request to SoapMessage
			InputStream in = new ByteArrayInputStream(getRequest().getBytes(Charset.defaultCharset()));
			request = messageFactory.createMessage(new MimeHeaders(),in);	

			request.writeTo(System.out);
			System.out.println();

			// Send out Soap Request to the endopoint
			connectionFactory = SOAPConnectionFactory.newInstance();

			connection = connectionFactory.createConnection();
			response = connection.call(request, url);

			// Normalize Response and get the soap body
			response.getSOAPBody().normalize();
			responseBody = response.getSOAPBody();
		} catch (UnsupportedOperationException uoe) {
			throw new RuntimeException(
					"Operation given did not match any operations in the service"
							+ uoe.getCause());
		} catch (SOAPException soape) {
			throw new RuntimeException(soape.getCause());
		} catch (IOException ioe) {
			throw new RuntimeException("Failed to read the request properly"
					+ ioe.getCause());
		}

		// Check for faults and report
		if (responseBody.hasFault()) {
			SOAPFault newFault = responseBody.getFault();
			setRepsonseStatusCode(newFault.getFaultCode());
			System.out
					.println("sendSoapReq FAULT:  " + newFault.getFaultCode());
		} else {
			setRepsonseStatusCode("200");
		}

		try {
			connection.close();

		} catch (SOAPException soape) {
			throw new RuntimeException(soape.getCause());
		}

		// Covert Soap Response to XML and set it as Response in memory
		Document doc = XMLTools.makeXMLDocument(response);
		doc.normalize();
		setResponseDocument(doc);
		setResponseBaseURI(responseBody.getNamespaceURI());
		System.out.println();
		System.out.println();
		System.out.println("Response");
		System.out.println(getResponse());
		return response;
	}	
	

	/**
	 * @summary Update multiple XPath nodes or attributes based on the value. The value
	 *          is not limited to simple values, but may also call various
	 *          functions by adding "fx:" as a prefix. Please see
	 *          {@link #handleValueFunction} for more information
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 * @param value
	 *            String: Depending on value given, will update the xpath value,
	 *            attribute, or call a separate function. 
	 *            <br><br><b>Value syntax expressions:</b>
	 *            <br><b>value="abc"</b>  -- Indirectly states that the node value will be set as "abc"
	 *            <br><b>value="value:abc"</b>  -- Directly states that the node value will be set as "abc"
	 *            <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be set as "abc"
	 *            <br><b>value="fx:funcName"</b> -- Will call the function "funcName" to be handled in {@link #handleValueFunction}
	 */
	public void setRequestNodeValueByXPath(String xpath, String value) {
			XMLTools.setRequestNodeValueByXPath(getRequestDocument(),xpath,value);
	}

	/**
	 * @summary Update multiple XPath nodes or attributes based on the value. The value
	 *          is not limited to simple values, but may also call various
	 *          functions by adding "fx:" as a prefix. Please see
	 *          {@link #handleValueFunction} for more information
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 * @param value
	 *            String: Depending on value given, will update the xpath value,
	 *            attribute, or call a separate function. 
	 *            <br><br><b>Value syntax expressions:</b>
	 *            <br><b>value="abc"</b>  -- Indirectly states that the node value will be set as "abc"
	 *            <br><b>value="value:abc"</b>  -- Directly states that the node value will be set as "abc"
	 *            <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be set as "abc"
	 *            <br><b>value="fx:funcName"</b> -- Will call the function "funcName" to be handled in {@link #handleValueFunction}
	 */
	public void setRequestNodeValueByXPath(Object[][] scenarios) {
		for (int x = 0; x < scenarios.length; x++) {
			XMLTools.setRequestNodeValueByXPath(getRequestDocument(),scenarios[x][0].toString(),
					scenarios[x][1].toString());
		}
	}

	/**
	 * @summary Validate XML Response and reports findings
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param doc Document: XML Document to evaluate
	 * @param xpath String: xpath to evaluate
	 * @param value String: Depending on value given, will validate the xpath node or attribute value,
	 *  		  	<br><br><b>Value syntax expressions:</b>
	 *	            <br><b>value="abc"</b>  -- Indirectly states that the node value will be validated and expected to be "abc"
	 *  	        <br><b>value="value:abc"</b>  -- Directly states that the node value will be validated and expected to be "abc"
	 *      	    <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be validated and expected to be "abc"
	 *            
	 */
	public boolean validateNodeValueByXPath(Document doc, Object[][] scenarios) {
		boolean status = true;
		buffer.setLength(0);
		buffer.append("<table border='1' width='100%'>");
		buffer.append("<tr><td style='width: 100px; color: black; text-align: center;'><b>XPath</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Regex</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Value</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Status</b></td></tr>");
		for (int x = 0; x < scenarios.length; x++) {
			if (!XMLTools.validateNodeValueByXPath(doc, scenarios[x][0].toString(),
					scenarios[x][1].toString())) {
				status = false;
			}
		}
		buffer.append("</table>");
		Reporter.log(buffer.toString()+ "<br/>");
		return status;
	}

	/**
	 * @summary Validate XML Response and reports findings
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param resourcePath: path of file to read
	 * @param scenario String: scenario to validate 
	 */
	public boolean validateResponse(String resourcePath, String scenario) {
		return validateNodeValueByXPath(getResponseDocument(),
				getTestScenario(resourcePath, scenario));
	}

	/**
	 * @summary Set the WSDL Endpoint for the class to use
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param endpoint String: Endpoint location of WSDL file. This can be a web location or local. 
	 */	
	protected void setEnvironmentServiceURL(String endpoint) {
		if (endpoint.contains("http")){
			setServiceURL(endpoint + "?wsdl");
		}else{
			setServiceURL(endpoint + ".wsdl");
		}
	}

	/**
	 * @summary Opens the WSDL file that was loaded with the {@link setEnvironmentServiceURL} and load a XML Template for selected operation
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param operation String: operation to load
	 */
	protected String buildRequestFromWSDL(String operation) {
		WsdlProject project = null;
		WsdlInterface[] wsdls = null;
		try {
			project = new WsdlProject();
			wsdls = WsdlImporter.importWsdl(project, getServiceURL());
		} catch (XmlException xmle) {
			throw new RuntimeException("Error loading XML: " + xmle.getCause());
		} catch (IOException ioe) {
			throw new RuntimeException("Error reading WSDL file: " + ioe.getCause());		
		} catch (SoapUIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		WsdlInterface wsdl = wsdls[0];
		WsdlOperation wsdlOperation = wsdl.getOperationByName(operation);
		setResponseTemplate(wsdlOperation.createResponse(true));
		return wsdlOperation.createRequest(true);
	}

	protected void removeComments() {
		setRequestDocument((Document) XMLTools
				.removeComments(getRequestDocument()));
	}

	protected void removeWhiteSpace() {
		setRequestDocument(XMLTools.removeWhiteSpace(getRequestDocument()));
	}

	public boolean validateRepsonseXML() {
		Document doc = getResponseDocument();
		String uri = getResponseBaseURI();

		if (doc == null) {
			throw new RuntimeException("Reponse document was null.");
		} else if (uri.isEmpty()) {
			throw new RuntimeException("URI was null.");
		}

		return XMLTools.validateXMLSchema(uri, doc);
	}
}