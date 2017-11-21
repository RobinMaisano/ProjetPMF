package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import contract.IPMFView;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;

public class PMFView extends JFrame implements IPMFView{

	
	private JPanel contentPane;
	private JLabel lblTempc;
	private JLabel lblHum;
	private JLabel lblTempDsire;
	private JButton butPlus;
	private JButton butMoins;
	private JLabel icoFrigo;

	/**
	 * Create the frame.
	 */
	public PMFView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 777);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTempc = new JLabel("Temp \u00B0C : ");
		lblTempc.setBounds(325, 354, 88, 16);
		contentPane.add(lblTempc);
		
		JLabel lblHum = new JLabel("Hum % :");
		lblHum.setBounds(325, 462, 88, 16);
		contentPane.add(lblHum);
		
		JLabel lblTempDsire = new JLabel("Temp d\u00E9sir\u00E9e \u00B0C : ");
		lblTempDsire.setBounds(38, 271, 132, 16);
		contentPane.add(lblTempDsire);
		
		JButton butPlus = new JButton("+");
		butPlus.setBounds(50, 221, 97, 37);
		contentPane.add(butPlus);
		
		JButton butMoins = new JButton("-");
		butMoins.setBounds(50, 300, 97, 37);
		contentPane.add(butMoins);
		
		JLabel icoFrigo = new JLabel("");
		icoFrigo.setBounds(0, 0, 733, 734);
		icoFrigo.setIcon(new ImageIcon(PMFView.class.getResource("/view/fridge.png")));
		contentPane.add(icoFrigo);
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JLabel getLblTempc() {
		return lblTempc;
	}

	public JLabel getLblHum() {
		return lblHum;
	}

	public JLabel getLblTempDsire() {
		return lblTempDsire;
	}

	public JButton getButPlus() {
		return butPlus;
	}

	public JButton getButMoins() {
		return butMoins;
	}

	public JLabel getIcoFrigo() {
		return icoFrigo;
	}

//	@Override
//	public void setLblTempIn(String content) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setLblHum(String content) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setLblTempDesire(String content) {
//		// TODO Auto-generated method stub
//		
//	}
	
	
}
