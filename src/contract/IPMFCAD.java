package contract;

import java.util.TooManyListenersException;

public interface IPMFCAD extends Runnable{

	public void setPower(int power);
	
	public void update() throws TooManyListenersException ;
	
}