/**
 * @project WebGodot
 * 
 * @package dao
 * 
 * @name CarJdbcDAO.java
 *
 * @description
 *
 * @author Giacomo Marciani
 * 
 */

package model.bean;

import java.io.Serializable;

import org.json.JSONObject;

public class CarBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String ownerUsername;
	private String driverUsername;
	private boolean message;

	public CarBean() {}

	public String getName() {
		return name;
	}

	public CarBean setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getOwnerUsername() {
		return ownerUsername;
	}

	public CarBean setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
		return this;
	}

	public String getDriverUsername() {
		return driverUsername;
	}

	public CarBean setDriverUsername(String driverUsername) {
		this.driverUsername = driverUsername;
		return this;
	}	

	public boolean getMessage() {
		return message;
	}

	public CarBean setMessage(boolean message) {
		this.message = message;
		return this;
	}	
	
	public JSONObject toJSON() {
		JSONObject jObj = new JSONObject();
		
		jObj.put("name", this.name);
		jObj.put("ownerUsername", this.ownerUsername);
		jObj.put("driverUsername", this.driverUsername);
		jObj.put("message", this.message);
		
		return jObj;
	}
	
	public CarBean fromJSON(JSONObject jObj) {
		this.setName(jObj.getString("name"));
		this.setOwnerUsername(jObj.getString("ownerUsername"));
		this.setDriverUsername(jObj.getString("driverUsername"));
		this.setMessage(jObj.getBoolean("message"));
		
		return this;
	}

}