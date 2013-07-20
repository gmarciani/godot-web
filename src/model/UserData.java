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
