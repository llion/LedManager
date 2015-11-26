package com.clt.ledmanager.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.app.Fragment.observable.TerminateObservable;

/**
 * Activity基类
 * 
 */
public abstract class BaseObservableActivity extends AppCompatActivity
{

	protected TerminateObservable terminateObservable;

	public TerminateObservable getTerminateObservable() {
		return terminateObservable;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Application.getInstance().terminateObservable = terminateObservable = TerminateObservable.getInstance();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

	}

}
