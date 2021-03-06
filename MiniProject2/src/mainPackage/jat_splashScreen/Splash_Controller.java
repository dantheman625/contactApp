package mainPackage.jat_splashScreen;

import mainPackage.jat.App_Template;
import mainPackage.jat_abstractClasses.Controller;
import javafx.concurrent.Worker;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */

public class Splash_Controller extends Controller<Splash_Model, Splash_View>{
	
	public Splash_Controller(final App_Template main, Splash_Model model, Splash_View view) {
		super(model, view);
		
		view.progress.progressProperty().bind(model.initializer.progressProperty());
		
		model.initializer.stateProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (newValue == Worker.State.SUCCEEDED)
						main.startApp();
				});
	}

}
