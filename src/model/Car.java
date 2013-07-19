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
import model.bean.CarBean;

public class Car implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String ownerUsername;
	private String driverUsername;
	private boolean message;
	
	public Car(String name, String ownerUsername, String driverUsername, boolean message) throws GodotSyntaxException {
		this.setName(name);
		this.setOwnerUsername(ownerUsername);
		this.setDriverUsername(driverUsername);
		this.setMessage(message);
	}
	
	public Car() {}

	public String getName() {
		return name;
	}

	public void setName(String name) throws GodotSyntaxException {
		if (name.isEmpty() || name == null) {
			throw new GodotSyntaxException();
		}
		
		this.name = name;
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) throws GodotSyntaxException {
		if (ownerUsername.isEmpty() || ownerUsername == null) {
			throw new GodotSyntaxException();
		}
		
		this.ownerUsername = ownerUsername;
	}

	public String getDriverUsername() {
		return driverUsername;
	}

	public void setDriverUsername(String driverUsername) {
		this.driverUsername = driverUsername;
	}

	public boolean getMessage() {
		return message;
	}

	public void setMessage(boolean message) {
		this.message = message;
	}
	
	public Car fromBean(CarBean carBean) throws GodotSyntaxException {
		return new Car(carBean.getName(), carBean.getOwnerUsername(), carBean.getDriverUsername(), carBean.getMessage());
	}	

}
