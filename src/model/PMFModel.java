package model;

import java.util.Observable;
import java.util.Observer;

import contract.IPMFModel;

public class PMFModel extends Observable implements IPMFModel{
	
	private float tempInterieur;
	private float humInterieur;
	private float tempDsir;
	
	public PMFModel(float tempInterieur, float humInterieur, float tempDsir) {
		super();
		this.tempInterieur = tempInterieur;
		this.humInterieur = humInterieur;
		this.tempDsir = tempDsir;
	}
	
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
		
	public void addObserver(Observer o){
		addObserver(o);
	}
	
	public void hasBeenChanged() {
		setChanged();
	}
	
	public void notifObservers(){
		this.notifyObservers();
	}
	
	@Override
	public float getTempInterieure() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setTempInterieure(float tempInt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public float getHumInterieure() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setHumInterieure(float humInt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public float getTempDesire() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setTempDesire(float tempDesire) {
		// TODO Auto-generated method stub
		
	}	
}
