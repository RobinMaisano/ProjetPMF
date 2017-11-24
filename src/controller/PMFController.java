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


	private IPMFModel model;
	private IPMFView view;
	private IPMFCAD cad;

	public PMFController(IPMFModel model, IPMFCAD cad) {
		this.model = model;
		this.cad = cad;
	}

	public void run() {
		this.model.addObserver(this);

		this.view = new PMFView();
		this.view.getButPlus().addActionListener(this);
		this.view.getButMoins().addActionListener(this);
		this.view.setVisible(true);

		Thread thread = new Thread(cad, "threadCAD");
		thread.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.getName().equals("butPlus")) {
			this.model.setTempDesire(checkTemp(this.model.getTempDesire() + 1));
			this.view.getLblTempDsire().setText(String.format("Temp désirée : %.2f °C", this.model.getTempDesire()));
		} else if (button.getName().equals("butMoins")) {
			this.model.setTempDesire(checkTemp(this.model.getTempDesire() - 1));
			this.view.getLblTempDsire().setText(String.format("Temp désirée : %.2f °C", this.model.getTempDesire()));
		} else {
			System.out.println("Une erreur est apparu");
			return;
		}
		
	//	updateRosee();
		
		updatePower();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof PMFModel) {

			String tempInt = String.format("Temp in : %.2f °C", this.model.getTempInterieure());
			String tempOut = String.format("Temp out : %.2f °C", this.model.getTempExterieure());
			String tempDsr = String.format("Temp désirée : %.2f °C", this.model.getTempDesire());
			String humInt = String.format("Hum : %.2f %%", this.model.getHumInterieure());
			view.getLblTempc().setText(tempInt);
			view.getLblTempOut().setText(tempOut);
			view.getLblTempDsire().setText(tempDsr);
			view.getLblHum().setText(humInt);

			updatePower();
			
			this.view.updateGraph(this.model.getTempInterieure(), this.model.getTempExterieure());

		}
	}

	public float checkTemp(float consigne) {
		float mini = 5, maxi = 20;
		if (mini < consigne && consigne < maxi) {return consigne;}
		else if (mini > consigne) {return mini;}
		else if (maxi < consigne) {return maxi;} 
		else return 18;
	}
	
	public void updatePower(){
		if(this.model.getTempDesire() > this.model.getTempInterieure()+0.2){
			this.cad.setPower(1);
		}else{
			this.cad.setPower(0);
		}
	}
	
//	private void updateRosee() {
//		float tdesire = this.model.getTempDesire();
//		float humid = this.model.getHumInterieure();
//		float firstpart = 17.27*tdesire;
//		float tRosee = (237,7 * ((17.27*tdesire)/(237.7+tdesire)+ log(humid)))/(17.27-((17.27*tdesire)/(237.7+tdesire)+log(humid)));
//	}
	
	public IPMFModel getModel() {
		return this.model;
	}

	public IPMFView getView() {
		return this.view;
	}
}
