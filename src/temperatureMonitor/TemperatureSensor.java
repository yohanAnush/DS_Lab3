//package temperatureMonitor;
/* * rmic compiler gives an error if a package name is specified. */

interface TemperatureSensor extends java.rmi.Remote {
	public double getTemperature() throws java.rmi.RemoteException;
	public void addTemperatureListener (TemperatureListener listener ) throws java.rmi.RemoteException;
	public void removeTemperatureListener (TemperatureListener listener ) throws java.rmi.RemoteException;
}
