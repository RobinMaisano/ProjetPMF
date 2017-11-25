package controller;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.JButton;

import contract.IPMFCAD;
import contract.IPMFController;
import contract.IPMFModel;
import contract.IPMFView;
import model.PMFModel;
import view.PMFView;

public class PMFController implements IPMFController {


	private IPMFModel model;
	private IPMFView view;
	private IPMFCAD cad;

	public PMFController(IPMFModel model, IPMFCAD cad) {
		this.model = model;
		this.cad = cad;
	}

	public void run() {
		this.model.addObs(this);

		this.view = new PMFView();
		
		this.view.getButPlus().addActionListener(this);
		this.view.getButMoins().addActionListener(this);

		view.setVisi(true);
		Thread thread = new Thread(cad, "threadCAD");
		thread.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		System.out.println(button.getText());
		if (button.getText().equals("+")) {
			this.model.setTempDesire(checkTemp(this.model.getTempDesire() + 1));
			this.view.getLblTempDsire().setText(String.format("Temp désirée : %.2f °C", this.model.getTempDesire()));
		} else if (button.getText().equals("-")) {
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
			
			String pointRosee = String.format("Point de rosée à : %.2f °C", calculRosee(this.model.getHumInterieure(), this.model.getTempDesire()));
			
			this.view.getLblTempc().setText(tempInt);
			this.view.getLblTempOut().setText(tempOut);
			this.view.getLblTempDsire().setText(tempDsr);
			this.view.getLblHum().setText(humInt);
			this.view.getLblPointDeRose().setText(pointRosee);

			updatePower();
			
			//this.view.updateGraph(this.model.getTempInterieure(), this.model.getTempExterieure());

		}
	}

	private float calculRosee(float hum, float temp) {
		float rosee;
		
		rosee = pow(); // (hum/100)(1/8);
		
		return rosee;
		
		
	}

	public float checkTemp(float consigne) {
		float mini = 5, maxi = 20;
		if (mini < consigne && consigne < maxi) {return consigne;}
		else if (mini >= consigne) {return mini;}
		else if (maxi <= consigne) {return maxi;} 
		else return 18;
	}
	
	public void updatePower(){
		if(this.model.getTempDesire() < this.model.getTempInterieure()+0.2){
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
	
}
