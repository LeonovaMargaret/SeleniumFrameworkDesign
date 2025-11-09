package seleniumFrameworkDesign.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import seleniumFrameworkDesign.data.DataReader;
import seleniumFrameworkDesign.pageobjects.LandingPage;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class BaseTest
{
	public DataReader dataReader = new DataReader();

	public WebDriver driver = null;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\seleniumFrameworkDesign\\resources\\GlobalData.properties");
		prop.load(fis);

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		// System.setProperty("webdriver.chrome.driver",
		// "/Users/Margo/Documents/chromedriver.exe");

		if (browserName.contains("chrome"))
		{
			ChromeOptions options = new ChromeOptions();
			if (browserName.contains("headless"))
				options.addArguments("headless");

			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1920, 1080));
		} else if (browserName.contains("firefox"))
		{
			FirefoxOptions options = new FirefoxOptions();
			if (browserName.contains("headless"))
				options.addArguments("headless");

			driver = new FirefoxDriver(options);
			driver.manage().window().setSize(new Dimension(1920, 1080));
		} else if (browserName.contains("edge"))
		{
			EdgeOptions options = new EdgeOptions();
			if (browserName.contains("headless"))
				options.addArguments("headless");

			driver = new EdgeDriver(options);
			driver.manage().window().setSize(new Dimension(1920, 1080));
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		List<HashMap<String, String>> data = dataReader.getJsonDataToMap(filePath);
		return data;
	}
	
	public Object[][] getExcelDataToObject(String filePath) throws IOException
	{
		Object[][] data = dataReader.getExcelDataToObject(filePath);
		return data;
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		String path = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(path);
		FileUtils.copyFile(source, file);
		return path;
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown()
	{
		driver.close();
	}

}
