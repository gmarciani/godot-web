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
