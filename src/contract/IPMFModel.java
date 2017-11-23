package contract;

import java.util.Observer;

public interface IPMFModel{

		public float getTempInterieure();
		
		public void setTempInterieure(float tempInt);
		
		public float getHumInterieure();
		
		public void setHumInterieure(float humInt);
	
		public float getTempExterieure();
		
		public void setTempExterieure(float tempOut);
		
		public float getTempDesire();
		
		public void setTempDesire(float tempDesire);
		
		public void hasBeenChanged();

		public void notifObservers();
		
		public void addObserver(Observer o);
}
