/*  WebGodot: Godot Server-Side Implementation. 
    Copyright (C) 2013  Giacomo Marciani <giacomo.marciani@gmail.com>.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package model.bean;

import java.io.Serializable;

import org.json.JSONObject;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	private String firstname;
	private String lastname;
	private String mail;
	private String username;
	private String password;

	public UserBean(){}

	public String getFirstname() {
		return firstname;
	}

	public UserBean setFirstname(String nome) {
		this.firstname = nome;
		return this;
	}

	public String getLastname() {
		return this.lastname;
	}

	public UserBean setLastname(String cognome) {
		this.lastname = cognome;
		return this;
	}

	public String getMail() {
		return this.mail;
	}

	public UserBean setMail(String mail) {
		this.mail = mail;
		return this;
	}

	public String getUsername() {
		return this.username;
	}

	public UserBean setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return this.password;
	}

	public UserBean setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public JSONObject toJSON() {
		JSONObject jObj = new JSONObject();
		
		jObj.put("firstname", this.firstname);
		jObj.put("lastname", this.lastname);
		jObj.put("mail", this.mail);
		jObj.put("username", this.username);
		jObj.put("password", this.password);
		
		return jObj;
	}
	
	public UserBean fromJSON(JSONObject jObj) {
		this.setFirstname(jObj.getString("firstname"));
		this.setLastname(jObj.getString("lastname"));
		this.setMail(jObj.getString("mail"));
		this.setUsername(jObj.getString("username"));
		this.setPassword(jObj.getString("password"));
		
		return this;
	}

}
