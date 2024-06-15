package com.mongoDBlivereporting;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(MongoDBTestListeners.class)
public class AmazonAppTest {
	
	
	WebDriver driver;
	
	@BeforeMethod
	public void setup() {
	WebDriverManager.chromedriver().setup();
	ChromeOptions co = new ChromeOptions();
	co.addArguments("--headless");
	driver = new ChromeDriver(co);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	driver.get("https://www.amazon.in");
	}
	
	@Test
	public void amazonTest_1() {
	Assert.assertEquals(driver.getTitle(), "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in");
	}
	
	@Test
	public void amazonTest_Careers() {
	Assert.assertTrue(driver.findElement(By.linkText("Careers11")).isDisplayed());
	}
	
	@Test
	public void amazonTest_AboutUs() {
	Assert.assertTrue(driver.findElement(By.linkText("About Us")).isDisplayed());
	}
	
	@Test
	public void amazonTest_Facebook() {
	Assert.assertTrue(driver.findElement(By.linkText("Facebook")).isDisplayed());
	}
	
	@Test
	public void amazonTest_Twitter() {
	Assert.assertTrue(driver.findElement(By.linkText("Twitter")).isDisplayed());
	}
	
	@Test
	public void amazonTest_Instagram() {
	Assert.assertTrue(driver.findElement(By.linkText("Instagram")).isDisplayed());
	}
	
	@Test
	public void amazon_search_test() {
	driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Laptop");
	driver.findElement(By.id("twotabsearchtextbox")).submit();
	String text = driver.findElement(By.cssSelector("span.a-color-state.a-text-bold")).getText();
	Assert.assertTrue(text.contains("Laptop"));
	}
	
	
	@AfterMethod
	public void tearDown() {
	driver.quit();
	}

}
