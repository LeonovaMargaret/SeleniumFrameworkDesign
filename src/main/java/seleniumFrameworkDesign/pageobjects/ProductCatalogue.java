package seleniumFrameworkDesign.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import seleniumFrameworkDesign.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	By productsBy = By.cssSelector(".mb-3");
	

	By productBy = By.tagName("b");
	By addToCartBy = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.id("toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName) 
	{
		WebElement product = getProductList().stream().filter(p -> p.findElement(productBy).getText().equals(productName)).findFirst().orElse(null);
		return product;
	}
	
	public void addProductToCart(String productName) throws InterruptedException
	{
		WebElement product = getProductByName(productName);
		product.findElement(addToCartBy).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
		waitForElementToDisappear(spinnerOverlay);
	}

}
