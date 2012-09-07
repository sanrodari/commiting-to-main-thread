package com.example.handlerexample;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

public class BinderService extends Service {
	
	private BinderService instance = this;
	
	public class LocalBinder extends Binder {
		public BinderService getService() {
			return instance;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new LocalBinder();
	}
	
	//---------------------------
	//----------Servicios--------
	
	public void consumeAndUpdateTextView(final TextView textView) {
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				
				Log.i("HandlerExample", "Id hilo consumidor " + Thread.currentThread().getId());
				
				// Supongamos que aca se hace el consumo del WS.
				// Se bloquea el hilo por dos segundos.
				long endTime = System.currentTimeMillis() + 1 * 2000;
				while (System.currentTimeMillis() < endTime) {
					synchronized (this) {
						try {
							wait(endTime - System.currentTimeMillis());
						} catch (Exception e) {
						}
					}
				}
				
				final String retrievedInfo = "" + new Random().nextInt(100);
				
				// Cuando se termina la acción se realiza el cambio en la
				// interfaz.
				textView.post(new Runnable() {
					public void run() {
						textView.setText("Después de esperar 2 segundos se " +
								"cambia por la info consultada: " + retrievedInfo);
					}
				});
			}
		});
		
		thread.start();
	}

}
