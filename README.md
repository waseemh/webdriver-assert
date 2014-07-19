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
	
	
**CSS Assertions**

	WebElement cssElement = driver.findElement(By.id("mydiv"));

Test background color:

	WebDriverAssert.assertCssBackgroundColorEquals(cssElement, Color.fromString("green"));
	
Test value of 'width' CSS property :
	
	WebDriverAssert.assertCssValueEquals(cssElement, "width", "100px");
	
Test border color: (supports compund and non-compund border-color properties)
	
	WebDriverAssert.assertCssBorderColorEquals(cssElement, Color.fromString("yellow"));
	WebDriverAssert.assertCssBorderColorEquals(cssElement, Color.fromString("yellow"), CssPosition.TOP);
	
Test color CSS property of element:
	WebDriverAssert.assertCssColorEquals(cssElement, Color.fromString("red"));
	

For more available functionality and examples, take a look at the unit tests or explore the API by yourself.	

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
