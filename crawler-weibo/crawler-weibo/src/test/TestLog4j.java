package test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLog4j {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Test.class);

		PropertyConfigurator.configure ( "conf/log4j.properties" ) ;

		logger.debug ( "test" ); 
		
		TestLog4jSon.kk();
	}

}
