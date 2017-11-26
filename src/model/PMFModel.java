package model;

import java.util.Observable;
import java.util.Observer;

import contract.IPMFModel;

public class PMFModel extends Observable implements IPMFModel{
	
	private float tempInterieur;
	private float tempExterieure;
	private float humInterieur;
	private float tempDsir;
	private boolean modelChanged = false;
	
	public PMFModel() {
		super();
		this.tempInterieur = 0;
		this.tempExterieure = 0;
		this.humInterieur = 20;
		this.tempDsir = 15;
	}
	
	/**
	 * Retourne la valeur de la temperature interieure
	 */
	@Override
	public float getTempInterieure() {
		return tempInterieur;
	}
	/**
	 * Defini la valeur de la temperature interieure
	 */
	@Override
	public void setTempInterieure(float tempInterieur) {
		this.tempInterieur = tempInterieur;
	}
	/**
	 * Retourne la valeur de l'humidite interieure
	 */
	@Override
	public float getHumInterieure() {
		return humInterieur;
	}
	/**
	 * Defini la valeur de l'humidite interieure
	 */
	@Override
	public void setHumInterieure(float humInterieur) {
		this.humInterieur = humInterieur;
	}
	/**
	 * Retourne la valeur de la temperature de consigne
	 */
	@Override
	public float getTempDesire() {
		return tempDsir;
	}
	/**
	 * Defini la valeur de la temperature de consigne
	 */
	@Override
	public void setTempDesire(float tempDsir) {
		this.tempDsir = tempDsir;
	}
	/**
	 * Ajoute un observateur à la liste
	 */
	@Override
	public void addObs(Observer o){
		this.addObserver(o);
	}
	/**
	 * Definie le modele comme ayant change
	 */
	@Override
	public void hasBeenChanged() {
		this.setChanged();
	}
	/**
	 * Notifie les observateur de la liste
	 */
	@Override
	public void notifObservers(){
		this.notifyObservers();
	}
	/**
	 * Retourne la valeur de la temperature exterieure
	 */
	@Override
	public float getTempExterieure() {
		return this.tempExterieure;
	}
	/**
	 * Defini la valeur de la temperature exterieure
	 */
	@Override
	public void setTempExterieure(float tempOut) {
		this.tempExterieure = tempOut;
	}
	/**
	 * Verifie les valeur actuelles par rapport aux nouvelles
	 * Met a jour si les valeurs ont change
	 * Definie le modele comme ayant change
	 * Notifie les observateur
	 */
	@Override
	public void testData(float hum, float tIn, float tOut) {
		
		if(hum != this.getHumInterieure()){
			this.setHumInterieure(hum);
			this.modelChanged = true;
		}
		if(tIn != this.getTempInterieure()){
			this.setTempInterieure(tIn);
			this.modelChanged = true;
		}
		if(tOut != this.getTempExterieure()){
			this.setTempExterieure(tOut);
			this.modelChanged = true;
		}
		if(this.modelChanged){
			this.hasBeenChanged();
			this.notifObservers();
			this.modelChanged = false;
		}	
	}
	
}
