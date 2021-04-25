package mainPackage.jat;

import java.util.Locale;
import java.util.logging.Logger;

import mainPackage.jat_commonClasses.Configuration;
import mainPackage.jat_commonClasses.Translator;


/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * The singleton instance of this class provide central storage for resources
 * used by the program. It also defines application-global constants, such as
 * the application name.
 * 
 * @author Brad Richards
 */

public class ServiceLocator {
	private static ServiceLocator serviceLocator;
	
	final private Class<?> APP_CLASS = App_Template.class;
	final private String APP_NAME = "JavaFX_App_Template";
	
	final private Locale[] locales = new Locale[] {new Locale("en"), new Locale("de")};
	
	private Logger logger;
	private Configuration configuration;
	private Translator translator;
	
	public static ServiceLocator getServiceLocator() {
		if(serviceLocator == null)
			serviceLocator = new ServiceLocator();
		return serviceLocator;
	}
	
	private ServiceLocator() {
		
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public Class<?> getAPP_CLASS() {
		return APP_CLASS;
	}

	public String getAPP_NAME() {
		return APP_NAME;
	}

	public Locale[] getLocales() {
		return locales;
	}
	
	
	
}
