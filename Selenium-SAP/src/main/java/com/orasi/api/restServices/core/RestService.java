package com.orasi.api.restServices.core;

/**
 * Just playing around with some different ways of using rest services with Jackson 
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestService {
	private int statusCode = 0;
	private String responseFormat;
	private String responseAsString = null;
	private String userAgent; 
	
	private ObjectMapper mapper = new ObjectMapper().
		      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	//constructor
	public RestService() {}
	
	/*
	 * Encapsulation area 
	 */ 
	public String getUserAgent(){ return this.userAgent;}	
	public void setUserAgent(String userAgent){ this.userAgent = userAgent;	}		
	public int getStatusCode(){ return statusCode; }	
	private void setStatusCode(HttpResponse httpResponse){ 	statusCode = httpResponse.getStatusLine().getStatusCode(); }	
	public String getResponseFormat(){ return responseFormat; }	
	private void setResponseFormat(HttpResponse httpResponse){ responseFormat = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType().replace("application/", "");	}
	
	/**
	 * Sends a GET request
	 * 
	 * @param 	URL for the service you are testing
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendGetRequest(String URL){
		HttpUriRequest request = new HttpGet(URL);
		HttpResponse httpResponse = null;
		try {
			httpResponse = HttpClientBuilder.create().setUserAgent(getUserAgent()).build().execute( request );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		try {
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}
	
	/**
	 * Sends a post (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendPostRequest(String URL, List<NameValuePair> params){
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httppost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		try {
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("String response: " + responseAsString);		
		
		return responseAsString;
	}
	
	public String sendPostRequest(String URL, HttpEntity entity){
		
		 HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		 HttpPost httppost = new HttpPost(URL);
		
		httppost.setEntity(entity);
		//httppost.addHeader("content-type","application/json");
		//httppost.addHeader("content-type","application/json");
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httppost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		try {
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	System.out.println("String response: " + responseAsString);		
		
		return responseAsString;
	}
	
	public String sendPostRequest(String URL, List<NameValuePair> params, HttpEntity contentBody){
		
		 HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		 HttpPost httppost = new HttpPost(URL);
		
		try {
		    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		httppost.setEntity(contentBody);
		
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httppost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		try {
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	System.out.println("String response: " + responseAsString);		
		
		return responseAsString;
	}
	/**
	 * Sends a put (create) request, pass in the parameters for the json arguments to create
	 * 
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendPutRequest(String URL, List<NameValuePair> params){
		HttpClient httpclient = HttpClients.createDefault();
		HttpPut putRequest = new HttpPut(URL);
		try {
			putRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(putRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		try {
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}
	
	/**
	 * Sends a patch (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendPatchRequest(String URL, List<NameValuePair> params){
		HttpClient httpclient = HttpClients.createDefault();
		HttpPatch patchRequest = new HttpPatch(URL);
		try {
			patchRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(patchRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		try {
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}
	
	/**
	 * Sends a delete request.  Depends on the service if a response is returned.
	 * If no response is returned, will return null	 * 
	 * 
	 * @param 	URL		for the service
	 * @return 	response in string format or null
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendDeleteRequest(String URL){

		HttpUriRequest deleteRequest = new HttpDelete(URL);
		HttpResponse httpResponse = null;
		try {
			httpResponse = HttpClientBuilder.create().build().execute( deleteRequest );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		if (httpResponse.getEntity()!=null){
			try {
				responseAsString = EntityUtils.toString(httpResponse.getEntity());
			} catch (ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("String response: " + responseAsString);
		}		
		
		return responseAsString;
	}
	
	/**
	 * Sends an options request.  Options should give what the acceptable methods are for
	 * the service (GET, HEAD, PUT, POST, etc).  There should be some sort of an ALLOW 
	 * header that will give you the allowed methods.  May or may not be a body to the response, 
	 * depending on the service.  
	 * 
	 * This method will return all the headers and the test should parse through and find the header 
	 * it needs, that will give the allowed methods, as the naming convention will be different for each service.  
	 * 
	 * @param 	URL		for the service
	 * @return 	returns an array of headers
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public Header[] sendOptionsRequest(String URL ){
		HttpClient httpclient = HttpClients.createDefault();
		HttpOptions httpOptions=new HttpOptions(URL);
		
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httpOptions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Response Headers: ");
		Header[] headers = httpResponse.getAllHeaders();
		for (Header header: headers ){	
			System.out.println(header.getName() + " : " + header.getValue());
		}
		
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		return headers;
	}
	
	/**
	 * Uses the class instance of the responeAsString to map to object
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public <T> T mapJSONToObject(Class<T> clazz){
		return mapJSONToObject(responseAsString, clazz);		
	}
	
	/**
	 * Can pass in any json as a string and map to object
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public <T> T mapJSONToObject(String stringResponse, Class<T> clazz){
		try {
			return mapper.readValue(stringResponse, clazz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Can pass in any json as a string and maps to tree
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public JsonNode mapJSONToTree(String stringResponse){
				
		try {
			return mapper.readTree(stringResponse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Uses the class instance of the responeAsString to map to tree
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public JsonNode mapJSONToTree(){
		return mapJSONToTree(responseAsString);
	}
	
}
