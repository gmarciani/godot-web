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

public class UserData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String firstname;
	private String lastname;
	private String mail;

	public UserData(String firstname, String lastname, String mail) throws GodotSyntaxException {
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setMail(mail);			
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) throws GodotSyntaxException {
		if (firstname.isEmpty() || firstname == null) {
			throw new GodotSyntaxException();
		}
		
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) throws GodotSyntaxException {
		if (lastname.isEmpty() || lastname == null) {
			throw new GodotSyntaxException();
		}
		
		this.lastname = lastname;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) throws GodotSyntaxException {
		if (mail.isEmpty() || mail == null) {
			throw new GodotSyntaxException();
		}
		
		this.mail = mail;
	}

}
