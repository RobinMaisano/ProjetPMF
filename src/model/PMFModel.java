package model;

import java.util.Observable;
import java.util.Observer;

import contract.IPMFModel;

public class PMFModel extends Observable implements IPMFModel{
	
	private float tempInterieur;
	private float tempExterieure;
	private float humInterieur;
	private float tempDsir;
	
	public PMFModel(float tempInterieur, float humInterieur, float tempDsir) {
		super();
		this.tempInterieur = tempInterieur;
		this.humInterieur = humInterieur;
		this.tempDsir = tempDsir;
	}
	@Override
	public float getTempInterieure() {
		return tempInterieur;
	}
	@Override
	public void setTempInterieure(float tempInterieur) {
		this.tempInterieur = tempInterieur;
	}
	@Override
	public float getHumInterieure() {
		return humInterieur;
	}
	@Override
	public void setHumInterieure(float humInterieur) {
		this.humInterieur = humInterieur;
	}
	@Override
	public float getTempDesire() {
		return tempDsir;
	}
	@Override
	public void setTempDesire(float tempDsir) {
		this.tempDsir = tempDsir;
	}
	@Override
	public void addObserver(Observer o){
		addObserver(o);
	}
	@Override
	public void hasBeenChanged() {
		setChanged();
	}
	@Override
	public void notifObservers(){
		this.notifyObservers();
	}
	@Override
	public float getTempExterieure() {
		return this.tempExterieure;
	}
	@Override
	public void setTempExterieure(float tempOut) {
		this.tempExterieure = tempOut;
	}
	
}
