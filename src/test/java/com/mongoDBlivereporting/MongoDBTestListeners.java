package com.mongoDBlivereporting;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBTestListeners implements ITestListener{
	
	MongoCollection<Document> webCollection;
	MongoClient mongoClient;
	
	@Override
	public void onFinish(ITestContext contextFinish) {
		
	mongoClient.close();
		
	System.out.println("onFinish method finished");

	}

	@Override
	public void onStart(ITestContext contextStart) {
		
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database = mongoClient.getDatabase("automationDB");
		// create collection:
		webCollection = database.getCollection("live");
	    System.out.println("onStart method started");
	
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	System.out.println("Method failed with certain success percentage"+ result.getName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		String methodName = result.getMethod().getMethodName();
		String className = result.getMethod().getRealClass().getName();
		Document d1 = new Document();
		d1.append("methodName", methodName);
		d1.append("className", className);
		d1.append("status", "Fail");
		List<Document> docsList = new ArrayList<Document>();
		docsList.add(d1);
		
		webCollection.insertMany(docsList);

	}

	@Override
	public void onTestSkipped(ITestResult result) {
	System.out.println("Method skipped"+ result.getName());

	}

	@Override
	public void onTestStart(ITestResult result) {
		
	

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		String methodName = result.getMethod().getMethodName();
		String className = result.getMethod().getRealClass().getName();
		Document d1 = new Document();
		d1.append("methodName", methodName);
		d1.append("className", className);
		d1.append("status", "PASS");
		List<Document> docsList = new ArrayList<Document>();
		docsList.add(d1);
		
		webCollection.insertMany(docsList);


	System.out.println("Method started"+ result.getName());
	System.out.println("Method passed"+ result.getName());

	}
	
	
	

}
