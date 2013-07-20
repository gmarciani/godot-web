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
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import model.bean.CarBean;

import controller.ControllerCore;

@WebServlet("/FindCarsByOwner")
public class FindCarsByOwner extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ControllerCore controllerCore;
       
    public FindCarsByOwner() {
        super();
        this.controllerCore = ControllerCore.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		List<CarBean> carList = this.controllerCore.findCarByOwner(username);
		
		if (!carList.isEmpty()) {
			response.setContentType("application/json");
			JSONArray jArray = new JSONArray();
			for (int count = 0; count < carList.size(); count ++) {
				CarBean car = carList.get(count);
				JSONObject jObj = car.toJSON();
				jArray.put(jObj);
			}
			
			PrintWriter out = response.getWriter();
			out.write(jArray.toString());
			out.flush();
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);			
			return;
		}
	}

}
