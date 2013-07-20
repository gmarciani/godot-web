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

package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.ConnectionManager;
import db.WebConnectionManager;
import exception.GodotConflictException;
import exception.GodotUserNotFoundException;
import model.Login;
import model.User;
import model.bean.UserBean;

public class UserJdbcDAO implements UserDAO{
	
	private static UserJdbcDAO singletonUtenteDAO; 
	
	private static ConnectionManager connectionManager;
	
	private UserJdbcDAO() {
		connectionManager = WebConnectionManager.getInstance();
	}
	
	public static synchronized UserDAO getInstance() {
		if(singletonUtenteDAO == null) {
			singletonUtenteDAO = new UserJdbcDAO();
		}		
		
		return singletonUtenteDAO;
	}

	@Override
	public synchronized void save(User user) throws GodotConflictException {		
		String SQL_INSERT_USER = "INSERT INTO `user` " +
				"(`username`, `password`, `firstname`, `lastname`, `mail`) " +
				"VALUES (\"" + user.getLogin().getUsername() + "\", " +
						"\"" + user.getLogin().getPassword() + "\", " +
						"\"" + user.getUserData().getFirstname() + "\", " +
						"\"" + user.getUserData().getLastname() + "\", " +
						"\"" + user.getUserData().getMail() + "\")";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(SQL_INSERT_USER);
		} catch (SQLException exc) {
			if (exc.getErrorCode() == 1062) {
				throw new GodotConflictException();
			}
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
		}
	}

	@Override
	public synchronized void update(User user) throws GodotUserNotFoundException {
		String SQL_UPDATE_USER = "UPDATE `user` SET " +
				"`firstname` = \"" + user.getUserData().getFirstname() + "\", " +
				"`lastname` =  \"" + user.getUserData().getLastname() + "\", " +
				"`mail` = \"" + user.getUserData().getMail() + "\", " +
				"`password` = \"" + user.getLogin().getPassword() + "\" " + 
				"WHERE `username` = \"" + user.getLogin().getUsername() + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		int rows = 0;
		
		try {
			statement = connection.createStatement();
			rows = statement.executeUpdate(SQL_UPDATE_USER);
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
			if (rows == 0) throw new GodotUserNotFoundException();
		}
	}
	
	@Override
	public synchronized List<UserBean> findAll() {
		List<UserBean> userList = new ArrayList<UserBean>();
		String SQL_FIND_ALL_USERS = "SELECT * FROM `user`";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_ALL_USERS);
			
			while(result.next()) {
				UserBean utenteBean = new UserBean();
				utenteBean.setFirstname(result.getString("firstname"));
				utenteBean.setLastname(result.getString("lastname"));
				utenteBean.setMail(result.getString("mail"));
				utenteBean.setUsername(result.getString("username"));
				utenteBean.setPassword(result.getString("password"));	
				
				userList.add(utenteBean);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return userList;
	}
	
	@Override
	public synchronized UserBean findByLogin(Login login) {
		String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM `user` " +
				"WHERE `username` = \"" + login.getUsername() + "\" " + 
				"and `password` = \"" + login.getPassword() + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		UserBean utenteBean = null;
		
		try {
			
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_USER_BY_LOGIN);
			
			if(result.next()) {
				utenteBean = new UserBean();
				utenteBean.setFirstname(result.getString("firstname"));
				utenteBean.setLastname(result.getString("lastname"));
				utenteBean.setMail(result.getString("mail"));
				utenteBean.setUsername(result.getString("username"));
				utenteBean.setPassword(result.getString("password"));
			}
			
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return utenteBean;
	}	
	
	@Override
	public UserBean findByUsername(String username) {
		String SQL_FIND_USER_BY_USERNAME = "SELECT * FROM `user` " +
				"WHERE `username` = \"" + username + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		UserBean utenteBean = null;
		
		try {
			
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_USER_BY_USERNAME);
			
			if(result.next()) {
				utenteBean = new UserBean();
				utenteBean.setFirstname(result.getString("firstname"));
				utenteBean.setLastname(result.getString("lastname"));
				utenteBean.setMail(result.getString("mail"));
				utenteBean.setUsername(result.getString("username"));
				utenteBean.setPassword(result.getString("password"));
			}
			
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return utenteBean;
	}	

}
