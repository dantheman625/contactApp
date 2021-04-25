package mainPackage.jat_appClasses;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mainPackage.jat.ServiceLocator;
import mainPackage.jat_abstractClasses.Model;

public class App_Model extends Model{
	ServiceLocator serviceLocator;
	TreeSet<App_Contact> treeSet = new TreeSet<App_Contact>();
	
	public App_Model() {
		serviceLocator = serviceLocator.getServiceLocator();
		serviceLocator.getLogger().info("Application model initialized");
		ArrayList<Object> data = new ArrayList<Object>();
		try {
			FileInputStream fis = new FileInputStream("contactdb.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			data = (ArrayList<Object>)ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}
		
		treeSet = (TreeSet<App_Contact>) data.get(0);
	}
	
	public void clearAll() {
		treeSet.clear();
	}
	
	public ArrayList<App_Contact> getArrayList(){
		return new ArrayList<>(treeSet);
	}
	
	public ObservableList<App_Contact> getObservableList(){
		ObservableList<App_Contact> observableList = FXCollections.observableArrayList();
		observableList.addAll(getArrayList());
		return observableList;
	}
	
	public ObservableList<App_Contact> filteredList(boolean pr, boolean bs, boolean fav){
		ObservableList<App_Contact> observableList = FXCollections.observableArrayList();
		ArrayList<App_Contact> unfilter = getArrayList();
		ArrayList<App_Contact> filter = new ArrayList<App_Contact>();
		
		for (App_Contact item : unfilter) {
			if(pr == true) {
				if(item.getContact_Type()==Contact_Type.Private)
					filter.add(item);
			}
			if(bs == true) {
				if(item.getContact_Type()==Contact_Type.Business)
					filter.add(item);
			}
			if(fav == true) {
				if(item.getContact_Type()==Contact_Type.Favorite)
					filter.add(item);
			}
		}
		observableList.addAll(filter);
		return observableList;
	}
	
	public int generateID() {
		int iD = 0;
		boolean duplicate = false;
		Random rand = new Random();
		while (iD == 0 || duplicate == true) {
		iD = rand.nextInt(90000) + 10000;
		if(getArrayList().size()== 0) {
		for (App_Contact item : getArrayList()) {
			if (iD == item.getiD())
				duplicate = true;
		}
		}
		}
		return iD;
	}
	
	public void deleteContact(App_Contact item) {
		int id = item.getiD();
		for (App_Contact contact : treeSet) {
			if (id == contact.getiD()) {
				treeSet.remove(contact);
				break;
			}
		}
	}
	
	public void addContact(App_Contact item) {
		item.setiD(generateID());
		treeSet.add(item);
	}
	
	public void updateContact(App_Contact item, App_Contact contact) {
		if (item.getPrename() != contact.getPrename())
			contact.setPrename(item.getPrename());
		if (item.getName() != contact.getName())
			contact.setName(item.getName());
		if (item.getContact_Type() != contact.getContact_Type())
			contact.setContact_Type(item.getContact_Type());
		if (item.getPhoneNumbers() != contact.getPhoneNumbers())
			contact.setPhones(item.getPhoneNumbers());
		if (item.getEmail() != contact.getEmail())
			contact.setEmails(item.getEmail());
		if (item.getBirthdate() != contact.getBirthdate())
			contact.setBirthdate(item.getBirthdate());
	}
	public void upload() {
		try {
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(treeSet);
			FileOutputStream fos = new FileOutputStream("contactdb.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();;
		}
	}

}
