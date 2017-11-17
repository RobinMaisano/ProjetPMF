package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import Model.Frigo;
import View.window;

public class windowcontroller implements Runnable, Observer, ActionListener {
		
	private Frigo frigo;
	private window view;
	
	public windowcontroller(Frigo frigo) {
		frigo.addObserver(this);
	}
	
	public Frigo getModel(){
		return this.frigo;
	}
	public window getView(){
		return this.view;
	}
	
	public void run(){
		if (!SwingUtilities.isEventDispatchThread()){
			System.err.println("Erreur, le lancement du controller");
		}
		
		this.view = new window();
		this.view.getButPlus().addActionListener(this);
		this.view.getButMoins().addActionListener(this);
		this.getView().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button.getName().equals("butPlus")) {
			this.frigo.setTempDsir(this.frigo.getTempDsir() + 1);
		} else if(button.getName().equals("butMoins")) {
			this.frigo.setTempDsir(this.frigo.getTempDsir() - 1);
		} else {
			System.out.println("Une erreur est apparu");
			return;
		}
		this.frigo.hasBeenChanged();
		this.frigo.notifyObservers();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Frigo) {
			Frigo frigo = (Frigo) o;
			
			String tempInt = String.format("Temp : %.2f °C", frigo.getTempInterieur());
			String humInt = String.format("Hum : %.2f %%", frigo.getHumInterieur());
			String tempDsr = String.format("Temp désirée : %.2f %%", frigo.getTempDsir());
			view.getLblTempc().setText(tempInt);
			view.getLblHum().setText(humInt);
			view.getLblTempDsire().setText(tempDsr);
			
			if (frigo.getTempInterieur() <= frigo.getTempDsir()) {
				
			} else {
				
			}
			
		}
		
	}
	
	
	
}
