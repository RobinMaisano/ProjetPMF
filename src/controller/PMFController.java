package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import contract.IPMFCAD;
import contract.IPMFController;
import contract.IPMFModel;
import contract.IPMFView;
import model.PMFModel;
import view.PMFView;

public class PMFController implements Observer, ActionListener, IPMFController {

	// private PMFModel frigo;
	// private PMFView view;

	private IPMFModel model;
	private IPMFView view;
	private IPMFCAD cad;

	public PMFController(IPMFModel model, IPMFCAD cad) {
		this.model = model;
		this.cad = cad;
	}

	// public void start() {
	// // thread = new Thread(this, "ThreadCtrl");
	// // thread.start();
	// }

	public void run() {
		this.model.addObserver(this);

		this.view = new PMFView();
		this.view.getButPlus().addActionListener(this);
		this.view.getButMoins().addActionListener(this);
		this.view.setVisible(true);

		Thread thread = new Thread(cad, "threadCAD");
		thread.start();
		
		// Thread thread = new Thread(new PMFCAD(model), "threadCAD");
		// thread.start();

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
			this.model.setTempDesire(checkTemp(this.model.getTempDesire() + 1));
			this.view.getLblTempDsire().setText(Float.toString(this.model.getTempInterieure()));
		} else if (button.getName().equals("butMoins")) {
			this.model.setTempDesire(checkTemp(this.model.getTempDesire() - 1));
			this.view.getLblTempDsire().setText(Float.toString(this.model.getTempInterieure()));
		} else {
			System.out.println("Une erreur est apparu");
			return;
		}
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

	public IPMFModel getModel() {
		return this.model;
	}

	public IPMFView getView() {
		return this.view;
	}

	private float checkTemp(float consigne) {
		float mini = 5, maxi = 20;
		if (mini < consigne && consigne < maxi) {return consigne;}
		else if (mini > consigne) {return mini;}
		else if (maxi < consigne) {return maxi;} 
		else return 18;

	}
}
