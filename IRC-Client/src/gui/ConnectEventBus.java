package gui;

import com.google.common.eventbus.EventBus;

public class ConnectEventBus {

	private static final ConnectEventBus instance = new ConnectEventBus();
	private ConnectEventBus() {
		eventBus = new EventBus();
	}
	public static ConnectEventBus getInstance() {
		return instance;
	}
	
	private EventBus eventBus;
	
	public void postConnectEvent(String serverName) {
		eventBus.post(serverName);
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
}
