package seleniumFrameworkDesign.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import seleniumFrameworkDesign.TestComponents.BaseTest;
import seleniumFrameworkDesign.pageobjects.CartPage;
import seleniumFrameworkDesign.pageobjects.CheckoutPage;
import seleniumFrameworkDesign.pageobjects.ConfirmationPage;
import seleniumFrameworkDesign.pageobjects.LandingPage;
import seleniumFrameworkDesign.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest
{
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public CartPage cartPage;
	public CheckoutPage checkoutPage;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password (String username, String password)
	{
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("^I add product (.+) to Cart$")
	public void I_add_product_to_cart (String productName) throws InterruptedException
	{
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_and_submit_the_order (String productName)
	{
		cartPage = productCatalogue.goToCartPage();		
		Assert.assertTrue(cartPage.isInCart(productName));		
		checkoutPage = cartPage.goToCheckout();	
		checkoutPage.selectCountry("India");		
		confirmationPage = checkoutPage.placeOrder();
	}
	
	@Then("{string} message is displayed on Confirmation page")
	public void message_displayed_confirmationPage(String string)
	{
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("{string} message is displayed")
	public void error_message_is_displayed (String string)
	{
		Assert.assertEquals(landingPage.getErrorMessage(), string);
		driver.close();
	}
	

}
