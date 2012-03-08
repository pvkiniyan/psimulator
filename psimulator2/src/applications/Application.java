/*
 * created 28.10.2011
 */
package applications;

import dataStructures.IpPacket;
import device.Device;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import logging.Loggable;
import logging.Logger;
import logging.LoggingCategory;
import networkModule.L4.TransportLayer;
import networkModule.TcpIpNetMod;
import utils.SmartRunnable;
import utils.WorkerThread;

/**
 * Represents network application which listens on specified port.
 *
 * @author Stanislav Rehak <rehaksta@fit.cvut.cz>
 */
public abstract class Application implements SmartRunnable, Loggable {
	public final int PID;
	public final String name;
	protected final Device device;
	private WorkerThread worker;
	protected Integer port = null; // TODO: doresit, zda sem nedat seznam portu
	protected final TransportLayer transportLayer;

	protected final List<IpPacket> buffer = Collections.synchronizedList(new LinkedList<IpPacket>());


	public Application(String name, Device device) {
		this.name = name;
		this.device = device;

		this.PID = device.getFreePID();
		if (device.getNetworkModule().isStandardTcpIpNetMod()) {
			this.transportLayer =  ((TcpIpNetMod) device.getNetworkModule()).transportLayer;
		} else {
			Logger.log(this, Logger.ERROR, LoggingCategory.GENERIC_APPLICATION, "Vytvari se sitova aplikace pro device, ktery nema TcpIpNetMod!", null);
			this.transportLayer = null;
		}
	}

	/**
	 * Starts aplication by turning on listening on port.
	 */
	public final void run() {
		device.registerApplication(this);
		this.port = transportLayer.registerApplication(this, port);
		this.worker = new WorkerThread(this);
		atStart();
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Integer getPort() {
		return port;
	}

	/**
	 * Exit the application. <br />
	 * Don't call this method from commands! Call device.exitApplication() instead.
	 */
	public void exit() {
		atExit();
		transportLayer.unregisterApplication(port);
		device.unregisterApplication(this);
		worker.die();
	}

	/**
	 * Exit the application without calling atExit(). <br />
	 * Don't call this method from commands! Call device.killApplication() instead.
	 */
	public void kill() {
		transportLayer.unregisterApplication(port);
		device.unregisterApplication(this);
		worker.die();
	}

	/**
	 * Implement this function to run some commands right before application start. <br />
	 * (treba pro nejake kontroly atd.)
	 */
	public abstract void atStart();

	/**
	 * Implement this function to run some commands right before application exit. <br />
	 * (treba pro nejake vypisy pri ukonceni aplikace)
	 */
	public abstract void atExit();

	public String getName() {
		return name;
	}

	public int getPID() {
		return PID;
	}

	public void receivePacket(IpPacket packet) {
		buffer.add(packet);
		worker.wake();
	}
}