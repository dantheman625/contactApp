package mainPackage.jat_splashScreen;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import mainPackage.jat.ServiceLocator;
import mainPackage.jat_abstractClasses.Model;
import mainPackage.jat_commonClasses.Configuration;
import mainPackage.jat_commonClasses.Translator;
import javafx.concurrent.Task;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */

public class Splash_Model extends Model{
	ServiceLocator serviceLocator;
	
	public Splash_Model() {
		super();
	}
	
	final Task<Void> initializer = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
			this.updateProgress(1, 6);
			
			serviceLocator = ServiceLocator.getServiceLocator();
			this.updateProgress(2, 6);
			
			serviceLocator.setLogger(configureLogging());;
			this.updateProgress(3, 6);
			
			serviceLocator.setConfiguration(new Configuration());
			this.updateProgress(4, 6);
			
			String language = serviceLocator.getConfiguration().getOption("Language");
			serviceLocator.setTranslator(new Translator(language));
			this.updateProgress(5, 6);
			
			this.updateProgress(6, 6);
			
			return null;
		}
	};
	
	public void initialize() {
		new Thread(initializer).start();
	}
	
	private Logger configureLogging() {
		Logger rootLogger = Logger.getLogger("");
		rootLogger.setLevel(Level.FINEST);
		
		Handler [] defaultHandlers = Logger.getLogger("").getHandlers();
		defaultHandlers[0].setLevel(Level.INFO);
		
		Logger ourLogger = Logger.getLogger(serviceLocator.getAPP_NAME());
		ourLogger.setLevel(Level.FINEST);
		
		try {
			Handler logHandler = new FileHandler("%t/"
					+ serviceLocator.getAPP_NAME() + "_%u" + "_%g" + ".log",
					1000000, 9);
		} catch (Exception e) {
			throw new RuntimeException("Unable to initialize log files: "
					+ e.toString());
		}
		
		return ourLogger;
	}

}
