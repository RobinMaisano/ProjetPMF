package contract;

public interface IPMFController {

	public void setModel(IPMFModel model);
	
	public void setView(IPMFView view);
	
	public void run();
	
	public void actionPerformed();
	
	public void update();
	
}
