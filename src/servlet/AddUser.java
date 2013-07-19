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
