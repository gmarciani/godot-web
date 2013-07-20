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
