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
	private JLabel lblWarning;
	private JLabel lblPointDeRos;
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
		chart = ChartFactory.createXYLineChart("Temp�rature Ext�rieure / Temp�rature Int�rieure", "Temps", "Temp�rature (�C)", dataset, PlotOrientation.VERTICAL, true, true, false);
		chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(667, 312, 774, 486);
		contentPane.add(chartPanel);
		
		JLabel lblTempc = new JLabel("Temp \u00B0C : ");
		lblTempc.setBounds(325, 354, 88, 16);
		contentPane.add(lblTempc);
		
		JLabel lblTempOut = new JLabel("Temp \u00B0C :");
		lblTempOut.setBounds(671, 100, 103, 27);
		contentPane.add(lblTempOut);
		
		JLabel lblHum = new JLabel("Hum % :");
		lblHum.setBounds(325, 462, 88, 16);
		contentPane.add(lblHum);
		
		JLabel lblTempDsire = new JLabel("Temp d\u00E9sir\u00E9e \u00B0C : ");
		lblTempDsire.setBounds(38, 271, 132, 16);
		contentPane.add(lblTempDsire);
		
		JLabel lblWarning = new JLabel("");
		lblWarning.setBounds(1038, 26, 300, 265);
		lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarning.setIcon(new ImageIcon(new ImageIcon("warning.png").getImage().getScaledInstance(300, 265, Image.SCALE_DEFAULT)));
		lblWarning.setVisible(false);
		contentPane.add(lblWarning);
		
		JLabel lblPointDeRos = new JLabel("Point de ros\u00E9 atteint");
		lblPointDeRos.setForeground(Color.RED);
		lblPointDeRos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPointDeRos.setToolTipText("");
		lblPointDeRos.setBounds(1128, 153, 121, 37);
		lblPointDeRos.setVisible(false);
		contentPane.add(lblPointDeRos);
		
		JButton butPlus = new JButton("+");
		butPlus.setBounds(50, 221, 97, 37);
		contentPane.add(butPlus);
		
		JButton butMoins = new JButton("-");
		butMoins.setBounds(50, 300, 97, 37);
		contentPane.add(butMoins);
		
		JLabel icoFrigo = new JLabel("");
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
		return butPlus;
	}

	public JButton getButMoins() {
		return butMoins;
	}

	public JLabel getIcoFrigo() {
		return icoFrigo;
	}
	
	public void setVisible(boolean b){
		this.setVisible(b);
	}

	public JLabel getLblTempOut() {
		return lblTempOut;
	}
	
	@Override
	public void updateGraph(float tempInterieure, float tempExterieure) {
		
			addToChart(tempInterieure, tempExterieure);
			
			if (this.iTime<25) {
				this.tempInt.remove(1);
				this.tempOut.remove(1);
				this.datasetSeries.notify();
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
		this.datasetSeries.notify();
		
	}
	
	@Override
	public void setWarn(boolean b) {
		this.lblPointDeRos.setVisible(b);
		this.lblWarning.setVisible(b);
	}
}
