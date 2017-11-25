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
		this.humInterieur = 0;
		this.tempDsir = 0;
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
	public void addObs(Observer o){
		this.addObserver(o);
	}
	@Override
	public void hasBeenChanged() {
		this.setChanged();
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
