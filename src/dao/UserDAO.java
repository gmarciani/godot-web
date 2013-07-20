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
