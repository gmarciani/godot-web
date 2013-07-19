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
import model.bean.CarBean;
import controller.ControllerModel;
import exception.GodotConflictException;
import exception.GodotPermissionException;
import exception.GodotSyntaxException;

@WebServlet("/AddCar")
public class AddCar extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ControllerModel controllerModel;
       
    public AddCar() {
        super();
        this.controllerModel = ControllerModel.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String carName = request.getParameter("carName");
		String ownerUsername = request.getParameter("username");
		
		CarBean carBean = new CarBean()
		.setName(carName)
		.setOwnerUsername(ownerUsername)
		.setDriverUsername(ownerUsername)
		.setMessage(false);
		
		try {
			this.controllerModel.insertCar(carBean);
			return;
		} catch (GodotSyntaxException exc) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE); //406
			return;
		} catch (GodotConflictException exc) {
			response.sendError(HttpServletResponse.SC_CONFLICT); //409
			return;
		} catch (GodotPermissionException exc) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED); //401
			return;
		}		
	}

}
