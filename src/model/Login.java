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

package model;

import java.io.Serializable;
import exception.GodotSyntaxException;
import model.bean.LoginBean;

public class Login implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	public Login(String username, String password) throws GodotSyntaxException{
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public Login() {
		//
	}
	
	public boolean equals(Login other) {
		return (this.getUsername().equals(other.getUsername()) && this.getPassword().equals(other.getPassword()));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws GodotSyntaxException {
		if (username.isEmpty() || username == null) {
			throw new GodotSyntaxException();
		}
		
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws GodotSyntaxException {
		if (password.isEmpty() || password == null) {
			throw new GodotSyntaxException();
		}
		
		this.password = password;
	}
	
	public Login fromBean(LoginBean loginBean) throws GodotSyntaxException  {
		return new Login(loginBean.getUsername(), loginBean.getPassword());
	}
}
