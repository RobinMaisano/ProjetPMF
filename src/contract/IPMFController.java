package contract;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public interface IPMFController extends Runnable, Observer, ActionListener {

	public void actionPerformed(ActionEvent e);
	
	public void update(Observable o, Object arg);
	
}
