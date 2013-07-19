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

import exception.GodotSyntaxException;
import model.bean.SubscriberBean;

public class Subscriber {
	
	private String name;
	private String mail;

	public Subscriber(String name, String mail) throws GodotSyntaxException {
		this.setName(name);
		this.setMail(mail);
	}
	
	public Subscriber() {}

	public String getName() {
		return name;
	}

	public void setName(String name) throws GodotSyntaxException {
		if (name.isEmpty() || name == null) {
			throw new GodotSyntaxException();
		}
		
		this.name = name;
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
	
	public Subscriber fromBean(SubscriberBean subscriberBean) throws GodotSyntaxException {
		return new Subscriber(subscriberBean.getName(), subscriberBean.getMail());
	}
	

}
