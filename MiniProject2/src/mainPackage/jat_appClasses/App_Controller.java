package mainPackage.jat_appClasses;

import mainPackage.jat.ServiceLocator;
import mainPackage.jat_abstractClasses.Controller;

import java.time.LocalDate;
import java.util.Calendar;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class App_Controller extends Controller<App_Model, App_View>	{
	ServiceLocator serviceLocator;
	
	public App_Controller(App_Model model, App_View view) {
		super(model, view);
		
		
		
		view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
		
		serviceLocator = ServiceLocator.getServiceLocator();
		serviceLocator.getLogger().info("Application controller initialized");
		
		view.itemCreate.setOnAction(this::addContactView);
		view.itemUpdate.setOnAction(this::updateContactView);
		view.itemClear.setOnAction(this::clearAll);
		view.itemDelete.setOnAction(this::deleteContact);
		view.itemFilter.setOnAction(this::filterView);
		view.itemDark.setOnAction(this::darkMode);
		view.itemLight.setOnAction(this::lightMode);
		view.backButton.setOnAction(this::back);
		view.contactlist.setOnMouseClicked(this::showDetails);
		view.addphneButton.setOnAction(this::addPhoneField);
		view.addEmailButton.setOnAction(this::addEmailField);
		view.addButton.setOnAction(this::createContact);
		view.updateButton.setOnAction(this::updateContact);
		view.applyFilter.setOnAction(this::filterContact);
		view.getStage().setOnCloseRequest(this::upload);
	}
	
	public void addContactView(ActionEvent event) {
		view.menuBar.getScene().setRoot(view.addContactView());
		view.contactlist.getSelectionModel().clearSelection();
		view.adjdustStage();
	}
	
	public void updateContactView(ActionEvent event) {
		view.getStage().getScene().setRoot(view.updateContactView());
		view.adjdustStage();
	}
	
	public void showDetails(MouseEvent event) {
		try{view.detailsBox.getChildren().clear();
		view.detailsBox.getChildren().add(view.showContact());
		view.adjdustStage();
		view.updateTexts();
		} catch (Exception e) {
		}
	}
	
	public void darkMode(ActionEvent event) {
		view.getStage().getScene().getStylesheets().clear();
		view.getStage().getScene().getStylesheets()
		.add(getClass().getResource("styledark.css").toExternalForm());
	}
	
	public void lightMode(ActionEvent event) {
		view.getStage().getScene().getStylesheets().clear();
		view.getStage().getScene().getStylesheets()
		.add(getClass().getResource("style.css").toExternalForm());
	}
	
	public void filterView(ActionEvent event) {
		view.getStage().getScene().setRoot(view.filterView());
		view.contactlist.getSelectionModel().clearSelection();
		view.adjdustStage();
	}
	
	public void back(ActionEvent event) {
		backhome();
	}
	
	public void backhome() {
		view.backButton.getScene().setRoot(view.startView());
		view.contactlist.getItems().clear();
		view.contactlist.getItems().addAll(model.getObservableList());
		view.adjdustStage();
	}
	
	public void addPhoneField(ActionEvent event) {
		view.dynamicTextfieldAdd(view.phoneFieldList, view.phoneFieldPane);
		view.getStage().sizeToScene();
		view.updateTexts();
	}
	
	public void addEmailField(ActionEvent event) {
		view.dynamicTextfieldAdd(view.emailFieldList, view.emailFieldPane);
		view.getStage().sizeToScene();
		view.updateTexts();
	}
	
	public void clearAll(ActionEvent event) {
		model.clearAll();
		view.contactlist.getItems().clear();
	}
	
	public String firstLetterCap(String string) {
		String cap = string.substring(0, 1).toUpperCase() + string.substring(1);
		return cap;
	}
	
	public void filterContact(ActionEvent event) {
		if(view.filterButton.isSelected()) {
			boolean pr;
			if (view.filterPr.isSelected())
				pr = true;
			else 
				pr = false;
			
			boolean bs;
			if (view.filterBs.isSelected())
				bs = true;
			else 
				bs = false;
			
			boolean fav;
			if (view.filterFa.isSelected())
				fav = true;
			else 
				fav = false;
			
			view.getStage().getScene().setRoot(view.startView());
			view.contactlist.getItems().clear();
			view.contactlist.getItems().addAll(model.filteredList(pr, bs, fav));
		}
		if(!view.filterButton.isSelected())
			backhome();
	}
	
	public void createContact(ActionEvent event) {
		App_Contact item = new App_Contact(firstLetterCap(view.prenameField.getText()));
		if (view.typeBox.getValue() != null)
			item.setContact_Type(view.typeBox.getValue());
		if (!view.nameField.getText().isEmpty())
			item.setName(firstLetterCap(view.nameField.getText()));
		for (TextField textField : view.phoneFieldList) {
			if(!textField.getText().isEmpty())
				item.addPhoneNumber(textField.getText());
		}
		for (TextField textField : view.emailFieldList) {
			if(!textField.getText().isEmpty())
				item.addEmail(textField.getText());
		}
		if (view.birthdayPicker.getValue() != null) {
			LocalDate ldate = view.birthdayPicker.getValue();
			Calendar calendar = Calendar.getInstance();
			calendar.set(ldate.getYear(), ldate.getMonthValue(), ldate.getDayOfMonth());
			item.setBirthdate(calendar.getTime());
		}
		model.addContact(item);
		backhome();
	}
	
	public void updateContact(ActionEvent event) {
		App_Contact item = new App_Contact(firstLetterCap(view.prenameField.getText()));
		if (view.typeBox.getValue() != null)
			item.setContact_Type(view.typeBox.getValue());
		if (!view.nameField.getText().isEmpty())
			item.setName(firstLetterCap(view.nameField.getText()));
		for (TextField textField : view.phoneFieldList) {
			if(!textField.getText().isEmpty())
				item.addPhoneNumber(textField.getText());
		}
		for (TextField textField : view.emailFieldList) {
			if(!textField.getText().isEmpty())
				item.addEmail(textField.getText());
		}
		if (view.birthdayPicker.getValue() != null) {
			LocalDate ldate = view.birthdayPicker.getValue();
			Calendar calendar = Calendar.getInstance();
			calendar.set(ldate.getYear(), ldate.getMonthValue(), ldate.getDayOfMonth());
			item.setBirthdate(calendar.getTime());
		}
		
		App_Contact contact = view.contactlist.getSelectionModel().getSelectedItem();
		
		model.updateContact(item, contact);
		backhome();
	}
	
	public void deleteContact(ActionEvent event) {
		App_Contact item = view.contactlist.getSelectionModel().getSelectedItem();
		model.deleteContact(item);
		view.contactlist.getItems().clear();
		view.contactlist.getItems().addAll(model.getObservableList());
	}
	
	public void upload(WindowEvent event) {
		model.upload();
	}

}
