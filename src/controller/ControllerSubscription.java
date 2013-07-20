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
