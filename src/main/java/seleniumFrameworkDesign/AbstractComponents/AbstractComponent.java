package seleniumFrameworkDesign.AbstractComponents;

import java.awt.List;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import seleniumFrameworkDesign.pageobjects.CartPage;
import seleniumFrameworkDesign.pageobjects.OrdersPage;

public class AbstractComponent {
	
	WebDriver driver;
	
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[routerlink*='cart']")
	WebElement cartHeader;
	@FindBy(css="[routerlink*='myorders']")
	WebElement ordersHeader;
	@FindBy(css=".ng-animating")
	public WebElement spinner;
	@FindBy(css=".ngx-spinner-overlay")
	public WebElement spinnerOverlay;

	public void waitForElementToAppear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForElementToAppear(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public void waitForElementToDisappear(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(ele));
		System.out.println(ele + "disappeared");
	}
	public void scrollIntoView(WebElement ele) throws InterruptedException
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		Thread.sleep(500);
	}
	
	public CartPage goToCartPage()
	{
		cartHeader.click();
		return new CartPage(driver);
	}
	
	public OrdersPage goToOrdersPage()
	{
		//ordersHeader.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", ordersHeader);
		return new OrdersPage(driver);
	}

}
