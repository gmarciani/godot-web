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
