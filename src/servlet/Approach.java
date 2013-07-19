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

import controller.ControllerCore;
import exception.GodotCarNotFoundException;
import exception.GodotPermissionException;

@WebServlet("/Approach")
public class Approach extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ControllerCore controllerCore;
	       
    public Approach() {
        super();
        this.controllerCore = ControllerCore.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String carName = request.getParameter("carName");
		String username = request.getParameter("username");
		
		int result = 0;
		
		try {
			result = this.controllerCore.approach(carName, username);
		} catch (GodotPermissionException exc) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED); //401
			return;
		} catch (GodotCarNotFoundException exc) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND); //404
			return;
		}
		
		if (result == ControllerCore.DRIVER_UPDATE) {
			response.setStatus(HttpServletResponse.SC_ACCEPTED); //202
			return;
		} else if (result == ControllerCore.MESSAGE_SENT) {
			response.setStatus(HttpServletResponse.SC_CREATED); //201
			return;
		}
	}

}
