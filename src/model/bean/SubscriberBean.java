/*  WebGodot: Godot Server-Side Implementation. 
    
   Copyright 2013 Giacomo Marciani <giacomo.marciani@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.   
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
