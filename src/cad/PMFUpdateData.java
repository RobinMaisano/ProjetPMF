package cad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class PMFUpdateData implements Runnable {

	InputStream in;
	OutputStream out;
	SerialPort serialPort;
	String dataReceived;

	public PMFUpdateData(SerialPort serialPort) throws IOException {
		this.serialPort = serialPort;
		in = serialPort.getInputStream();
		out = serialPort.getOutputStream();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			out.write("update".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader input = new BufferedReader(new InputStreamReader(in));// TODO
		// Lire ligne de characères

		// TODO On crée un event listener pour voir si quelque chose se
		// passe sur le port série
		try {
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
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO On abonne l'event listener au port série
		serialPort.notifyOnDataAvailable(true);

	}

}
