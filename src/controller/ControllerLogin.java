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
