package contract;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public interface IPMFView {

	public JPanel getContentPane();
	

	public JButton getButPlus();
	
	public JButton getButMoins();

	public JLabel getLblTempc();

	public JLabel getLblHum();
	
	public JLabel getLblTempOut();

	public JLabel getLblTempDsire();
	
	/**
	 * Set Visible on the View Frame
	 * @param b
	 */
	public void setVisible(boolean b);
	
	/**
	 * Update chart on the View Frame
	 * @param tempInterieure float temperature
	 * @param tempExterieure float temperature
	 */
	public void updateGraph(float tempInterieure, float tempExterieure);
	
	/**
	 * Set On Warning message
	 * @param b
	 */
	public void setWarn(boolean b);
	
}