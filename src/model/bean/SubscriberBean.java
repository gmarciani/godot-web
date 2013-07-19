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

import org.json.JSONObject;

public class SubscriberBean {
	
	private String name;
	private String mail;

	public SubscriberBean() {}

	public String getName() {
		return name;
	}

	public SubscriberBean setName(String name) {
		this.name = name;
		return this;
	}

	public String getMail() {
		return mail;
	}

	public SubscriberBean setMail(String mail) {
		this.mail = mail;
		return this;
	}
	
	public JSONObject toJSON() {
		JSONObject jObj = new JSONObject();
		
		jObj.put("name", this.name);
		jObj.put("mail", this.mail);
		
		return jObj;
	}
	
	public SubscriberBean fromJSON(JSONObject jObj) {
		this.setName(jObj.getString("name"));
		this.setMail(jObj.getString("mail"));
		
		return this;
	}

}
