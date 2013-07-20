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

package controller;

import java.util.List;

import model.bean.CarBean;
import model.bean.UserBean;
import dao.CarDAO;
import dao.CarJdbcDAO;
import dao.UserDAO;
import dao.UserJdbcDAO;
import exception.GodotCarNotFoundException;
import exception.GodotPermissionException;

public class ControllerCore {
	
	public static final int DRIVER_UPDATE = 1;
	public static final int MESSAGE_SENT = 2;
	
	private static ControllerCore controllerCore;
	
	private UserDAO userDAO;
	private CarDAO carDAO;

	private ControllerCore() {
		this.userDAO = UserJdbcDAO.getInstance();
		this.carDAO = CarJdbcDAO.getInstance();
	}
	
	public static synchronized ControllerCore getInstance() {
		if (controllerCore == null) {
			controllerCore = new ControllerCore();
		}
		
		return controllerCore;
	}

	public synchronized int approach(String carName, String username) throws GodotPermissionException, GodotCarNotFoundException {
		if (this.checkCoOwnership(carName, username)) {
			this.updateDriver(carName, username);
			return DRIVER_UPDATE;
		} else {
			this.pushMessageToCar(carName);
			return MESSAGE_SENT;
		}
	}	
	
	private synchronized void updateDriver(String carName, String username) throws GodotPermissionException  {
		this.carDAO.updateCarDriver(carName, username);
	}
	
	private synchronized void pushMessageToCar(String carName) throws GodotCarNotFoundException {
		this.carDAO.pushMessage(carName);
	}
	
	public synchronized boolean listen(String username) {
		CarBean carBean = this.carDAO.findByDriver(username);
		if (carBean == null) return false;
		return carBean.getMessage();
	}
	
	public synchronized void popMessage(String username){
		try {
			this.carDAO.popMessage(username);
		} catch (GodotCarNotFoundException exc) {
			return;
		}		
	}	
	
	public synchronized CarBean findCarByDriver(String username) {
		CarBean carBean = this.carDAO.findByDriver(username);
		return carBean;
	}
	
	public synchronized List<CarBean> findCarByOwner(String username) {
		List<CarBean> carList = this.carDAO.findByOwner(username);
		return carList;
	}
	
	private synchronized boolean checkCoOwnership(String carName, String username) throws GodotPermissionException {
		UserBean userBean = this.userDAO.findByUsername(username);
		if (userBean == null) throw new GodotPermissionException();
		return this.carDAO.isCoOwner(carName, username);	
	}
		
}
