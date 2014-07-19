package com.waseemh.webdriver.assertions;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
/**
 * Assertions library for WebDriver 2.0 based on JUnit.
 * @author Waseem Hamshawi
 *
 */
public class WebDriverAssert{

	/**
	 * private constructor (static class)
	 */
	private WebDriverAssert() {
	}

	/**
	 * Assert that web page title equals to expected title.
	 * @param driver
	 * @param expectedTitle
	 */
	static public void assertTitleEquals(WebDriver driver, String expectedTitle) {
		assertEquals(expectedTitle, driver.getTitle());
	}

	/**
	 * Assert that response time of page load is less than/equals expected time.
	 * Time is in milliseconds (1 
	 * @param driver
	 * @param url
	 * @param msTime
	 */
	static public void assertResponseTimeLessThan(WebDriver driver, String url, int msTime) {
		long start = System.currentTimeMillis(); 
		driver.get(url);
		long end  = System.currentTimeMillis(); 
		long duration = end - start; 
		
		assertTrue("Page took " + duration + "ms to load (Greater than " + msTime+")",msTime>duration);
	}

	/**
	 * Assert that website URL equals to expected URL.
	 * @param driver
	 * @param url
	 */
	static public void assertUrlEquals(WebDriver driver, URL url) {
		assertUrlEquals(driver, url.toString());
	}

	/**
	 * Assert that website URL equals to expected URL (as string).
	 * @param driver
	 * @param url
	 */
	static public void assertUrlEquals(WebDriver driver, String url) {
		assertEquals( url , driver.getCurrentUrl() );
	}

	/**
	 * Assert that cookie is set in
	 * @param driver
	 * @param cookieName
	 */
	static public void assertCookie(WebDriver driver, String cookieName) {
		assertNotNull(driver.manage().getCookieNamed(cookieName));
	}

	/**
	 * Assert that web element exists using locator.
	 * @param driver
	 * @param by
	 */
	static public void assertElementExists(WebDriver driver, By by) {
		try {
			driver.findElement(by);
		} catch (NoSuchElementException ex) { 
			fail("Element not found using locator: " + by.toString());
		}
	}

	/**
	 * Assert that alert exists.
	 * @param driver
	 */
	static public void assertAlert(WebDriver driver) {
		try {
			driver.switchTo().alert();
		}
		catch(NoAlertPresentException e) {
			fail("Alert window not found.");
		}
	}

	/**
	 * Assert that expected text is present in page.
	 * @param driver
	 * @param expectedString
	 */
	static public void assertTextPresent(WebDriver driver, String expectedString) {
		assertTrue("Page doesn't contain expected string: " + expectedString, driver.findElement(By.tagName("body")).getText().contains(expectedString));
	}

	/**
	 * Assert that expected text is present (contained) in web element as inner text.
	 * @param element
	 * @param expectedString
	 */
	static public void assertTextPresent(WebElement element, String expectedString) {
		assertTrue("Element doesn't contain expected string: " + expectedString, element.getText().contains(expectedString));
	}

	/**
	 * Assert that label tag is present and bounded for a form element.
	 * Label to input binding is based on input id.
	 * @param driver
	 * @param inputId
	 */
	static public void assertLabelPresent(WebDriver driver, String inputId) {
		assertElementExists(driver,By.xpath("//label[@for='"+inputId+"']"));
	}

	/**
	 * Assert that inner text of web element equals to expected string.
	 * @param element
	 * @param expectedString
	 */
	static public void assertTextEquals(WebElement element, String expectedString) {
		assertEquals(expectedString, element.getText());
	}

	/**
	 * Assert that value of CSS property for web element equals to expected value.
	 * @param element
	 * @param cssProperty
	 * @param expectedCssValue
	 */
	static public void assertCssValueEquals(WebElement element, String cssProperty, String expectedCssValue) {
		assertEquals(expectedCssValue, element.getCssValue(cssProperty));
	}

