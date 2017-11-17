package Model;

import java.util.Observable;

public class Frigo extends Observable {
	
	private float tempInterieur;
	private float humInterieur;
	private float tempDsir;
	
	
	public float getTempInterieur() {
		return tempInterieur;
	}
	public void setTempInterieur(float tempInterieur) {
		this.tempInterieur = tempInterieur;
	}
	public float getHumInterieur() {
		return humInterieur;
	}
	public void setHumInterieur(float humInterieur) {
		this.humInterieur = humInterieur;
	}
	public float getTempDsir() {
		return tempDsir;
	}
	public void setTempDsir(float tempDsir) {
		this.tempDsir = tempDsir;
	}
	
	public Frigo(float tempInterieur, float humInterieur, float tempDsir) {
		super();
		this.tempInterieur = tempInterieur;
		this.humInterieur = humInterieur;
		this.tempDsir = tempDsir;
	}
	
	public void hasBeenChanged() {
		setChanged();
	}
	
	
}
