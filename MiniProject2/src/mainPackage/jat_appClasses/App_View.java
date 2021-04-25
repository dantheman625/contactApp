package mainPackage.jat_appClasses;

import java.awt.GridBagConstraints;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;


import mainPackage.jat.ServiceLocator;
import mainPackage.jat_abstractClasses.View;
import mainPackage.jat_commonClasses.Translator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class App_View extends View<App_Model>{
	MenuBar menuBar;
	Menu menuFile;
	Menu menuFileLanguage;
	Menu menuAbout;
	Menu menuView;
	Menu menuVisibility;
	Menu menuData;
	MenuItem itemDark;
	MenuItem itemLight;
	MenuItem itemCreate;
	MenuItem itemUpdate;
	MenuItem itemClear;
	MenuItem itemHelp;
	MenuItem itemDelete;
	MenuItem itemFilter;
	
	Label lblName = new Label(" ");
	Label lblPreName = new Label(" ");
	Label lblPhone = new Label(" ");
	Label lblEmail= new Label(" ");
	Label lblType= new Label(" ");
	Label lblBirthday= new Label(" ");
	Label lbltitleAdd;
	Label lbltitleUpdate;
	Label lblTitle;
	Label lbltitleFilter;
	Label lblFilter;
	Label lblPrivate;
	Label lblBusiness;
	Label lblFavorite;
	
	TextField prenameField;
	TextField nameField;
	DatePicker birthdayPicker;
	
	ToggleButton filterButton = new ToggleButton();
	CheckBox filterPr = new CheckBox();
	CheckBox filterBs = new CheckBox();
	CheckBox filterFa = new CheckBox();
	
	Button backButton = new Button();
	Button addButton = new Button();
	Button updateButton = new Button();
	Button applyFilter = new Button();
	Button addphneButton = new Button("+");
	Button addEmailButton= new Button("+");
	
	VBox detailsBox;
	GridPane phoneFieldPane;
	GridPane emailFieldPane;
	ColumnConstraints cc = new ColumnConstraints();
	
	ListView<App_Contact> contactlist;
	
	ArrayList<TextField> phoneFieldList = new ArrayList<TextField>();
	ArrayList<TextField> emailFieldList = new ArrayList<TextField>();
	
	ComboBox<Contact_Type> typeBox = new ComboBox<Contact_Type>();
	
	public App_View(Stage stage, App_Model model) {
		super(stage, model);
		ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
		cc.setMinWidth(120);
		
	}
	
	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();  
	    Logger logger = sl.getLogger();
	    
	    
	    contactlist = new ListView<App_Contact>();
	    contactlist.getStyleClass().add("contactlist");
	    contactlist.getItems().addAll(model.getObservableList());
	    contactlist.setCellFactory(new Callback<ListView<App_Contact>, ListCell<App_Contact>>() {
	    	
	    	@Override
	    	public ListCell<App_Contact> call(ListView<App_Contact> param) {
	    		final ListCell<App_Contact> cell = new ListCell<App_Contact>() {
	    			
	    			@Override
	    			protected void updateItem(App_Contact item, boolean empty) {
	    				super.updateItem(item, empty);
	    				if (item != null) {
	    					if(item.getName() == null)
	    						setText(item.getPrename());
	    					else 
	    						setText(item.getPrename() + " " + item.getName());
	    				} 
	    				else {
	    					setText(null);
	    				}
	    			}
	    		};
	    		return cell;
	    	}});
	    
	    menuBar = menuBar();
	    
	    for (Locale locale : sl.getLocales()) {
	    	MenuItem language = new MenuItem(locale.getLanguage());
	    	menuFileLanguage.getItems().add(language);
	    	language.setOnAction( event -> {
	    		sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
	    		sl.setTranslator(new Translator(locale.getLanguage()));
	    		updateTexts();
	    	});
	    }
	    
        Scene scene = new Scene(startView());
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
	}
	
	public BorderPane startView() {
		
		BorderPane root = new BorderPane();
		
		root.setTop(menuBar);
		
		VBox contactBox = new VBox(contactlist);
		contactBox.getStyleClass().add("box");
		
		lblTitle = new Label();
		HBox contentBox = new HBox();
		detailsBox = new VBox();
		detailsBox.getStyleClass().add("box");
	
		detailsBox.getChildren().add(lblTitle);
		lblTitle.getStyleClass().add("titlelabel");
		
		contentBox.getChildren().addAll(contactBox, detailsBox);
		
		root.setCenter(contentBox);
		
		updateTexts();
		
		return root;
		
	}
	
	public VBox showContact() {
		updateTexts();
		
		App_Contact item = contactlist.getSelectionModel().getSelectedItem();
		
		String name;
		if(item.getName()!=null) 
			name = item.getPrename() + " " + item.getName();
		else 
			name = item.getPrename();
		
		Label prename = new Label(name);
		prename.getStyleClass().add("titlelabel");
		
		GridPane namePane = new GridPane();
		namePane.getStyleClass().add("gridpane");
		namePane.getColumnConstraints().add(cc);
		namePane.add(prename, 0, 0, 2, 1);
		
		if(item.getContact_Type() != null) {
			Label type = new Label(item.getContact_Type().toString());
			namePane.add(type, 0, 1);
		}
		int row = 0;		
		GridPane contactDataPane = new GridPane();
		contactDataPane.getStyleClass().add("gridpane");
		contactDataPane.getColumnConstraints().add(cc);
		
		if(contactlist.getSelectionModel().getSelectedItem().getPhoneNumbers()!=null) {
			for(int phone : contactlist.getSelectionModel().getSelectedItem().getPhoneNumbers()) {
				contactDataPane.add(new Label(lblPhone.getText()), 0, row);
				contactDataPane.add(new Label(Integer.toString(phone)), 1, row);
				row++;
			}
		}
		
		if(contactlist.getSelectionModel().getSelectedItem().getEmail()!=null) {
			for(String email : contactlist.getSelectionModel().getSelectedItem().getEmail()) {
				contactDataPane.add(new Label(lblEmail.getText()), 0, row);
				contactDataPane.add(new Label(email), 1, row);
				row++;
			}
		}
		String datePattern = new String("dd/mm/yyyy");
		DateFormat df = new SimpleDateFormat(datePattern);
		
		if(item.getBirthdate()!=null) {
		contactDataPane.add(lblBirthday, 0, row);
		contactDataPane.add(new Label(df.format(item.getBirthdate())), 1, row);
		}
		VBox topBox = new VBox(namePane, contactDataPane);
		return topBox;
	}
	
	public BorderPane addContactView() {
		phoneFieldList.clear();
		emailFieldList.clear();
		typeBox.getItems().setAll(Contact_Type.values());
		
		HBox titleBox = new HBox();
		titleBox.getStyleClass().add("box");

		lbltitleAdd = new Label();
		titleBox.getChildren().add(lbltitleAdd);
		lbltitleAdd.getStyleClass().add("titlelabel");
		
		HBox typeHBox = new HBox(typeBox);
		typeHBox.getStyleClass().add("box");
		
		GridPane namePane = new GridPane();
		namePane.getStyleClass().add("gridpane");
        namePane.getColumnConstraints().add(cc);
		
		prenameField = new TextField();
		namePane.add(lblPreName, 0, 0);
		namePane.add(prenameField, 1, 0);
		
		nameField = new TextField();
		namePane.add(lblName, 0, 1);
		namePane.add(nameField, 1, 1);

		phoneFieldPane = new GridPane();
		phoneFieldPane.getStyleClass().add("gridpane");
		phoneFieldPane.getColumnConstraints().add(cc);
		phoneFieldPane.add(lblPhone, 0, 0);
		phoneFieldList.add(new TextField());
		phoneFieldPane.add(phoneFieldList.get(0), 1, 0);
		
		HBox phoneButBox = new HBox(addphneButton);
		phoneButBox.getStyleClass().add("box");
		
		emailFieldPane = new GridPane();
		emailFieldPane.getStyleClass().add("gridpane");
		emailFieldPane.getColumnConstraints().add(cc);
		emailFieldPane.add(lblEmail, 0, 0);
		emailFieldList.add(new TextField());
		emailFieldPane.add(emailFieldList.get(0), 1, 0);
		
		HBox emailButBox = new HBox(addEmailButton);
		emailButBox.getStyleClass().add("box");
		
		GridPane birthdayBox = new GridPane ();
		birthdayBox.getStyleClass().add("gridpane");
		birthdayBox.getColumnConstraints().add(cc);
		birthdayPicker = new DatePicker();
		birthdayBox.add(lblBirthday, 0, 0);
		birthdayBox.add(birthdayPicker, 1, 0);
		
		HBox buttonBox = new HBox(addButton, backButton);
		addButton.disableProperty().bind(Bindings.isEmpty(prenameField.textProperty()));
		buttonBox.getStyleClass().add("box");
		
		VBox topBox = new VBox(titleBox, typeHBox, namePane, phoneFieldPane, phoneButBox, emailFieldPane, 
								emailButBox, birthdayBox, buttonBox);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		
		borderPane.setCenter(topBox);
		
		updateTexts();
		
		return borderPane;
		
	}
	
	public BorderPane updateContactView() {
		phoneFieldList.clear();
		emailFieldList.clear();
		updateTexts();
		lbltitleUpdate = new Label();
		lbltitleUpdate.getStyleClass().add("titlelabel");
		App_Contact item = contactlist.getSelectionModel().getSelectedItem();
		GridPane namePane = new GridPane();
		namePane.getStyleClass().add("gridpane");
		namePane.getColumnConstraints().add(cc);
		prenameField = new TextField();
		prenameField.setText(item.getPrename());
		namePane.add(lblPreName, 0, 0);
		namePane.add(prenameField, 1, 0);
		
		nameField = new TextField();
		if(item.getName() != null)
			nameField.setText(item.getName());
		namePane.add(lblName, 0, 1);
		namePane.add(nameField, 1, 1);
		
		typeBox.getItems().setAll(Contact_Type.values());
		if(item.getContact_Type()!= null)
			typeBox.setValue(item.getContact_Type());
		namePane.add(lblType, 0, 2);
		namePane.add(typeBox, 1, 2);
		
		phoneFieldPane = new GridPane();
		phoneFieldPane.getStyleClass().add("gridpane");
		phoneFieldPane.getColumnConstraints().add(cc);
		int phonerow = 0;
		if(item.getPhoneNumbers().size() >0) {
			for(int phone : item.getPhoneNumbers()) {
				phoneFieldPane.add(new Label(lblPhone.getText()), 0, phonerow);
				phoneFieldList.add(new TextField(Integer.toString(phone)));
				phoneFieldPane.add(phoneFieldList.get(phonerow), 1, phonerow);
				phonerow++;
			}
		} else {
			phoneFieldPane.add(lblPhone, 0, 0);
			phoneFieldList.add(new TextField());
			phoneFieldPane.add(phoneFieldList.get(0), 1, 0);
		}
		HBox phoneButBox = new HBox(addphneButton);
		
		emailFieldPane = new GridPane();
		emailFieldPane.getStyleClass().add("gridpane");
		emailFieldPane.getColumnConstraints().add(cc);
		int emailrow = 0;
		if(item.getEmail().size() >0) {
			for(String email : item.getEmail()) {
				emailFieldPane.add(new Label(lblEmail.getText()), 0, emailrow);
				emailFieldList.add(new TextField(email));
				emailFieldPane.add(emailFieldList.get(emailrow), 1, emailrow);
				emailrow++;
			}
		} else {
			emailFieldPane.add(lblEmail, 0, 0);
			emailFieldList.add(new TextField());
			emailFieldPane.add(emailFieldList.get(0), 1, 0);
		}
		HBox emailButBox = new HBox(addEmailButton);
		
		GridPane birthdayBox = new GridPane ();
		birthdayBox.getStyleClass().add("gridpane");
		birthdayBox.getColumnConstraints().add(cc);
		birthdayPicker = new DatePicker();
		if(item.getBirthdate() != null) {
		birthdayPicker.setValue(item.getBirthdate().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate());
		}
		birthdayBox.add(lblBirthday, 0, 0);
		birthdayBox.add(birthdayPicker, 1, 0);
		
		HBox buttonBox = new HBox(updateButton, backButton);
		buttonBox.getStyleClass().add("box");
		updateButton.disableProperty().bind(Bindings.isEmpty(prenameField.textProperty()));
		
		VBox topBox = new VBox(lbltitleUpdate, namePane, phoneFieldPane, phoneButBox, emailFieldPane, 
				emailButBox, birthdayBox, buttonBox);
		
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(topBox);
		borderPane.setTop(menuBar);
		updateTexts();
		return borderPane;
	}
	
	public BorderPane filterView() {
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		
		if(!filterButton.isSelected()) {
		filterPr.setSelected(false);
		filterBs.setSelected(false);
		filterFa.setSelected(false);
		}
		
		filterPr.disableProperty().bind(filterSelected());
		filterBs.disableProperty().bind(filterSelected());
		filterFa.disableProperty().bind(filterSelected());
		
		lbltitleFilter = new Label();
		lbltitleFilter.getStyleClass().add("titlelabel");
		HBox titleBox = new HBox(lbltitleFilter);
		titleBox.getStyleClass().add("box");
		
		GridPane gridPane = new GridPane();
		gridPane.getStyleClass().add("gridpane");
		ColumnConstraints col = new ColumnConstraints();
		col.setMinWidth(120);
		gridPane.getColumnConstraints().add(col);
		lblFilter =  new Label();
		gridPane.add(lblFilter, 0, 0);
		gridPane.add(filterButton, 1, 0);
		gridPane.add(new Label(), 0, 1);
		lblPrivate = new Label();
		gridPane.add(lblPrivate, 0, 2);
		gridPane.add(filterPr, 1, 2);
		lblBusiness = new Label();
		gridPane.add(lblBusiness, 0, 3);
		gridPane.add(filterBs, 1, 3);
		lblFavorite = new Label();
		gridPane.add(lblFavorite, 0, 4);
		gridPane.add(filterFa, 1, 4);
		
		HBox buttonBox = new HBox(applyFilter, backButton);
		buttonBox.getStyleClass().add("box");
		
		VBox topBox = new VBox(titleBox, gridPane, buttonBox);
		
		borderPane.setCenter(topBox);
		updateTexts();
		return borderPane;
	}
	
	
	public void dynamicTextfieldAdd(ArrayList<TextField> arrayList, GridPane pane) {
		int rowcount = arrayList.size();
		Node result = null;
	    ObservableList<Node> childrens = pane.getChildren();

	    for (Node node : childrens) {
	        if(pane.getRowIndex(node) == 0 && pane.getColumnIndex(node) == 0) {
	            result = node;
	            break;
	        }
	    }
		arrayList.add(new TextField());
		try {
		Label label = (Label) result;
		pane.add(new Label(label.getText()), 0, rowcount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pane.add(arrayList.get(rowcount), 1, rowcount);
		
	}
	
	public void dynamicTextfieldEmail() {
		int rowcount = emailFieldList.size();
		emailFieldList.add(new TextField());
		emailFieldPane.add(new Label(lblEmail.getText()), 0, rowcount);
		emailFieldPane.add(emailFieldList.get(rowcount), 1, rowcount);
	}
	
	public BooleanBinding contactSelected() {
		BooleanBinding notselected = contactlist.getSelectionModel().selectedItemProperty().isNull();
		return notselected;
	}
	
	public BooleanBinding filterSelected() {
		BooleanBinding selected = filterButton.selectedProperty().not();
		return selected;
	}
	
	public MenuBar menuBar() {
		menuBar = new MenuBar();
		menuFile = new Menu();
		menuFileLanguage = new Menu();
		itemFilter = new MenuItem();
		menuFile.getItems().addAll(menuFileLanguage, itemFilter);
		
		menuView = new Menu();
		menuVisibility = new Menu();
		menuView.getItems().add(menuVisibility);
		itemDark = new MenuItem();
		itemLight = new MenuItem();
		menuVisibility.getItems().addAll(itemDark, itemLight);
		
		menuAbout = new Menu();
		itemHelp = new MenuItem();
		menuAbout.getItems().add(itemHelp);
		
		menuData = new Menu();
		itemUpdate = new MenuItem();
		itemUpdate.disableProperty().bind(contactSelected());
		itemClear = new MenuItem();
		itemCreate = new MenuItem();
		itemDelete = new MenuItem();
		itemDelete.disableProperty().bind(contactSelected());
		menuData.getItems().addAll(itemUpdate, itemDelete, itemCreate, itemClear);
		
		menuBar.getMenus().addAll(menuFile, menuData, menuView, menuAbout);
		
		return menuBar;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();
        
        // The menu entries
       menuFile.setText(t.getString("program.menu.file"));
       menuFileLanguage.setText(t.getString("program.menu.file.language"));
       itemFilter.setText(t.getString("program.menu.file.filter"));
       menuView.setText(t.getString("program.menu.view"));
       menuVisibility.setText(t.getString("program.menu.view.visibility"));
       itemDark.setText(t.getString("program.menu.view.visibility.dark"));
       itemLight.setText(t.getString("program.menu.view.visibility.light"));
       
       menuAbout.setText(t.getString("program.menu.about"));
       itemHelp.setText(t.getString("program.menu.help"));
       
       menuData.setText(t.getString("program.menu.data"));
       itemUpdate.setText(t.getString("program.menu.data.update"));
       itemCreate.setText(t.getString("program.menu.data.create"));
       itemClear.setText(t.getString("program.menu.data.clear"));
       itemDelete.setText(t.getString("program.menu.data.delete"));
       
       if(lblTitle!=null)
    	   lblTitle.setText(t.getString("program.name"));
       if (lblName != null)
    	   lblName.setText(t.getString("label.name"));
       if (lblPreName != null)
    	   lblPreName.setText(t.getString("label.prename"));
       if (lblPhone != null)
    	   lblPhone.setText(t.getString("label.phone"));
       if (lblEmail != null)
    	   lblEmail.setText(t.getString("label.email"));
       if (lblBirthday != null)
    	   lblBirthday.setText(t.getString("label.birthday"));
       if(lblFilter != null)
    	   lblFilter.setText(t.getString("label.filter"));
       if(lblPrivate != null)
    	   lblPrivate.setText(t.getString("label.private"));
       if(lblBusiness != null)
    	   lblBusiness.setText(t.getString("label.business"));
       if(lblFavorite != null)
    	   lblFavorite.setText(t.getString("label.favorite"));
   
       if(lbltitleAdd != null)
    	   lbltitleAdd.setText(t.getString("label.title.add"));
       if(lbltitleUpdate != null)
    	   lbltitleUpdate.setText(t.getString("label.title.update"));
       if(lbltitleFilter != null)
    	   lbltitleFilter.setText(t.getString("label.title.filter"));
       
       if(backButton != null)
    	   backButton.setText(t.getString("button.back"));
       if(addButton != null)
    	   addButton.setText(t.getString("button.add"));
       if(updateButton != null)	
    	   updateButton.setText(t.getString("button.update"));
       if(applyFilter != null)
    	   applyFilter.setText(t.getString("button.save"));
       
       
       stage.setTitle(t.getString("program.name"));
	}
	
	public void adjdustStage () {
		stage.sizeToScene();
	}
	
}
