package cad;

import contract.IPMFCAD;
import contract.IPMFModel;

public class FakePMFCAD implements IPMFCAD {

	private IPMFModel model;
	private int power;
	/**
	 * Constructeur Fake CAD
	 * @param model Modele a modifier
	 */
	public FakePMFCAD(IPMFModel model) {
		this.model = model;
	}
	/**
	 * Laisse le ctrl tranquille pendant 2 secondes
	 * Genère 3 nouvelles donnees
	 * Fait vérifier le modèle quand a la nouveauté de ces donnees
	 * Retourne la puissance devant etre delivree au Peltier
	 */
	@Override
	public void run() {
		while (Boolean.TRUE) {

			try {
				Thread.sleep(2000);

				float hum = (float) Math.random() * 100;
				float tIn = (float) Math.random() * 20;
				float tOut = (float) Math.random() * 30;
				
				this.model.testData(hum, tIn, tOut);
				
				System.out.println("Power Returned : " + this.power);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	/**
	 * Definie la valeur de la puissance devant etre envoye au Peltier
	 */
	@Override
	public void setPower(int power) {
		this.power = power;
	}

}
