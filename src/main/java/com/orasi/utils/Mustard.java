package com.orasi.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;

import com.orasi.api.restServices.core.RestService;
import com.orasi.core.Beta;
import com.saucelabs.common.SauceOnDemandAuthentication;


@Beta
public class Mustard {
	private static String mustardURL = "http://mustard.orasi.com/results";
    	private static String mustardKey = "c73fbfed815904a032a5cec113bfe85f"; //dev key da8f8779749cfb27bbba1fb9f136c1cf
	protected static ResourceBundle appURLRepository = ResourceBundle.getBundle(Constants.ENVIRONMENT_URL_PATH);
	protected static SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
			Base64Coder.decodeString(appURLRepository.getString("SAUCELABS_USERNAME")),
			Base64Coder.decodeString(appURLRepository.getString("SAUCELABS_KEY")));
	

    @Beta
	public static void postResultsToMustard(OrasiDriver driver, ITestResult result, String runLocation){
		
		RestService request = new RestService();
		
		String device_id = driver.getDriverCapability().platform().name()  + "-" +driver.getDriverCapability().browserName() + "_" + driver.getDriverCapability().browserVersion().replace(".", "_");
		//String os_version = driver.getDriverCapability().browserVersion();
		String device_platform = driver.getDriverCapability().platform().family().name();
		String test_name = result.getTestClass().getName();
		test_name = test_name.substring(test_name.lastIndexOf(".") + 1, test_name.length())+ "-" +result.getMethod().getMethodName() ;
		String status = "";
		if (result.getStatus() == ITestResult.SUCCESS) status = "pass";
		else if (result.getStatus() == ITestResult.SKIP) status = "skip";
		else status = "fail";
		String sauceURL = "";
		MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
		multipartEntity.addTextBody("project_id",mustardKey);
		multipartEntity.addTextBody("device_id", device_id);
		multipartEntity.addTextBody("device_platform", device_platform);
		multipartEntity.addTextBody("test_name",test_name);
		multipartEntity.addTextBody("status",status);
		
		if(runLocation.toLowerCase().equals("sauce")){
		    sauceURL = "https://saucelabs.com/beta/tests/" + driver.getSessionId().toString();
		    multipartEntity.addTextBody("link",sauceURL);
		}
		
		if(status.equals("fail")) {
		    multipartEntity.addTextBody("comment",result.getThrowable().getMessage());
		    multipartEntity.addTextBody("stacktrace",ExceptionUtils.getFullStackTrace(result.getThrowable()));
		    multipartEntity.addBinaryBody("screenshot", driver.getScreenshotAs(OutputType.FILE), ContentType.create("image/jpeg"), Randomness.randomAlphaNumeric(32));
		}
	
		
		request.sendPostRequest(mustardURL, multipartEntity.build());
		
	}
}
