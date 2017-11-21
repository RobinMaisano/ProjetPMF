package contract;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public interface IPMFView {

	public JPanel getContentPane();
	
//	public void setLblTempIn(String content);
//	
//	public void setLblHum(String content);
//	
//	public void setLblTempDesire(String content);

	public JButton getButPlus();

	public JLabel getLblTempc();

	public JLabel getLblHum();

	public JLabel getLblTempDsire();
	
}