	/**
	 * Assert that CSS background color of web element equals to expected color.
	 * @param element
	 * @param color
	 */
	static public void assertCssBackgroundColorEquals(WebElement element, Color color) {
		assertCssPropertyColorEquals(element,color,"background-color");
	}

	/**
	 * Assert that CSS border color of web element equals to expected color.
	 * @param element
	 * @param color
	 */
	static public void assertCssBorderColorEquals(WebElement element, Color color) {
		String cssValue = element.getCssValue("border-color");
		if(cssValue.isEmpty()) { //handle firefox driver issue with border-color property
			assertCssBorderColorEquals(element,color,CssPosition.TOP);
			assertCssBorderColorEquals(element,color,CssPosition.BOTTOM);
			assertCssBorderColorEquals(element,color,CssPosition.RIGHT);
			assertCssBorderColorEquals(element,color,CssPosition.LEFT);
		}
		else assertCssBorderColorEquals(element,color,CssPosition.NONE);
	}

	static public void assertCssBorderColorEquals(WebElement element, Color expectedColor, CssPosition position) {

		String cssBorderProperty;
		switch(position) {
		case TOP:
			cssBorderProperty = "border-top-color";
			break;
		case BOTTOM:
			cssBorderProperty = "border-bottom-color";
			break;
		case RIGHT:
			cssBorderProperty = "border-right-color";
			break;
		case LEFT:
			cssBorderProperty = "border-left-color";
			break;
		default:
			cssBorderProperty = "border-color";
		}

		assertCssPropertyColorEquals(element,expectedColor,cssBorderProperty);
	}

	/**
	 * Assert that CSS color property of web element equals to expected color.
	 * @param element
	 * @param color
	 */
	static public void assertCssColorEquals(WebElement element, Color color) {
		assertCssPropertyColorEquals(element,color,"color");
	}

	/**
	 * Assert that color value of CSS property equals to expected color.
	 * Applies to any CSS property with a color value.
	 * @param element
	 * @param expectedColor
	 * @param cssProperty
	 */
	static public void assertCssPropertyColorEquals(WebElement element, Color expectedColor, String cssProperty) {
		String cssValue = element.getCssValue(cssProperty);
		Color actualColor = Color.fromString(cssValue); 
		assertTrue(expectedColor.equals(actualColor));
	}

	/**
	 * Assert that web element value (value of "value" attribute) equals to expected value.
	 * @param element
	 * @param expectedValue
	 */
	static public void assertValueEquals(WebElement element, String expectedValue) {
		assertEquals(expectedValue, element.getAttribute("value"));
	}

	/**
	 * Assert that form web element is enabled.
	 * @param element
	 */
	static public void assertEnabled(WebElement element) {
		assertTrue("Element is not enabled.",element.isEnabled());
	}

	/**
	 * Assert that form web element is disabled.
	 * @param element
	 */
	static public void assertDisabled(WebElement element) {
		assertTrue("Element is enabled.",!element.isEnabled());
	}

	/**
	 * Assert that web element is selected.
	 * @param element
	 */
	static public void assertSelected(WebElement element) {
		assertTrue("Element is not selected.",element.isSelected());
	}

	/**
	 * Assert that web element is visible.
	 * @param element
	 */
	static public void assertVisible(WebElement element) {
		assertTrue("Element is not visible.",element.isDisplayed());
	}

	/**
	 * Assert that tag name of web elements equals to expected tag name.
	 * @param element
	 * @param expectedTagName
	 */
	static public void assertTagNameEquals(WebElement element, String expectedTagName) {
		assertEquals(expectedTagName,element.getTagName());
	}

	/**
	 * Assert that web element type is text input.
	 * @param element
	 */
	static public void assertTextInput(WebElement element) {
		assertEquals("Element is not a text input.","input",element.getTagName());
		assertEquals("Element is not a text input.","text",element.getAttribute("type"));
	}

