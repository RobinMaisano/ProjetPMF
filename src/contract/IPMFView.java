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
	
	public JLabel getLblPointDeRose();
	
	/**
	 * Update chart on the View Frame
	 * @param tempInterieure float temperature
	 * @param tempExterieure float temperature
	 */
	public void updateGraph(float tempInterieure, float tempExterieure);
	
	/**
	 * Set On or Off Power icon
	 * @param b
	 */
	public void setPower(boolean b);

	/**
	 * Set Visible on the View Frame
	 * @param b
	 */
	void setVisi(boolean b);
	
}