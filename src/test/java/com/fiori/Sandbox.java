package com.fiori;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fiori.logisticsSD.SelectCustomerPage;
import com.fiori.logisticsSD.createSalesOrder.SOShoppingCartPage;
import com.fiori.logisticsSD.createSalesOrder.SalesOrdersPage;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.utils.debugging.Highlight;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

public class Sandbox extends TestEnvironment {

    private OrasiDriver driver = null;
/*    @DataProvider(name = "dataScenario")
    public Object[][] scenarios() {
	return new ExcelDataProvider(Constants.BLUESOURCE_DATAPROVIDER_PATH + "Login.xlsx", "Login").getTestData();
    }*/
    
    @BeforeTest( alwaysRun=true)
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion", "operatingSystem", "environment" })
    public void setup(String runLocation, String browserUnderTest, String browserVersion, String operatingSystem, String environment) {
		setApplicationUnderTest("Fiori");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		setThreadDriver(true);
		testName="Sandbox";
		testStart(testName);
		driver = getDriver();
		Highlight.setDebugMode(true);
    }

    @AfterMethod( alwaysRun=true)
    public void closeSession(ITestResult test) {
	endTest(driver, test);
    }    

   // @Features("Login")
   // @Stories("Logging in will land me on the Homepage")
   // @Severity(SeverityLevel.BLOCKER)
   // @Title("Login - Login with correct information")
    @Test()
    public void testLogin() {
	
    	TestReporter.log("Login to Fiori");
		LaunchPad launchPad = new LaunchPad(driver);
		TestReporter.assertTrue(launchPad.pageLoaded(),"Application opened and loaded successfully");
		launchPad.handleEula();
		launchPad.logisticsSD_CreateSalesOrder();
		
		SelectCustomerPage selectCustomerPage = new SelectCustomerPage(driver);
		TestReporter.assertTrue(selectCustomerPage.pageLoaded(),"Loaded Create Sales Order - Select Customer successfully");
		selectCustomerPage.searchForCustomer("Becker Berlin");
		selectCustomerPage.selectCustomer();
		
		SalesOrdersPage salesOrderPage = new SalesOrdersPage(driver);
		TestReporter.assertTrue(salesOrderPage.pageLoaded(),"Loaded Sales Order Page successfully");
		TestReporter.assertTrue(salesOrderPage.customerLoaded("Becker Berlin"),"Loaded Customer to Sales Order Page successfully");
		salesOrderPage.showProducts();
		salesOrderPage.selectItemByName("Sunny Sunny");
		salesOrderPage.addProductToCart();
		
		salesOrderPage.selectItemByName("REA Printer with battery");
		salesOrderPage.addProductToCart();
		
		driver.data().add(salesOrderPage.storeInfo());
		driver.data().printData();
		
		salesOrderPage.goToShoppingCart();
		
		SOShoppingCartPage shoppingCart = new SOShoppingCartPage(driver);
		TestReporter.assertTrue(shoppingCart.pageLoaded(),"Loaded Shopping Cart successfully");
		TestReporter.assertTrue(shoppingCart.validateNumberOfItems(),"Number of items in cart matched");
		shoppingCart.setDeliveryDate("10");
		shoppingCart.updateItemQuantity("Sunny Sunny", "8");
		shoppingCart.updateItemDelivery("REA Printer with battery", "45");
		System.out.println();
   }
}
