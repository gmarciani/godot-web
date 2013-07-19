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

package db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WebConnectionManager implements ConnectionManager {
	
	private static WebConnectionManager singletonConnectionManager = null;
	
	private DataSource dataSource;
    private Connection connection;

	private WebConnectionManager() {
		try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/GodotDB");             
        } catch (NamingException e) {
            e.printStackTrace();
        }
	}
	
	public static synchronized WebConnectionManager getInstance() {
		if (singletonConnectionManager == null) {
			singletonConnectionManager = new WebConnectionManager();
		}
		
		return singletonConnectionManager;
	}
	
	@Override
	public synchronized Connection getConnection() {
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	@Override
	public void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}		
	}
	
	@Override
	public synchronized void close(Connection connection, Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}
		
		close(connection);
	}
	
	@Override
	public synchronized void close(Connection connection, Statement statement, ResultSet result) {
		if (result != null) {
			try {
				result.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}
		
		close(connection, statement);
	}	

}
