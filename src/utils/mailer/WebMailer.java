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

package utils.mailer;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WebMailer extends AbstractMailer {
	
	private static WebMailer singletonWebMailer = null;

	protected WebMailer() {
		try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            session = (Session) envContext.lookup("mail/Session");             
        } catch (NamingException e) {
            e.printStackTrace();
        }
	}
	
	public static synchronized WebMailer getInstance() {
		if (singletonWebMailer == null) {
			singletonWebMailer = new WebMailer();
		}
		
		return singletonWebMailer;
	}

}
