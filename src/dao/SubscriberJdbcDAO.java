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
import java.sql.SQLException;
import java.sql.Statement;
import db.ConnectionManager;
import db.WebConnectionManager;
import model.Subscriber;

public class SubscriberJdbcDAO implements SubscriberDAO{
	
	private static SubscriberJdbcDAO singletonSubscriberDAO; 
	
	private static ConnectionManager connectionManager;
	
	private SubscriberJdbcDAO() {
		connectionManager = WebConnectionManager.getInstance();		
	}
	
	public static synchronized SubscriberDAO getInstance() {
		if(singletonSubscriberDAO == null) {
			singletonSubscriberDAO = new SubscriberJdbcDAO();
		}		
		
		return singletonSubscriberDAO;
	}

	@Override
	public void save(Subscriber subscriber) {
		String SQL_INSERT_SUBSCRIBER = "INSERT INTO `subscriber` " +
				"(`name`, `mail`) " +
				"VALUES (\"" + subscriber.getName() + "\", " +
						"\"" + subscriber.getMail() + "\")";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(SQL_INSERT_SUBSCRIBER);
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
		}
	}			

}
