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

package controller;

import utils.mailer.Mailer;
import utils.mailer.WebMailer;
import model.Subscriber;
import model.bean.SubscriberBean;
import dao.SubscriberDAO;
import dao.SubscriberJdbcDAO;
import exception.GodotSyntaxException;

public class ControllerSubscription {
	
	private static ControllerSubscription controllerSubscription;
	
	private SubscriberDAO subscriberDAO;
	
	private Mailer mailer;

	private ControllerSubscription() {
		this.subscriberDAO = SubscriberJdbcDAO.getInstance();
		this.mailer = WebMailer.getInstance();
	}
	
	public static synchronized ControllerSubscription getInstance() {
		if (controllerSubscription == null) {
			controllerSubscription = new ControllerSubscription();
		}
		
		return controllerSubscription;
	}

	public synchronized void insertSubscriber(SubscriberBean subscriberBean) throws GodotSyntaxException {
		Subscriber subscriber = new Subscriber().fromBean(subscriberBean);
		this.subscriberDAO.save(subscriber);
		this.notifySubscriberInsertion(subscriber);
	}
	
	private void notifySubscriberInsertion(Subscriber subscriber) {
		String mail = subscriber.getMail();
		mailer.sendMail(mail, "Godot", "Hi " + subscriber.getName() + "!" +
				"\n\nThank you for your subscription to GODOT!!\n" +
				"\n\nCheers by Godot Team.");
	}
		
}
