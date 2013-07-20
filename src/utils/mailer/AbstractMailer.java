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

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class AbstractMailer implements Mailer {	
	
	protected Session session;

	protected AbstractMailer() {}
	
	@Override
	public void sendMail(String dest, String obj, String msg) {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", "mail.godot.mobi");
		props.put("mail.smtp.port", "26");
 
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Mailer.USERNAME, Mailer.PASSWORD);
			}
		  });
 
		try { 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Mailer.MAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));
			message.setSubject(obj);
			message.setText(msg); 
			Transport.send(message); 
		} catch (MessagingException exc) {
			throw new RuntimeException(exc);
		}
	}

}