	/**
	 * Assert that web element is hidden.
	 * @param element
	 */
	static public void assertHiddenInput(WebElement element) {
		assertEquals("Element is not a hidden input","input",element.getTagName());
		assertEquals("Element is not a hidden input","hidden",element.getAttribute("type"));
	}

	/**
	 * Assert that web element is a button.
	 * @param element
	 */
	static public void assertButton(WebElement element) {
		assertEquals("button",element.getTagName());
	}

	/**
	 * Assert that web element is a checkbox.
	 * @param element
	 */
	static public void assertCheckbox(WebElement element) {
		assertEquals("Element is not a checkbox,","input",element.getTagName());
		assertEquals("Element is not a checkbox.","checkbox",element.getAttribute("type"));
	}

	/**
	 * Assert that web element is a checkbox and is checked.
	 * @param element
	 */
	static public void assertCheckboxChecked(WebElement element) {
		assertCheckbox(element);
		assertSelected(element);
	}

	/**
	 * Assert that web element is radio element.
	 * @param element
	 */
	static public void assertRadio(WebElement element) {
		assertEquals("Element is not a radio.","input",element.getTagName());
		assertEquals("Element is not a radio.","radio",element.getAttribute("type"));
	}

	/**
	 * Assert that web element is a link.
	 * Link is represented by <a> tag.
	 * @param element
	 */
	static public void assertLink(WebElement element) {
		assertEquals("Element is not a link.","a",element.getTagName());
	}

	/**
	 * Assert that link URL equals to expected URL.
	 * @param element
	 * @param url
	 */
	static public void assertLinkUrlEquals(WebElement element,URL url) {
		assertLinkUrlEquals(element,url.toString());
	}

	/**
	 * Assert that link URL equals to expected URL.
	 * @param element
	 * @param url
	 */
	static public void assertLinkUrlEquals(WebElement element,String url) {
		assertLink(element);
		assertEquals(url.toString(),element.getAttribute("href"));
	}

	/**
	 * Assert that web element is a text area.
	 * @param element
	 */
	static public void assertTextArea(WebElement element) {
		assertEquals("Element is not a text area.","textarea",element.getTagName());
	}

	/**
	 * Assert that web element is an image.
	 * @param element
	 */
	static public void assertImage(WebElement element) {
		assertEquals("Element is not an image.","img",element.getTagName());
	}

	/**
	 * Assert image is visible.
	 * Image is not visible if image link is broken or not actually visible in page.
	 * @param driver
	 * @param element
	 */
	static public void assertImageVisible(WebDriver driver, WebElement element) {

		assertImage(element);

		String script;
		boolean result = false;

		if(driver instanceof InternetExplorerDriver)
			script = "return arguments[0].complete";

		else script = "return (typeof arguments[0].naturalWidth!=\"undefined\" && arguments[0].naturalWidth>0)";

		if (driver instanceof JavascriptExecutor)
			result = (Boolean) ((JavascriptExecutor)driver).executeScript(script,element);

		assertTrue("Image is not visible.", result);
	}

	/**
	 * Asserts that web element is a menu.
	 * Menu is represented by 'select' and 'option' tags.
	 * @param element
	 */
	static public void assertMenu(WebElement element) {
		try {
			new Select(element);
		}
		catch (UnexpectedTagNameException e) {
			fail("Element is not a menu.");
		}
	}

	/**
	 * Assert that option is selected in a menu.
	 * Options are compared by inner text (not value attribute).
	 * Menu is represented by 'select' and 'option' tags.
	 * @param element
	 * @param expectedOption
	 */
	static public void assertMenuOptionSelected(WebElement element, String expectedOption) {

		assertMenu(element);

		boolean result = false;

		Select selectElement = new Select(element);

		List<WebElement> optionElements = selectElement.getAllSelectedOptions();

		for (WebElement option : optionElements) {
			if(option.getText().equals(expectedOption)) {
				result=true;
			}
		}

		assertTrue("Expected option isn't selected: " + expectedOption,result);
	}

