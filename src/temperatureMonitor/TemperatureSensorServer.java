//package temperatureMonitor;
/* * rmic compiler gives an error if a package name is specified. */

import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class TemperatureSensorServer extends UnicastRemoteObject implements TemperatureSensor, Runnable {

	private volatile double temp;
	private ArrayList<TemperatureListener> list = new ArrayList<TemperatureListener>();

	public TemperatureSensorServer() throws java.rmi.RemoteException {
		temp = 98.0;
	}

	
	// returns the current temperature.
	public double getTemperature() throws java.rmi.RemoteException {
		return temp;
	}

	
	// A monitor that keeps track of temperature can be added to the listening list.
	// those monitors can be notified if temperature changes(via another method).
	public void addTemperatureListener(TemperatureListener listener) throws java.rmi.RemoteException {
		System.out.println("adding listener -" + listener);
		list.add(listener);
	}

	// Remove a monitor from the listening list so it is no longer, 
	// notified of any temperature change.
	public void removeTemperatureListener(TemperatureListener listener)
			throws java.rmi.RemoteException {
		System.out.println("removing listener -" + listener);
		list.remove(listener);
	}

	public void run() {
		Random r = new Random();
		for (;;) {
			try {
				// Sleep for a random amount of time
				int duration = r.nextInt() % 10000 + 200;
				// Check to see if negative, if so, reverse
				if (duration < 0) {
					duration = duration * -1;
					Thread.sleep(duration);
				}
			} catch (InterruptedException ie) {
			}

			// Get a number, to see if temp goes up or down
			int num = r.nextInt();
			if (num < 0) {
				temp += 0.5;
			} else {
				temp -= 0.5;
			}

			// Notify registered listeners
			notifyListeners();
		}
	}

	
	// Rely the changed temperature to all the listening monitors.
	private void notifyListeners() {
		// TODO: Notify every listener in the registered list if there is a change in the temperature
		for (int i = 0; i < list.size(); i++) {
			
			try {
				list.get(i).temperatureChanged(getTemperature());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		System.out.println("Loading temperature service");

		try {
			TemperatureSensorServer sensor = new TemperatureSensorServer();
			String registry = "localhost";

			String registration = "rmi://" + registry + "/TemperatureSensor";

			Naming.rebind(registration, sensor);

			Thread thread = new Thread(sensor);
			thread.start();
		} catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		} catch (Exception e) {
			System.err.println("Error - " + e);
		}

	}

}