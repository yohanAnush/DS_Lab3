//package temperatureMonitor;
/* * rmic compiler gives an error if a package name is specified. */

interface TemperatureListener extends java.rmi.Remote {
	
	public void temperatureChanged(double temperature) throws java.rmi.RemoteException;
}
