webdriver-assert
================

Assertions library for Selenium WebDriver 2.0, based on JUnit.
Library includes ~50 assertions covering different aspects:
- Elements types
- CSS properties
- Form fields
- Broken image links
- Page load time
- Much more..

Examples
========

WebDriver assertions are used as a static import in your JUnit tests classes.

**General Assertions**

Test response time of page load:

	WebDriverAssert.assertResponseTimeLessThan(driver,"http://google.com",5000);

Test content appearance in web page:

	WebDriverAssert.assertTextPresent(driver, "This text should appear in web page");
	
Test alert window appearance:

	WebDriverAssert.assertAlert(driver);
	
Test element size:

	WebDriverAssert.assertSizeEquals(element,100,150);
	
**CSS Assertions**

	WebElement cssElement = driver.findElement(By.id("mydiv"));

Test background color:

	WebDriverAssert.assertCssBackgroundColorEquals(cssElement, Color.fromString("green"));
	
Test value of 'width' CSS property:
	
	WebDriverAssert.assertCssValueEquals(cssElement, "width", "100px");
	
Test border color: (supports compund and non-compund border-color properties)
	
	WebDriverAssert.assertCssBorderColorEquals(cssElement, Color.fromString("yellow"));
	WebDriverAssert.assertCssBorderColorEquals(cssElement, Color.fromString("yellow"), CssPosition.TOP);
	
Test color CSS property of element:

	WebDriverAssert.assertCssColorEquals(cssElement, Color.fromString("red"));
	
**Image Assertions**
	
Test image visibilty: Fails if image link is broken or image isn't visible.	
	
	WebElement imgElement = driver.findElement(By.id("mario"));
	WebDriverAssert.assertImageVisible(driver, imgElement);
	

**Form Assertions**

Test text area elements:

	WebDriverAssert.assertTextArea(element);
	WebDriverAssert.assertTextEquals(element, "Default text.");

Test password field:

	WebDriverAssert.assertPasswordTextField(element);
	
Test if label is bounded for input element:

	WebDriverAssert.assertLabelPresent(driver, "f0");
	
Test if element is a checkbox:

	WebDriverAssert.assertCheckbox(cbElement);
	
Test if element is a radio button:

	WebDriverAssert.assertRadio(radioElement);
	
**Menu Assertions**

Assertions to be used with '<select>' and '<option>' form elements.

Test selected option in menu:

	WebDriverAssert.assertMenuOptionSelected(element, "two");

Test if menu contains specific option:

	WebDriverAssert.assertMenuContainsOption(element, "one");
	
Test if menu contains list of options:

	List<String> list = new ArrayList<String>();
	list.add("one"); list.add("two"); list.add("three");
	WebDriverAssert.assertMenuOptionsEqual(element, list);

For more available functionality and examples, take a look at the unit tests or explore the API and Javadoc.

Building from Sources
========

Maven is used as a build system.
In order to produce a package, run maven command `mvn clean package`.
Tests can be executed using command `mvn test`. 

License
========

Copyright 2014 Waseem Hamshawi

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
