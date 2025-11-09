package seleniumFrameworkDesign.tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import seleniumFrameworkDesign.TestComponents.BaseTest;
import seleniumFrameworkDesign.pageobjects.CartPage;
import seleniumFrameworkDesign.pageobjects.CheckoutPage;
import seleniumFrameworkDesign.pageobjects.ConfirmationPage;
import seleniumFrameworkDesign.pageobjects.LandingPage;
import seleniumFrameworkDesign.pageobjects.OrdersPage;
import seleniumFrameworkDesign.pageobjects.ProductCatalogue;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SubmitOrderTest extends BaseTest {

	//String productName = "ZARA COAT 3";
	
	@Test(dataProvider = "getJsonData")
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		String countryName = "India";
		String thankYouForTheOrder = "Thankyou for the order.";

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		
		productCatalogue.addProductToCart(input.get("productName"));
		
		CartPage cartPage = productCatalogue.goToCartPage();		
		Assert.assertTrue(cartPage.isInCart(input.get("productName")));		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.selectCountry(countryName);		
		ConfirmationPage confirmationPage = checkoutPage.placeOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(thankYouForTheOrder));
	}
	
	@Test(dependsOnMethods= {"submitOrder"}, dataProvider = "getExcelData")
	public void orderHistoryTest(String email, String password, String productName) throws InterruptedException
	{	
		ProductCatalogue productCatalogue = landingPage.loginApplication(email, password);
		OrdersPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.isInOrder(productName));
	}
	
	@DataProvider
	public Object[][] getJsonData() throws IOException
	{
		List<HashMap<String,String>> data = getJsonDataToMap("\\src\\test\\java\\seleniumFrameworkDesign\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}
	
	@DataProvider
	public Object[][] getExcelData() throws IOException
	{
		Object[][] data = getExcelDataToObject("\\src\\test\\java\\seleniumFrameworkDesign\\data\\orderHistoryTest.xlsx");
		return data;
	}

}
