package generics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Property {
	public static String getPropertyValue(String filePath,String key) throws FileNotFoundException, IOException
	{
		String value = "";
		Properties ppt = new Properties();
		try{
		ppt.load(new FileInputStream(filePath));
		value = ppt.getProperty(key);
		}
		catch(Exception e)
		{
		}
		return value;
		
	}
	
}
