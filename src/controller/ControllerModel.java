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

import utils.mailer.Mailer;
import utils.mailer.WebMailer;
import model.Car;
import model.Login;
import model.User;
import model.bean.CarBean;
import model.bean.LoginBean;
import model.bean.UserBean;
import dao.CarDAO;
import dao.CarJdbcDAO;
import dao.UserDAO;
import dao.UserJdbcDAO;
import exception.GodotConflictException;
import exception.GodotPermissionException;
import exception.GodotSyntaxException;
import exception.GodotUserNotFoundException;

public class ControllerModel {
	
	private static ControllerModel controllerModel;
	
	private UserDAO userDAO;
	private CarDAO carDAO;
	
	private Mailer mailer;

	private ControllerModel() {
		this.userDAO = UserJdbcDAO.getInstance();
		this.carDAO = CarJdbcDAO.getInstance();
		this.mailer = WebMailer.getInstance();
	}
	
	public static synchronized ControllerModel getInstance() {
		if (controllerModel == null) {
			controllerModel = new ControllerModel();
		}
		
		return controllerModel;
	}
	
	public synchronized void insertUser(UserBean userBean) throws GodotSyntaxException, GodotConflictException {
		User user = new User().fromBean(userBean);
		this.userDAO.save(user);
		this.notifyUserInsertion(user);
	}
	
	public synchronized void insertCar(CarBean carBean) throws GodotSyntaxException, GodotConflictException, GodotPermissionException {		
		Car car = new Car().fromBean(carBean);
		this.carDAO.save(car);		
		this.notifyCarInsertion(car.getName(), carBean.getOwnerUsername());		
	}
	
	public synchronized void deleteCar(String carName, LoginBean loginBean) throws GodotPermissionException {		
		if (checkOwnership(carName, loginBean)) {
			this.carDAO.delete(carName);		
			this.notifyCarRemoval(carName, loginBean.getUsername());
		} else {		
			throw new GodotPermissionException();
		}				
	}
	
	public synchronized void insertCoOwner(String carName, LoginBean loginBean, String username) throws GodotPermissionException, GodotUserNotFoundException {
		if (checkOwnership(carName, loginBean)) {
			this.carDAO.addCarCoOwner(carName, username);
			this.notifyOwnershipInsertion(carName, username);	
		} else {
			throw new GodotPermissionException();
		}			
	}	

	public synchronized void deleteCoOwner(String carName, LoginBean loginBean, String username) throws GodotPermissionException {
		if (checkOwnership(carName, loginBean)) {
			this.carDAO.deleteCarCoOwner(carName, username);
			this.notifyOwnershipRemoval(carName, username);
		} else {
			throw new GodotPermissionException();
		}
					
	}
	
	private synchronized boolean checkOwnership(String carName, LoginBean loginBean) {
		Login login;
		try {
			login = new Login().fromBean(loginBean);
		} catch (GodotSyntaxException exc) {
			return false;
		}
		UserBean userBean = this.userDAO.findByLogin(login);
		if (userBean == null) return false;
		String username = userBean.getUsername();
		return this.carDAO.isOwner(carName, username);
	}
	
	private void notifyUserInsertion(User user) {
		String mail = user.getUserData().getMail();
		this.mailer.sendMail(mail, "Godot", "Hi " + user.getLogin().getUsername() + "!" +
				"\n\nHere your registration data:\n\n" + 
				"\tFirstname: " + user.getUserData().getFirstname() + "\n" +
				"\tLastname: " + user.getUserData().getLastname() + "\n" +
				"\tMail: " + user.getUserData().getMail() + "\n" +
				"\tUsername: " + user.getLogin().getUsername() + "\n" +
				"\tPassword: " + user.getLogin().getPassword() + "\n" +
				"\n\nCheers by Godot Team.");
	}
	
	private void notifyCarInsertion(String carName, String username) {
		UserBean userBean = this.userDAO.findByUsername(username);
		if (userBean == null) return;
		String mail = userBean.getMail();
		CarBean carBean = this.carDAO.findByName(carName);
		this.mailer.sendMail(mail, "Godot", "Hi " + userBean.getUsername() + "!" +
				"\n\nHere your new car registration data:\n\n" + 
				"\tCar Name: " + carName + "\n" +
				"\tOwner: " + carBean.getOwnerUsername() + "\n" +
				"\tDriver: " + carBean.getDriverUsername() + "\n" +
				"\n\nCheers by Godot Team.");
	}
	
	private void notifyCarRemoval(String carName, String username) {
		UserBean userBean = this.userDAO.findByUsername(username);
		if (userBean == null) return;
		String mail = userBean.getMail();
		this.mailer.sendMail(mail, "Godot", "Hi " + userBean.getUsername() + "!" +
				"\n\nThis mail is for notify you about the removal of your car from Godot system.\n\n" +
				"\tCar Name: " + carName + "\n" +
				"\n\nCheers by Godot Team.");
	}
	
	private void notifyOwnershipInsertion(String carName, String username) {
		UserBean userBean = this.userDAO.findByUsername(username);
		if (userBean == null) return;
		String mail = userBean.getMail();
		CarBean carBean = this.carDAO.findByName(carName);
		this.mailer.sendMail(mail, "Godot", "Hi " + userBean.getUsername() + "!" +
				"\n\nHere your new car data:\n\n" + 
				"\tCar Name: " + carBean.getName() + "\n" +
				"\tOwner: " + carBean.getOwnerUsername() + "\n" +
				"\tDriver: " + carBean.getDriverUsername() + "\n" +
				"\n\nCheers by Godot Team.");
		
	}
	
	private void notifyOwnershipRemoval(String carName, String username){
		UserBean userBean = this.userDAO.findByUsername(username);
		if (userBean == null) return;
		String mail = userBean.getMail();
		this.mailer.sendMail(mail, "Godot", "Hi " + userBean.getUsername() + "!" +
				"\n\nThis mail is for notify you about the removal of your ownership on car.\n\n" +
				"\tCar Name: " + carName + "\n" +
				"\n\nCheers by Godot Team.");		
	}
		
}
