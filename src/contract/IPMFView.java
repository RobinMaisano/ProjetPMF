package contract;

import javax.swing.JPanel;

public interface IPMFView {

	public JPanel getContentPane();
	
	public void setLblTempIn(String content);
	
	public void setLblHum(String content);
	
	public void setLblTempDesire(String content);
	
}