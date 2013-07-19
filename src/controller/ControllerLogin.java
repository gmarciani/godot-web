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

import dao.UserDAO;
import dao.UserJdbcDAO;
import exception.GodotPermissionException;
import exception.GodotSyntaxException;
import exception.GodotUserNotFoundException;
import model.FactoryResetCode;
import model.Login;
import model.ResetCode;
import model.User;
import model.bean.LoginBean;
import model.bean.UserBean;
import utils.mailer.Mailer;
import utils.mailer.WebMailer;

public class ControllerLogin {
		
	private static ControllerLogin controllerLogin;
	private UserDAO userDAO;
	private Mailer mailer;

	private ControllerLogin() {
		this.userDAO = UserJdbcDAO.getInstance();
		this.mailer = WebMailer.getInstance();		
	}
	
	public static synchronized ControllerLogin getInstance() {
		if (controllerLogin == null) {
			controllerLogin = new ControllerLogin();
		}		
		
		return controllerLogin;
	}
		
	public synchronized UserBean login(LoginBean loginBean) throws GodotPermissionException, GodotSyntaxException {
		Login login = new Login().fromBean(loginBean);
		UserBean utenteBean = this.userDAO.findByLogin(login);	
		if (utenteBean == null) throw new GodotPermissionException();
		return utenteBean;				
	}
	
	public void resetPassword(String username) throws GodotPermissionException {		
		FactoryResetCode factoryResetCode = FactoryResetCode.getInstance();
		ResetCode resetCode = factoryResetCode.createResetCode();
		String resetPassword = String.valueOf(resetCode.getCode());		
		
		try {
			UserBean userBean = this.userDAO.findByUsername(username).setPassword(resetPassword);
			User user = new User().fromBean(userBean);
			this.userDAO.update(user);
			
		} catch (NullPointerException | GodotSyntaxException | GodotUserNotFoundException exc) {
			throw new GodotPermissionException();
		}	
		
		this.notifyPasswordReset(username, resetPassword);
	}
	
	private synchronized void notifyPasswordReset(String username, String resetPassword) {
		UserBean userBean = this.userDAO.findByUsername(username);
		String mail = userBean.getMail();
		this.mailer.sendMail(mail, "Godot", "Hi " + userBean.getUsername() + "!" +
				"\n\nHere your new password:\n\n" + 
				"\tPassword: " + resetPassword + "\n" +
				"\n\nCheers by Godot Team.");
	}

}
