package seleniumFrameworkDesign.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import seleniumFrameworkDesign.AbstractComponents.AbstractComponent;

public class OrdersPage extends AbstractComponent{
	
	WebDriver driver;
	
	public OrdersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".table tr")
	List<WebElement> orderList;
	
	By table = By.cssSelector(".table");
	By nameColumnBy = By.xpath("//td[2]");
	
	public List<WebElement> getOrderList()
	{
		waitForElementToAppear(table);
		return orderList;
	}
	
	public Boolean isInOrder (String productName)
	{
		Boolean match = getOrderList().stream().anyMatch(o -> o.findElement(nameColumnBy).getText().equalsIgnoreCase(productName));
		return match;
	}

}
