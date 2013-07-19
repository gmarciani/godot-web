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
