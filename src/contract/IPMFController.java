package contract;

import java.awt.event.ActionEvent;
import java.util.Observable;

public interface IPMFController {

	public void actionPerformed(ActionEvent e);
	
	public void update(Observable o, Object arg);
	
}
