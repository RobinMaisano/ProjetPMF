package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import contract.IPMFController;
import contract.IPMFModel;
import contract.IPMFView;
import model.PMFModel;
import view.PMFView;

public class PMFController implements Runnable, Observer, ActionListener, IPMFController {

	// private PMFModel frigo;
	// private PMFView view;

	private IPMFModel model;
	private IPMFView view;

	// public PMFController(PMFModel frigo) {
	// frigo.addObserver(this);
	// }

	public PMFController(IPMFModel model) {
		this.model = model;
	}

	public IPMFModel getModel() {
		return this.model;
	}

	public IPMFView getView() {
		return this.view;
	}

	public void run() {

		this.view = new PMFView();
		this.view.getButPlus().addActionListener(this);
		this.view.setVisible(true);

	}

	// public void run(){
	// if (!SwingUtilities.isEventDispatchThread()){
	// System.err.println("Erreur, le lancement du controller");
	// }
	//
	// this.view = new PMFView();
	// this.view.getButPlus().addActionListener(this);
	// this.view.getButMoins().addActionListener(this);
	// this.getView().setVisible(true);
	// }

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.getName().equals("butPlus")) {
			this.model.setTempDesire(this.model.getTempDesire() + 1);
		} else if (button.getName().equals("butMoins")) {
			this.model.setTempDesire(this.model.getTempDesire() - 1);
		} else {
			System.out.println("Une erreur est apparu");
			return;
		}
		this.model.hasBeenChanged();
		this.model.notifyObservers();

	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof PMFModel) {

			PMFModel frigo = (PMFModel) o;
			String tempInt = String.format("Temp : %.2f °C", frigo.getTempInterieur());
			String tempDsr = String.format("Temp désirée : %.2f °C", frigo.getTempDsir());
			String humInt = String.format("Hum : %.2f %%", frigo.getHumInterieur());
			view.getLblTempc().setText(tempInt);
			view.getLblTempDsire().setText(tempDsr);
			view.getLblHum().setText(humInt);

			// if (frigo.getTempInterieur() <= frigo.getTempDsir()) {
			//
			// } else {
			//
			// }

		}
	}
}
