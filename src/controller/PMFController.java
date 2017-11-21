package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import model.PMFModel;
import view.PMFView;

public class PMFController implements Runnable, Observer, ActionListener {
		
	private PMFModel frigo;
	private PMFView view;
	
	public PMFController(PMFModel frigo) {
		frigo.addObserver(this);
	}
	
	public PMFModel getModel(){
		return this.frigo;
	}
	public PMFView getView(){
		return this.view;
	}
	
	public void run(){
		if (!SwingUtilities.isEventDispatchThread()){
			System.err.println("Erreur, le lancement du controller");
		}
		
		this.view = new PMFView();
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
		if(o instanceof PMFModel) {
			PMFModel frigo = (PMFModel) o;
			
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
