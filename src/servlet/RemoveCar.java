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
import model.bean.LoginBean;
import controller.ControllerModel;
import exception.GodotPermissionException;

@WebServlet("/RemoveCar")
public class RemoveCar extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ControllerModel controllerModel;
       
    public RemoveCar() {
        super();
        this.controllerModel = ControllerModel.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String carName = request.getParameter("carName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		LoginBean loginBean = new LoginBean()
		.setUsername(username)
		.setPassword(password);
		
		try {
			this.controllerModel.deleteCar(carName, loginBean);
			return;
		} catch (GodotPermissionException exc) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED); //404
			return;
		}			
	}

}
