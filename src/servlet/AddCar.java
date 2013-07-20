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
