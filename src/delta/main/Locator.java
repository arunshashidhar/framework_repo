package delta.main;

import org.openqa.selenium.By;

public class Locator {

	public static By getLocator(String input1)
	{
		By b = null;
//		System.out.println(input1);
		String[] str = input1.split("_");
		String locator = str[0];
		String value = str[1];
//		System.out.println(locator);
//		System.out.println(value);
		if(locator.equalsIgnoreCase("ID"))
		{
			b = By.name(value);
		}
		else if (locator.equalsIgnoreCase("NAME"))
		{
			b = By.name(value);
		}
		else if (locator.equalsIgnoreCase("XPATH"))
		{
			b = By.xpath(value);
		}
		else if (locator.equalsIgnoreCase("CSSSelector"))
		{
			b = By.cssSelector(value);
		}
		else if (locator.equalsIgnoreCase("TAGNAME"))
		{
			b = By.tagName(value);
		}
		else if (locator.equalsIgnoreCase("PARTIALLINKTEXT"))
		{
			b = By.partialLinkText(value);
		}
		else if (locator.equalsIgnoreCase("LINKTEXT"))
		{
			b = By.linkText(value);
		}
		else if (locator.equalsIgnoreCase("CLASSNAME"))
		{
			b = By.className(value);
		}
		return b;
	}
}