	/**
	 * Assert that selected options in menu equal to expected selected options.
	 * Options are compared by displayed text (not value attribute).
	 * Menu is represented by 'select' and 'option' tags.
	 * @param element
	 * @param expectedSelectValues
	 */
	static public void assertMenuOptionsSelected(WebElement element, List<String> expectedSelectValues) {

		for(String expectedValue : expectedSelectValues) {
			assertMenuOptionSelected(element,expectedValue);
		}
	}

	/**
	 * Assert that menu options are equal to expected options list.
	 * Options are compared by displayed text (not value attribute).
	 * Menu is represented by 'select' and 'option' tags.
	 * @param element
	 * @param expectedOptions
	 */
	static public void assertMenuOptionsEqual(WebElement element, List<String> expectedOptions) {
		for(String expectedOption : expectedOptions) {
			assertMenuContainsOption(element,expectedOption);
		}
	}

	/**
	 * Assert that menu contains option.
	 * Options are compared by displayed text (not value attribute).
	 * Menu is represented by 'select' and 'option' tags.
	 * @param element
	 * @param expectedOption
	 */
	static public void assertMenuContainsOption(WebElement element, String expectedOption) {

		assertMenu(element);
		Select select = new Select(element);
		boolean result = false;

		for (WebElement optionElement : select.getOptions()) {
			if(optionElement.getText().equals(expectedOption)) {
				result=true;
			}
		}

		assertTrue("Expected option isn't found: " + expectedOption,result);
	}


	static public void assertListEquals(WebElement element, List<String> expectedValues) {

		List<WebElement> liElements = element.findElements(By.tagName("li"));

		List<String> actualValues = new ArrayList<String>();

		for (WebElement liElement: liElements) {
			actualValues.add(liElement.getText());
		}

		assertArrayEquals(expectedValues.toArray(),actualValues.toArray());
	}

	/**
	 * Assert that web element is a password text field.
	 * @param element
	 */
	static public void assertPasswordTextField(WebElement element) {
		assertTrue("Element is not a password text field.",element.getTagName().equals("input") && element.getAttribute("type").equals("password"));
	}

	/**
	 * Assert that attribute is present in web element.
	 * @param element
	 * @param attribute
	 */
	static public void assertAttributePresent(WebElement element, String attribute) {
		assertNotNull("Attribute is not found in element.", element.getAttribute(attribute));
	}

	/**
	 * Assert that value of attribute equals to expected value.
	 * @param element
	 * @param attribute
	 * @param expectedValue
	 */
	static public void assertAttributeValueEquals(WebElement element, String attribute, String expectedValue) {
		assertAttributePresent(element,attribute);
		assertEquals(element.getAttribute(attribute),expectedValue);
	}

	/**
	 * Assert element size.
	 * @param element
	 * @param dim
	 */
	static public void assertSizeEquals(WebElement element,Dimension dim) {
		assertEquals(element.getSize(),dim);
	}

	/**
	 * Assert element size.
	 * @param element
	 * @param width
	 * @param height
	 */
	static public void assertSizeEquals(WebElement element,int width, int height) {
		Dimension dim = new Dimension(width,height);
		assertSizeEquals(element,dim);
	}

	/**
	 * Assert element position.
	 * @param element
	 * @param x
	 * @param y
	 */
	static public void assertPointEquals(WebElement element,int x, int y) {
		Point point = new Point(x, y);
		assertPointEquals(element,point);
	}

	/**
	 * Assert element position.
	 * @param element
	 * @param point
	 */
	static public void assertPointEquals(WebElement element,Point point) {
		assertEquals(element.getLocation(),point);
	}

	public enum CssPosition {
		TOP,BOTTOM,RIGHT,LEFT,NONE;
	} 

}