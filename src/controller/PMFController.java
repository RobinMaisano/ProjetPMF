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
	
	/**
	 * Controleur constructeur
	 * @param model Modele qui sera observe
	 * @param cad CAD qui sera lancee dans un autre Thread
	 */
	public PMFController(IPMFModel model, IPMFCAD cad) {
		this.model = model;
		this.cad = cad;
	}

	/**
	 * Le controleur Observe le modele
	 * 
	 * Instancie la vue S'abonne aux deux boutons Rend la vue visible
	 * 
	 * Lance la CAD dans un nouveau thread
	 * 
	 */
	public void run() {
		this.model.addObs(this);

		this.view = new PMFView();
		this.view.getButPlus().addActionListener(this);
		this.view.getButMoins().addActionListener(this);
		this.view.setVisi(true);

		Thread thread = new Thread(cad, "threadCAD");
		thread.start();

	}

	/**
	 * En cas d'action sur une des deux boutons : V�rifie de quel bouton il
	 * s'agit, Modifie le controleur en cons�quence Modifie la temp de consigne
	 * affich�e en cons�quence Recalcule la temp�rature de ros�e avec cette
	 * nouvelle temp�rature de consigne Modifie si besoin la valeur renvoy�e �
	 * l'arduino et au Peltier
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		System.out.println(button.getText());
		if (button.getText().equals("+")) {
			this.model.setTempDesire(checkTemp(this.model.getTempDesire() + 1));
			this.view.getLblTempDsire().setText(String.format("Temp d�sir�e : %.2f �C", this.model.getTempDesire()));
		} else if (button.getText().equals("-")) {
			this.model.setTempDesire(checkTemp(this.model.getTempDesire() - 1));
			this.view.getLblTempDsire().setText(String.format("Temp d�sir�e : %.2f �C", this.model.getTempDesire()));
		} else {
			System.out.println("Une erreur est apparue");
			return;
		}

		updateRosee(this.model.getHumInterieure(), this.model.getTempDesire());

		updatePower();

	}

	/**
	 * Lorsqu'une notification venant du modele apparait
	 * V�rifie qu'il sagit bien du modele observ�
	 * R�cup�re les valeurs du mod�le et met � jour la vue en cons�quence
	 * Recalcule la temp�rature de ros�e avec la nouvelle humidit�
	 * Modifie si besoin la valeur renvoy�e � l'arduino et au Peltier
	 * Envoi au graph les nouvelles valeurs de temp int�rieure et ext�rieure
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof PMFModel) {

			String tempInt = String.format("Temp in : %.2f �C", this.model.getTempInterieure());
			String tempOut = String.format("Temp out : %.2f �C", this.model.getTempExterieure());
			String tempDsr = String.format("Temp d�sir�e : %.2f �C", this.model.getTempDesire());
			String humInt = String.format("Hum : %.2f %%", this.model.getHumInterieure());

			this.view.getLblTempc().setText(tempInt);
			this.view.getLblTempOut().setText(tempOut);
			this.view.getLblTempDsire().setText(tempDsr);
			this.view.getLblHum().setText(humInt);

			updateRosee(this.model.getHumInterieure(), this.model.getTempDesire());
			updatePower();

			this.view.updateGraph(this.model.getTempInterieure(), this.model.getTempExterieure());

		}
	}

	/**
	 * Fonction calculant la temp�rature � laquelle de la condensation peut se
	 * former et mettant a jour la vue
	 * 
	 * @param hum
	 *            Humidite relative (en %)
	 * @param temp
	 *            Temperature a l'interieur du frigo
	 */
	private void updateRosee(float hum, float temp) {
		float hum100 = hum / 100;
		float temp09 = (float) (0.9 * temp);
		float temp01 = (float) (0.1 * temp);
		float rosee = (float) ((Math.pow((hum100), 0.125) * (112 + (temp09)) + (temp01) - 112));
		String pointRosee = String.format("Point de ros�e � : %.2f �C", rosee);
		this.view.getLblPointDeRose().setText(pointRosee);
	}

	/**
	 * Fonction v�rifiant que la temp�rature de consigne ne d�passe pas les
	 * seuils definis
	 * 
	 * @param consigne
	 *            Temperature de consigne desiree par l'utilisateur
	 * @return Temperature acceptee par l'application, temp consigne entre 5 et
	 *         20 et bornes sinon
	 */
	public float checkTemp(float consigne) {
		float mini = 5, maxi = 20;
		if (mini < consigne && consigne < maxi) {
			return consigne;
		} else if (mini >= consigne) {
			return mini;
		} else if (maxi <= consigne) {
			return maxi;
		} else
			return 18;
	}

	/**
	 * Fontion mettant a jour la valeur que doit adopter le peltier
	 */
	public void updatePower() {
		if (this.model.getTempDesire() < this.model.getTempInterieure() + 0.2) {
			this.cad.setPower(1);
			this.view.setPower(true);
		} else {
			this.cad.setPower(0);
			this.view.setPower(false);
		}
	}
}