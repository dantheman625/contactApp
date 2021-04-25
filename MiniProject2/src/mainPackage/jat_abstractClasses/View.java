package mainPackage.jat_abstractClasses;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */

public abstract class View<M extends Model> {
	protected Stage stage;
	protected Scene scene;
	protected M model;
	
	
	
	protected View (Stage stage, M model) {
		this.stage=stage;
		this.model=model;
		
		scene = create_GUI();
		stage.setScene(scene);
	}
	
	protected abstract Scene create_GUI();
		
		public void start() {
			stage.show();
		}
		
		public void stop() {
			stage.hide();
		}
		
		public Stage getStage() {
			return stage;
		}
	}

