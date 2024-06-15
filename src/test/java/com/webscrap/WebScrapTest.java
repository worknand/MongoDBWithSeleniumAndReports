package com.webscrap;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.github.bonigarcia.wdm.WebDriverManager;



public class WebScrapTest {
	
	WebDriver driver;
	MongoCollection<Document> webCollection;
	
	@BeforeSuite
	public void connectMongoDB() {
		
	Logger mongoLogger=Logger.getLogger("org.mongodb.driver");
	
	MongoClient mongoClient= MongoClients.create("mongodb://localhost:27017");
	
	MongoDatabase  database= mongoClient.getDatabase("autoDB");
	
	//create collections
	webCollection= database.getCollection("web");
	
	}
	
	@BeforeTest
	public void setUp() {
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions chromeOptions=new ChromeOptions();
		chromeOptions.addArguments("--headless");
		driver=new ChromeDriver(chromeOptions);
	}
	
	@Test(dataProvider="getWebData")
	public void webScrapTesting(String appUrl) {
		driver.get(appUrl);
		String url= driver.getCurrentUrl();
		String title= driver.getTitle();
		int linksCount= driver.findElements(By.tagName("a")).size();
		int imagesCount= driver.findElements(By.tagName("img")).size();
		List<WebElement> links= driver.findElements(By.tagName("a"));
		List<String> hyperLinks=new ArrayList<String>();
		List<WebElement> imagesList= driver.findElements(By.tagName("img"));
		List<String> imagesLinks=new ArrayList<String>();
		
		Document d1=new Document();
		d1.append("url", url);
		d1.append("title", title);
		d1.append("linksCount", linksCount);
		d1.append("imagesCount", imagesCount);
		for (WebElement webElement : links) {
			String link=webElement.getAttribute("href");
			hyperLinks.add(link);	
		}
		for (WebElement webElement : imagesList) {
			String img=webElement.getAttribute("src");
			imagesLinks.add(img);	
		}
		d1.append("hyperLink", hyperLinks);
		d1.append("imagesLinks", imagesLinks);
		
		List<Document> docsList=new ArrayList<Document>();
		docsList.add(d1);
		
		webCollection.insertMany(docsList);
	}
	
	@DataProvider
	public Object[][] getWebData() {
		return new Object[][] {
			{"https://www.amazon.com"},
		/*	{"https://www.walmart.com"},
			{"https://www.flipkart.com"},
			{"https://www.amazon.in"},
			{"https://www.reliancedigital.in"},
			{"https://www.cricbuzz.com"}  */
		};
		
	}
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
		
	}

}
