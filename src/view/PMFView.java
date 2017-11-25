package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import contract.IPMFView;
import javax.swing.SwingConstants;

public class PMFView extends JFrame implements IPMFView{

	

	private JPanel contentPane;
	private JLabel lblTempc;
	private JLabel lblTempOut;
	private JLabel lblHum;
	private JLabel lblTempDsire;
	private JButton butPlus;
	private JButton butMoins;
	private JLabel icoFrigo;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private XYDataset dataset;
	private final XYSeries tempInt = new XYSeries("Temp Int");
	private final XYSeries tempOut = new XYSeries("Temp Ext");
	private final XYSeriesCollection datasetSeries = new XYSeriesCollection();
	private Integer iTime = 1;
	private JLabel lblPowerOn;
	private JLabel lblPowerOff;
	private JLabel lblPointDeRose;
	

	/**
	 * Create the frame.
	 */
	public PMFView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1471, 858);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		dataset = createDateset();
		chart = ChartFactory.createXYLineChart("Température Extérieure / Température Intérieure", "Temps", "Température (°C)", dataset, PlotOrientation.VERTICAL, true, true, false);
		chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(667, 312, 774, 486);
		contentPane.add(chartPanel);
		
		lblTempc = new JLabel("Temp \u00B0C : ");
		lblTempc.setBounds(325, 354, 117, 16);
		contentPane.add(lblTempc);
		
		lblTempOut = new JLabel("Temp \u00B0C :");
		lblTempOut.setBounds(671, 100, 132, 27);
		contentPane.add(lblTempOut);
		
		lblHum = new JLabel("Hum % :");
		lblHum.setBounds(325, 462, 117, 16);
		contentPane.add(lblHum);
		
		lblTempDsire = new JLabel("Temp d\u00E9sir\u00E9e \u00B0C : ");
		lblTempDsire.setBounds(38, 271, 132, 16);
		contentPane.add(lblTempDsire);
		
		lblPointDeRose = new JLabel("Point de ros\u00E9e :");
		lblPointDeRose.setBounds(671, 179, 162, 16);
		//lblPointDeRose.setVisible(false);
		contentPane.add(lblPointDeRose);
		
		butPlus = new JButton("+");
		butPlus.setBounds(50, 221, 97, 37);
		contentPane.add(butPlus);
		
		butMoins = new JButton("-");
		butMoins.setBounds(50, 300, 97, 37);
		contentPane.add(butMoins);
		
		lblPowerOn = new JLabel("");
		lblPowerOn.setBounds(33, 418, 113, 265);
		lblPowerOn.setIcon(new ImageIcon(view.PMFView.class.getResource("/View/powerOn.png")));
		lblPowerOn.setVisible(false);
		contentPane.add(lblPowerOn);
		
		lblPowerOff = new JLabel("");
		lblPowerOff.setBounds(33, 418, 113, 265);
		lblPowerOff.setIcon(new ImageIcon(view.PMFView.class.getResource("/View/powerOff.png")));
		lblPowerOff.setVisible(false);
		contentPane.add(lblPowerOff);
		
		icoFrigo = new JLabel("");
		icoFrigo.setBounds(0, 0, 733, 734);
		icoFrigo.setIcon(new ImageIcon(view.PMFView.class.getResource("/View/fridge.png")));
		contentPane.add(icoFrigo);
		
	}
	/**
	 * Create first data of the chart
	 * @return
	 */
	private XYDataset createDateset() {
		this.tempInt.add(0.0,1.0);
		
		this.tempOut.add(0.0,2.0);
		
		
		this.datasetSeries.addSeries(tempInt);
		this.datasetSeries.addSeries(tempOut);
		
		return this.datasetSeries;
	
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
		return this.butPlus;
	}

	public JButton getButMoins() {
		return this.butMoins;
	}

	public JLabel getIcoFrigo() {
		return icoFrigo;
	}
	
	public JLabel getLblPointDeRose() {
		return lblPointDeRose;
	}

	@Override
	public void setVisi(boolean b){
		this.setVisible(b);
	}

	public JLabel getLblTempOut() {
		return lblTempOut;
	}
	
	@Override
	public void updateGraph(float tempInterieure, float tempExterieure) {
		
			addToChart(tempInterieure, tempExterieure);
			
			if (this.iTime>25) {
				this.tempInt.remove(0);
				this.tempOut.remove(0);
				
			}
		
	}
	
	/**
	 * add param to lines Chart
	 * @param tempInterieure float temperature
	 * @param tempExterieure float temperature
	 */
	private void addToChart(float tempInterieure, float tempExterieure) {
		this.tempInt.add((double) this.iTime, (double) tempInterieure);
		this.tempOut.add((double) this.iTime, (double) tempExterieure);
		this.iTime++;
		//this.datasetSeries.notify();
		
	}
	
	@Override
	public void setPower(boolean b) {
		this.lblPowerOff.setVisible(!b);
		this.lblPowerOn.setVisible(b);
	}
}
