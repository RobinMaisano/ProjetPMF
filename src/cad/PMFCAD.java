package cad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import contract.IPMFCAD;
import contract.IPMFModel;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class PMFCAD implements IPMFCAD {

	private IPMFModel model;
	private final int refreshTime = 2000;
	private boolean modelChanged = false;
	private int power = 0;
	private float tIn;
	private float tOut;
	private float hum;
	private String portName = "COM3";
	private String dataReceived;
	private String[] listStr;
	private SerialPort serialPort;
	private InputStream in;
	private OutputStream out;

	public PMFCAD(IPMFModel model) {
		this.model = model;

		// Connection
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if (portIdentifier.isCurrentlyOwned()) {
				System.out.println("Error: Port is currently in use");
			} else {
				CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
				if (commPort instanceof SerialPort) {
					this.serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					this.in = serialPort.getInputStream();
					this.out = serialPort.getOutputStream();
				} else {
					System.out.println("Error: Only serial ports are handled.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void connect(String portName) throws Exception {
	 * CommPortIdentifier portIdentifier =
	 * CommPortIdentifier.getPortIdentifier(portName); if
	 * (portIdentifier.isCurrentlyOwned()) {
	 * System.out.println("Error: Port is currently in use"); } else { CommPort
	 * commPort = portIdentifier.open(this.getClass().getName(), 2000);
	 * 
	 * if (commPort instanceof SerialPort) { this.serialPort = (SerialPort)
	 * commPort; serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
	 * SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	 * 
	 * in = serialPort.getInputStream(); out = serialPort.getOutputStream();
	 * 
	 * // (new Thread(new SerialReader(in))).start(); // (new Thread(new
	 * SerialWriter(out))).start();
	 * 
	 * } else { System.out.
	 * println("Error: Only serial ports are handled by this example."); } } }
	 */


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(refreshTime);
				
				//On envoie sur le port série l'état que doit adopter le Peltier (0 ou 1)
				out.write(power);
				
				BufferedReader input = new BufferedReader(new InputStreamReader(in));// TODO
				// Lire ligne de characères

				// On crée un event listener pour voir si quelque chose se passe
				// sur le port série
				serialPort.addEventListener(new SerialPortEventListener() {

					@Override
					public void serialEvent(SerialPortEvent arg0) {
						// TODO Si il y a un event
						if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
							try {
								// TODO On attend tant que le port n'est pas
								// dispo (pour pas cut le message)
								while (!input.ready());

								// TODO On affiche ce qu'on a lu
								dataReceived = input.readLine();
								System.out.println("arg0 :" + dataReceived);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
				
				listStr = dataReceived.split(";");
				
				hum = Float.parseFloat(listStr[0]);
				tIn = Float.parseFloat(listStr[1]);
				tOut = Float.parseFloat(listStr[2]);
				
				if(this.hum != model.getHumInterieure()){
					model.setHumInterieure(hum);
					modelChanged = true;
				}
				if(this.tIn != model.getTempInterieure()){
					model.setTempInterieure(tIn);
					modelChanged = true;
				}
				if(this.tOut != model.getTempExterieure()){
					model.setTempExterieure(tOut);
					modelChanged = true;
				}
				if(modelChanged){
					model.hasBeenChanged();
					model.notifObservers();
					modelChanged = false;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO On abonne l'event listener au port série
			serialPort.notifyOnDataAvailable(true);
		}
	}
	
	@Override
	public void setPower(int power) {
		this.power = power;
	}
}