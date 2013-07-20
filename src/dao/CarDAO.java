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
