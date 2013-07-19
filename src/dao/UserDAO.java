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
import exception.GodotConflictException;
import exception.GodotUserNotFoundException;
import model.Login;
import model.User;
import model.bean.UserBean;

public interface UserDAO {
	
	public void save(User user) throws GodotConflictException;
	
	public void update(User user) throws GodotUserNotFoundException;
	
	public List<UserBean> findAll();
	
	public UserBean findByLogin(Login login);
	
	public UserBean findByUsername(String username);

}
