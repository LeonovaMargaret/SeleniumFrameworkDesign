package seleniumFrameworkDesign.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import seleniumFrameworkDesign.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent{
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".form-group input")
	WebElement countryField;
	
	@FindBy(css="button.ta-item span")
	List<WebElement> countryList;
		
	@FindBy(css=".action__submit")
	WebElement placeOrderButton;
	
	By results = By.cssSelector(".ta-results");
	
	public void selectCountry (String countryName)
	{
		countryField.sendKeys(countryName);
		waitForElementToAppear(results);
		countryList.stream().filter(c -> c.getText().trim().equalsIgnoreCase(countryName)).findFirst().orElse(null).click();
	}
	
	public ConfirmationPage placeOrder()
	{
		placeOrderButton.click();
		return new ConfirmationPage(driver);
	}


}
