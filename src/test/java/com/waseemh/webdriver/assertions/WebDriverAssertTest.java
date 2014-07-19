package com.waseemh.webdriver.assertions;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;

import com.waseemh.webdriver.assertions.WebDriverAssert.CssPosition;



/**
 * Unit test for simple App.
 */
public class WebDriverAssertTest
{
	static WebDriver driver;
	
	static String URL;

	@BeforeClass
	public static void setup() {
		File file = new File(WebDriverAssertTest.class.getClass().getResource("/test.html").getFile());
		URL = file.getAbsolutePath();
		driver = new FirefoxDriver();
		driver.get(URL);
	}
	
	@Test
	public void generalTests() {
		WebDriverAssert.assertResponseTimeLessThan(driver,URL,5000);
		WebDriverAssert.assertTextPresent(driver, "Testing display of HTML elements");
		WebDriverAssert.assertTitleEquals(driver, "Website Example");
	}
	
	@Test
	public void cssTests() {
		WebElement cssElement = driver.findElement(By.id("mydiv"));
		
		WebDriverAssert.assertCssBackgroundColorEquals(cssElement, Color.fromString("green"));
		WebDriverAssert.assertCssValueEquals(cssElement, "width", "100px");
		WebDriverAssert.assertCssBorderColorEquals(cssElement, Color.fromString("yellow"));
		WebDriverAssert.assertCssColorEquals(cssElement, Color.fromString("red"));
		WebDriverAssert.assertCssBorderColorEquals(cssElement, Color.fromString("yellow"), CssPosition.TOP);
	}
	
	@Test
	public void imageTest() {
		WebElement imgElement = driver.findElement(By.id("mario"));
		WebDriverAssert.assertImageVisible(driver, imgElement);
	}
	
	@Test
	public void elementTypeTest() {
		WebElement cbElement = driver.findElement(By.id("f5"));
		WebDriverAssert.assertCheckbox(cbElement);
		
		WebElement radioElement = driver.findElement(By.id("f3"));
		WebDriverAssert.assertRadio(radioElement);
	}
	
	@Test
	public void tagNameTest() {
		WebElement element = driver.findElement(By.id("f3"));
		WebDriverAssert.assertTagNameEquals(element, "input");
	}
	
	@Test
	public void listTest() {
		WebElement element = driver.findElement(By.id("mylist"));
		List<String> list = new ArrayList<String>();
		list.add("elem1");
		list.add("elem2");
		list.add("elem3");
		WebDriverAssert.assertListEquals(element, list);
	}
	
	@Test
	public void labelTest() {
		WebDriverAssert.assertLabelPresent(driver, "f0");
	}
	
	@Test
	public void textAreaTest() {
		WebElement element = driver.findElement(By.id("f2"));
		WebDriverAssert.assertTextArea(element);
		WebDriverAssert.assertTextEquals(element, "Default text.");
	}
	
	@Test
	public void passwordFieldTest() {
		WebElement element = driver.findElement(By.id("f22"));
		WebDriverAssert.assertPasswordTextField(element);
	}	
	
	@Test
	public void hiddenInputTest() {
		WebElement element = driver.findElement(By.id("f55"));
		WebDriverAssert.assertHiddenInput(element);
	}
	
	@Test
	public void menuTests() {
		WebElement element = driver.findElement(By.id("f10"));
		WebDriverAssert.assertMenuOptionSelected(element, "two");
		WebDriverAssert.assertMenuContainsOption(element, "one");
		
		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		WebDriverAssert.assertMenuOptionsEqual(element, list);
	}
	
	@AfterClass
	public static void teardown() {
		driver.quit();
	}
}
