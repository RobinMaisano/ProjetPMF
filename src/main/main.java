package main;
import javax.swing.SwingUtilities;

import cad.FakePMFCAD;
import cad.PMFCAD;
import controller.PMFController;
import model.PMFModel;


public class main {

	public static void main(String[] args) throws Exception {
		
		PMFModel model = new PMFModel();
	//	PMFCAD cad = new PMFCAD(model);
		FakePMFCAD cad = new FakePMFCAD(model);
		PMFController ctrl = new PMFController(model, cad);
		SwingUtilities.invokeLater(ctrl);
	}

}
