package com.clt.ledmanager.app.Fragment.observable;

import com.clt.ledmanager.app.AdvancedActivity;

import java.util.Observable;

public class TerminateObservable extends Observable {

	public static TerminateObservable getInstance(){
		return terminateObservable;
	}

	private TerminateObservable(){

	}

	private static TerminateObservable terminateObservable = new TerminateObservable();

	public void dealHandlerMessage(AdvancedActivity.MessageWrapper msg){
		setChanged();
		notifyObservers(msg);
	}
}
