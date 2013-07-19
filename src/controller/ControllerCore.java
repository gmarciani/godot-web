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
