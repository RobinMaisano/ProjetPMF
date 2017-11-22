package cad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.TooManyListenersException;

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
	private int power = 0;
	private String portName = "COM3";
	private String dataReceived;
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
					System.out.println("Error: Only serial ports are handled by this example.");
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

	/** */
	public static class SerialReader implements Runnable {
		InputStream in;

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int len = -1;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					System.out.print(new String(buffer, 0, len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** */
	public static class SerialWriter implements Runnable {
		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;
		}

		public void run() {
			try {
				int c = 0;
				while ((c = System.in.read()) > -1) {
					this.out.write(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			// TODO (new PMFCAD()).connect("COM3");
			Thread.sleep(2000);
			String test = "R";

			// out.write(test.getBytes());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public void update() throws TooManyListenersException {
		// TODO Auto-generated method stub
		try {
			out.write("update".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader input = new BufferedReader(new InputStreamReader(in));// TODO
		// Lire
		// ligne
		// de
		// characères

		// TODO On crée un event listener pour voir si quelque chose se
		// passe sur le port série
		serialPort.addEventListener(new SerialPortEventListener() {

			@Override
			public void serialEvent(SerialPortEvent arg0) {
				// TODO Si il y a un event
				if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
					try {
						// TODO On attend tant que le port n'est pas
						// dispo (pour pas cut le message)
						while (!input.ready())
							;
						// TODO On affiche ce qu'on a lu
						String dataReceived = input.readLine();
						System.out.println("arg0 :" + dataReceived);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

			}
		});
		// TODO On abonne l'event listener au port série
		serialPort.notifyOnDataAvailable(true);

	}

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
				
				if(dataReceived.length() != 3){
					System.out.println("Données reçues invalides");
				}else{
					
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO On abonne l'event listener au port série
			serialPort.notifyOnDataAvailable(true);

		}
	}
}