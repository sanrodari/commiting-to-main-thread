package com.example.handlerexample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.handlerexample.BinderService.LocalBinder;

public class MainActivity extends Activity {
	
	private BinderService binderService;
	
	private TextView textView;

    private ServiceConnection conn = new ServiceConnection() {
    	public void onServiceConnected(ComponentName className, IBinder binder) {
    		LocalBinder localBinder = (LocalBinder) binder;
    		binderService = localBinder.getService();
    	}
		public void onServiceDisconnected(ComponentName className) {}
	};

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView = (TextView) findViewById(R.id.textView1);
    }
	
	public void consumeAndUpdateTextView(View view) {
		
		Log.i("HandlerExample", "Id hilo principal " + Thread.currentThread().getId());
		
		binderService.consumeAndUpdateTextView(textView);
	}
    
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	Intent intent = new Intent(this, BinderService.class);
    	bindService(intent, conn, BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	
    	try {
			unbindService(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
