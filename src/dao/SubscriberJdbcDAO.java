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
