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

import java.io.Serializable;

import org.json.JSONObject;

public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

	public LoginBean() {}

	public String getUsername() {
		return this.username;
	}

	public LoginBean setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return this.password;
	}

	public LoginBean setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public JSONObject toJSON() {
		JSONObject jObj = new JSONObject();
		
		jObj.put("username", this.username);
		jObj.put("password", this.password);
		
		return jObj;
	}
	
	public LoginBean fromJSON(JSONObject jObj) {
		this.setUsername(jObj.getString("username"));
		this.setPassword(jObj.getString("password"));
		
		return this;
	}	

}
