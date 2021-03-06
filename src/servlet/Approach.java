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
