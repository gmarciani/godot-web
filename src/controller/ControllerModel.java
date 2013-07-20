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
		//CarBean carBean = this.carDAO.findByName(carName);
		this.mailer.sendMail(mail, "Godot", "Hi " + userBean.getUsername() + "!" +
				"\n\nHere your new car registration data:\n\n" + 
				"\tCar Name: " + carName + "\n" +
				"\tOwner: " + username + "\n\n" +
				"\tDownload your QRCode from here:\n\n" +
				"\thttp://qrfree.kaywa.com/?l=1&s=8&d=" + carName + "\n" +
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
