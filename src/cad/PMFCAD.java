package cad;

import java.io.InputStream;
import java.io.OutputStream;

import contract.IPMFCAD;
import contract.IPMFModel;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class PMFCAD implements IPMFCAD {

	private IPMFModel model;
	private final int longWaitTime = 2000;
	private final int smallWaitTime = 1000;
	private int power = 0;
	private float tIn;
	private float tOut;
	private float hum;
	private String portName = "COM3";
	private InputStream in;
	private OutputStream out;

	public PMFCAD(IPMFModel model) throws Exception {
		this.model = model;
		this.initPort();
	}

	private void initPort() throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Port en cours d'utilisation ..");
		} else {
			this.connect(portIdentifier);
		}
	}

	private void connect(CommPortIdentifier portIdentifier) throws Exception {
		CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
		if (commPort instanceof SerialPort) {
			SerialPort serialPort = (SerialPort) commPort;
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			this.in = serialPort.getInputStream();
			this.out = serialPort.getOutputStream();
		}
	}

	private void updateData(InputStream in) {
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			while ((len = in.read(buffer)) > -1) {
				String data = new String(buffer, 0, len);
				String[] dataElements = data.split(";");

				Thread.sleep(longWaitTime);
				if (dataElements.length == 3) {
					this.hum = Float.parseFloat(dataElements[0]);
					Thread.sleep(smallWaitTime);
					this.tIn = Float.parseFloat(dataElements[1]);
					Thread.sleep(smallWaitTime);
					this.tOut = Float.parseFloat(dataElements[2]);
					Thread.sleep(smallWaitTime);

					this.model.testData(this.hum, this.tIn, this.tOut);
					this.action(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void displayPower(OutputStream out) {
		try {
			out.write(this.power);
			this.action(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void action(boolean b) {
		if (b == false) {
			this.updateData(this.in);
		} else {
			this.displayPower(this.out);
		}
	}

	@Override
	public void run() {
		this.updateData(in);
	}

	@Override
	public void setPower(int power) {
		this.power = power;
	}
}