package mainPackage.jat_appClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

enum Contact_Type{Private, Business, Favorite}

public class App_Contact implements Comparable<App_Contact>, java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name, prename;
	private Date birthdate;
	private ArrayList<String> email;
	private ArrayList<Integer> phoneNumber;
	private Contact_Type contact_Type;
	private Integer iD;
	
	public App_Contact(String prename) {
		this.prename=prename;
		email = new ArrayList<String>();
		phoneNumber = new ArrayList<Integer>();
	}
	
	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		if (this.iD == null)
		this.iD = iD;
	}

	public int compareTo (App_Contact other) {
		if(this.getName() != null) {
			try{return this.getName().compareTo(other.getName());
			} catch (Exception e){
				return this.getName().compareTo(other.getPrename());
			}
		}else {
			try {return this.getPrename().compareTo(other.getName());
			} catch (Exception e) {
				return this.getPrename().compareTo(other.getPrename());
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Contact_Type getContact_Type() {
		return contact_Type;
	}

	public void setContact_Type(Contact_Type contact_Type) {
		this.contact_Type = contact_Type;
	}

	public ArrayList<String> getEmail() {
		return email;
	}
	
	public void setPhones(ArrayList<Integer> phones) {
		this.phoneNumber = phones;
	}
	
	public void setEmails(ArrayList<String> emails) {
		email = emails;
	}

	public ArrayList<Integer> getPhoneNumbers() {
		return phoneNumber;
	}
	
	
	public void addPhoneNumber (String phoneNR) {
		int phone = Integer.parseInt(phoneNR);
		this.phoneNumber.add(phone);
	}
	
	public void addEmail (String email) {
		this.email.add(email);
	}

}
