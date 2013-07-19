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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.ConnectionManager;
import db.WebConnectionManager;
import exception.GodotCarNotFoundException;
import exception.GodotConflictException;
import exception.GodotPermissionException;
import exception.GodotUserNotFoundException;
import model.Car;
import model.bean.CarBean;

public class CarJdbcDAO implements CarDAO{
	
	private static CarJdbcDAO singletonCarDAO; 
	
	private static ConnectionManager connectionManager;
	
	private CarJdbcDAO() {
		connectionManager = WebConnectionManager.getInstance();
	}
	
	public static synchronized CarDAO getInstance() {
		if(singletonCarDAO == null) {
			singletonCarDAO = new CarJdbcDAO();
		}		
		
		return singletonCarDAO;
	}

	@Override
	public void save(Car car) throws GodotConflictException, GodotPermissionException {
		String SQL_INSERT_CAR = "INSERT INTO `car` " +
				"(`car_name`, `owner_username`, `driver_username`) " +
				"VALUES (\"" + car.getName() + "\", " +
						"\"" + car.getOwnerUsername() + "\")";
		
		String SQL_INSERT_OWNERSHIP = "INSERT INTO `ownership` " +
				"(`car_name`, `owner_username`) " +
				"VALUES (\"" + car.getName() + "\", " +
						"\"" + car.getOwnerUsername() + "\")";				
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(SQL_INSERT_CAR);
			statement.executeUpdate(SQL_INSERT_OWNERSHIP);						
		} catch (SQLException exc) {
			if (exc.getErrorCode() == 1062) { //Primary Key Duplication
				throw new GodotConflictException();
			} else if (exc.getErrorCode() == 1452) { //Foreign Key Constraint Error
				throw new GodotPermissionException();
			}
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
		}
	}
	
	@Override
	public void delete(String carName) {
		String SQL_DELETE_OWNERSHIP = "DELETE FROM `ownership` " +
				"WHERE `car_name` = \"" + carName + "\"";
		
		String SQL_DELETE_CAR = "DELETE FROM `car` " +
				"WHERE `car_name` = \"" + carName + "\"";		
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(SQL_DELETE_OWNERSHIP);
			statement.executeUpdate(SQL_DELETE_CAR);
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
		}
	}
	
	@Override
	public void addCarCoOwner(String carName, String username) throws GodotUserNotFoundException {
		String SQL_INSERT_CAR_CO_OWNER = "INSERT INTO `ownership` " +
				"(`car_name`, `owner_username`) " +
				"VALUES (\"" + carName + "\", " +
						"\"" + username + "\")";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(SQL_INSERT_CAR_CO_OWNER);
		} catch (SQLException exc) {
			if (exc.getErrorCode() == 1452) {
				throw new GodotUserNotFoundException();
			}
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
		}
	}

	@Override
	public void deleteCarCoOwner(String carName, String username) throws GodotPermissionException {
		
		if (this.isOwner(carName, username)) throw new GodotPermissionException();
			
		String SQL_DELETE_CAR_CO_OWNER = "DELETE FROM `ownership` " +
				"WHERE `car_name` = \"" + carName + "\"" +
						"and `owner_username` = \"" + username + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(SQL_DELETE_CAR_CO_OWNER);
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
		}
	}

	@Override
	public void updateCarDriver(String carName, String username) throws GodotPermissionException {
		if (!this.isCoOwner(carName, username)) throw new GodotPermissionException();
			
		String SQL_UPDATE_CAR_DRIVER = "UPDATE `car` SET " +
				"`driver_username` = \"" + username + "\" " +
				"WHERE `car_name` = \"" + carName + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(SQL_UPDATE_CAR_DRIVER);
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
		}		
	}	
	
	@Override
	public void pushMessage(String carName) throws GodotCarNotFoundException {
		String SQL_PUSH_MESSAGE = "UPDATE `car` SET " +
				"`message` = 1 " +
				"WHERE `car_name` = \"" + carName + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		int rows = 0;
		
		try {
			statement = connection.createStatement();
			rows = statement.executeUpdate(SQL_PUSH_MESSAGE);
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
			if (rows == 0) throw new GodotCarNotFoundException();
		}
	}

	@Override
	public void popMessage(String username) throws GodotCarNotFoundException {
		String SQL_POP_MESSAGE = "UPDATE `car` SET " +
				"`message` = 0 " + 
				"WHERE `driver_username` = \"" + username + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		int rows = 0;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(SQL_POP_MESSAGE);
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement);
			if (rows == 0) throw new GodotCarNotFoundException();
		}
	}
	

	@Override
	public List<CarBean> findAll() {
		List<CarBean> carList = new ArrayList<CarBean>();
		String SQL_FIND_ALL_CARS = "SELECT * FROM `car`";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_ALL_CARS);
			
			while(result.next()) {
				CarBean carBean = new CarBean();
				carBean.setName((result.getString("car_name")));
				carBean.setOwnerUsername((result.getString("owner_username")));
				carBean.setDriverUsername((result.getString("driver_username")));
				carBean.setMessage((result.getBoolean("message")));	
				
				carList.add(carBean);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return carList;
	}
	
	@Override
	public List<CarBean> findByOwner(String username) {
		List<CarBean> carList = new ArrayList<CarBean>();
		String SQL_FIND_CARS_BY_OWNER = "SELECT * FROM `ownership` " +
				"WHERE `owner_username` = \"" + username + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_CARS_BY_OWNER);
			
			while(result.next()) {
				CarBean carBean = new CarBean();
				carBean.setName((result.getString("car_name")));
				//carBean.setOwnerUsername((result.getString("owner_username")));
				//carBean.setDriverUsername((result.getString("driver_username")));
				//carBean.setMessage((result.getBoolean("message")));	
				
				carList.add(carBean);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return carList;
	}

	@Override
	public CarBean findByName(String carName) {
		String SQL_FIND_CAR_BY_NAME = "SELECT * FROM `car` " +
				"WHERE `car_name` = \"" + carName + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		CarBean carBean = null;
		
		try {
			
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_CAR_BY_NAME);
			
			if(result.next()) {
				carBean = new CarBean();
				carBean.setName(result.getString("car_name"));
				carBean.setOwnerUsername((result.getString("owner_username")));
				carBean.setDriverUsername((result.getString("driver_username")));
				carBean.setMessage(result.getBoolean("message"));
			}
			
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return carBean;
	}

	
	@Override
	public CarBean findByDriver(String username) {
		String SQL_FIND_CAR_BY_DRIVER = "SELECT * FROM `car` " +
				"WHERE `driver_username` = \"" + username + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		CarBean carBean = null;
		
		try {
			
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_CAR_BY_DRIVER);
			
			if(result.next()) {
				carBean = new CarBean();
				carBean.setName(result.getString("car_name"));
				carBean.setOwnerUsername((result.getString("owner_username")));
				carBean.setDriverUsername((result.getString("driver_username")));
				carBean.setMessage(result.getBoolean("message"));
			}
			
		} catch (SQLException exc) {
			System.out.println("exc");
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return carBean;
	}
	
	@Override
	public boolean isOwner(String carName, String username) {
		String SQL_FIND_OWNER = "SELECT * FROM `car` " +
				"WHERE `car_name` = \"" + carName + "\" " + 
				"and `owner_username` = \"" + username + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		try {
			
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_OWNER);
			
			if(result.next()) {
				return true;
			}
			
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return false;
	}
	
	@Override
	public boolean isCoOwner(String carName, String username) {
		String SQL_FIND_CO_OWNER = "SELECT * FROM `ownership` " +
				"WHERE `car_name` = \"" + carName + "\" " + 
				"and `owner_username` = \"" + username + "\"";
		
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		try {
			
			statement = connection.createStatement();
			result = statement.executeQuery(SQL_FIND_CO_OWNER);
			
			if(result.next()) {
				return true;
			}
			
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			connectionManager.close(connection, statement, result);
		}
		
		return false;
	}	

}
