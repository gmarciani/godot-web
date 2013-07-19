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
