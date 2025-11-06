package es.grise.upm.profundizacion.subscriptionService;

import java.util.ArrayList;
import java.util.Collection;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.NullUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;

public class SubscriptionService {

	private Collection <User> subscribers;
	
	/*
	 * Constructor
	 */
	public SubscriptionService() {
		
		this.subscribers = new ArrayList <User> ();
		
	}

	/*
	 * Method to code
	 */
	public void addSubscriber(User user) throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		
		if(user == null)
			throw new NullUserException();
		
		if(subscribers.contains(user))
			throw new ExistingUserException();
		
		if(user.getDelivery() == Delivery.LOCAL)
			if(user.getEmail() != null)
				throw new LocalUserDoesNotHaveNullEmailException();

		subscribers.add(user);
	
	}
	
	/*
	 * Other setters & getters
	 */
	public Collection <User> getSubscribers() {
		
		return subscribers;
		
	}

}
