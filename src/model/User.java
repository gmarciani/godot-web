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
import model.bean.UserBean;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UserData userData;
	private Login login;

	public User(UserData userData, Login login) {
		this.setUserData(userData);
		this.setLogin(login);
	}
	
	public User() {
		//
	}		

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
	public User fromBean(UserBean userBean) throws GodotSyntaxException {
		UserData bUserDati = new UserData(userBean.getFirstname(), userBean.getLastname(), userBean.getMail());
		Login bLogin = new Login(userBean.getUsername(), userBean.getPassword());
		return new User(bUserDati, bLogin);			
	}

}
