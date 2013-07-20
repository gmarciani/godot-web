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
