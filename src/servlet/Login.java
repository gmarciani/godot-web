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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import model.bean.LoginBean;
import model.bean.UserBean;
import controller.ControllerLogin;
import exception.GodotPermissionException;
import exception.GodotSyntaxException;

@WebServlet("/Login")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ControllerLogin controllerLogin;

    public Login() {
    	super();
        this.controllerLogin = ControllerLogin.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		LoginBean loginBean = new LoginBean()
		.setUsername(username)
		.setPassword(password);
		
		try {
			
			UserBean userBean = this.controllerLogin.login(loginBean);
			
			Cookie cookieUsername = new Cookie("GodotUsername", loginBean.getUsername());
			Cookie cookiePassword = new Cookie("GodotPassword", loginBean.getPassword());
			cookieUsername.setMaxAge(60*60*24);
			cookiePassword.setMaxAge(60*60*24);
			response.addCookie(cookieUsername);
			response.addCookie(cookiePassword);
			
			HttpSession session = request.getSession(true);
			session.setAttribute("user", userBean);
			
			response.setContentType("application/json");			
			JSONObject jObj = userBean.toJSON();
			PrintWriter out = response.getWriter();
			out.write(jObj.toString());
			out.flush();
			
		} catch (GodotPermissionException | GodotSyntaxException exc) {
			
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);			
			return;
			
		}		
		 
	}

}
