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

import java.util.List;
import exception.GodotCarNotFoundException;
import exception.GodotConflictException;
import exception.GodotPermissionException;
import exception.GodotUserNotFoundException;
import model.Car;
import model.bean.CarBean;

public interface CarDAO {
	
	public void save(Car car) throws GodotConflictException, GodotPermissionException;
	
	public void delete(String carName);
	
	public void addCarCoOwner(String carName, String username) throws GodotUserNotFoundException;
	
	public void deleteCarCoOwner(String carName, String username) throws GodotPermissionException;
	
	public void updateCarDriver(String carName, String username) throws GodotPermissionException;
		
	public void pushMessage(String carName) throws GodotCarNotFoundException;
	
	public void popMessage(String carName) throws GodotCarNotFoundException;
	
	public List<CarBean> findAll();
	
	public List<CarBean> findByOwner(String username);
	
	public CarBean findByName(String carName);
	
	public CarBean findByDriver(String username);
	
	public boolean isOwner(String carName, String username);
	
	public boolean isCoOwner(String carName, String username);

}
