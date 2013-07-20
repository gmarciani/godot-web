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

package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.UserBean;
import controller.ControllerModel;
import exception.GodotConflictException;
import exception.GodotSyntaxException;

@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    private ControllerModel controllerModel;
       
    public AddUser() {
        super();
        this.controllerModel = ControllerModel.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String mail = request.getParameter("mail");
		
		UserBean userBean = new UserBean()
		.setFirstname(firstname)
		.setLastname(lastname)
		.setMail(mail)
		.setUsername(username)
		.setPassword(password);
		
		try {
			this.controllerModel.insertUser(userBean);
		} catch (GodotSyntaxException exc) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE); //406
			return;
		} catch (GodotConflictException exc) {
			response.sendError(HttpServletResponse.SC_CONFLICT); //409
			return;
		}
	}

}
