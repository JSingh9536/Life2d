package entity;

import java.io.Serializable;
import java.net.InetAddress;

public class ClientToServer implements Serializable{
	private int x, y;
	private int PIDtoServer;
	private InetAddress ip;
	
	public ClientToServer(int newPID, int defX, int defY) {
		PIDtoServer = newPID;
		x = defX;
		y = defY;
	}
	public ClientToServer(int defX, int defY) {//used for main player to create object to send to server; won't know it's PID
		x = defX;
		y = defY;
	}
	public ClientToServer(int newPID, int defX, int defY, InetAddress IP) {//for UDP connection
		PIDtoServer = newPID;
		x = defX;
		y = defY;
		ip = IP;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int newX) {
		x = newX;
	}
	public int getY() {
		return y;
	}
	public void setY(int newY) {
		y = newY;
	}
	public int getPID() {
		return PIDtoServer;
	}
	public InetAddress getIP() {
		return ip;
	}
	public void setIP(InetAddress newIP) {
		ip = newIP;
	}
}
