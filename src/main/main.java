package main;
import javax.swing.SwingUtilities;

import cad.PMFCAD;
import controller.PMFController;
import model.PMFModel;


public class main {

	public static void main(String[] args) {
		
		PMFModel model = new PMFModel(0, 0, 0);
		PMFCAD cad = new PMFCAD(model);
		PMFController ctrl = new PMFController(model, cad);
		SwingUtilities.invokeLater(ctrl);

		
		
		/**
		 //* 
		 //* Le main cr�� le modele
		 //* la cad et lui donne le modele
	     //* Puis le ctrl et lui donne le modele et la cad
		 //* invokelater lance le 'run' du ctrl
		 * 
		 //* dans le run du ctrl il cr�� la vue et du coup est dans le m�me thread
		 //* il s'abonne au button
		 //* + il observe le model
		 //* et rend la vue visible
		 * 
		 //* soit dans le main soit dans le run ctrl
		 //* on cr�� un nouveau thread, on y met la cad
		 * 
		 //* la CAD se lance avec un .start et sur un while1 qui met a jour tout le temps
		 //* elle v�rifie si les infos par rapport au modele ont chang�
		 //* si oui, elle lance le notify observers
		 //* �a trigger le ctrl
		 * qui recup les infos et met a jour la vue
		 * 
		 * 
		 * 
		 * le button trigger le ctrl qui change le modele 
		 * et qui en changeant met a jour la vue en triggant le ctrl
		 * 
		 * la vue affiche le graph
		 * 
		*/
		
		
		
		
		
		
		ctrl.run();
	}

}
