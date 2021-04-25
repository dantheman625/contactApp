package mainPackage.jat;

import mainPackage.jat_appClasses.App_Controller;
import mainPackage.jat_appClasses.App_Model;
import mainPackage.jat_appClasses.App_View;
import mainPackage.jat_splashScreen.Splash_Controller;
import mainPackage.jat_splashScreen.Splash_Model;
import mainPackage.jat_splashScreen.Splash_View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */

public class App_Template extends Application{
	
	private static App_Template mainProgram;
	private Splash_View splashView;
	private App_View view;
	
	private ServiceLocator serviceLocator;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() {
		if(mainProgram == null) {
			mainProgram = this;
		} else {
			Platform.exit();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		Splash_Model splashModel = new Splash_Model();
		splashView = new Splash_View(primaryStage, splashModel);
		new Splash_Controller(this, splashModel, splashView);
		splashView.start();

		splashModel.initialize();
	}
	
	public void startApp() {
		Stage appStage = new Stage();
		App_Model model = new App_Model();
		view = new App_View(appStage, model);
		new App_Controller(model, view);
		
		serviceLocator = ServiceLocator.getServiceLocator();
		
		splashView.stop();
		splashView = null;
		
		view.start();
	}
	
	@Override
	public void stop() {
		serviceLocator.getConfiguration().save();
		if (view != null) {
			view.stop();
		}
		
		serviceLocator.getLogger().info("Application terminated");
	}
	
	protected static App_Template getMainProgram() {
		return mainProgram;
	}
}
