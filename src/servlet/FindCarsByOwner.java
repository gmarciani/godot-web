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
