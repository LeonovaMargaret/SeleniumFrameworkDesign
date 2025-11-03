package seleniumFrameworkDesign.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import seleniumFrameworkDesign.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	
	WebDriver driver;
	
	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartProductNames;
	
	@FindBy(css=".totalRow button")
	WebElement checkout;
	
	public CheckoutPage goToCheckout()
	{
		checkout.click();
		return new CheckoutPage(driver);
	}
	
	public Boolean isInCart (String productName)
	{
		Boolean match = cartProductNames.stream().anyMatch(p -> p.getText().equalsIgnoreCase(productName));
		return match;
	}

}
