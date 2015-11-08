package com.clt.ledmanager.app.Fragment.observable;

import com.clt.ledmanager.app.AdvancedActivity;

import java.util.Observable;

public class TerminateObservable extends Observable {

	public void dealHandlerMessage(AdvancedActivity.MessageWrapper msg){
		setChanged();
		notifyObservers(msg);
	}
}
