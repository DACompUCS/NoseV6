package persistence;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import de.toolforge.googlechartwrapper.Color;

public class Grafico extends JFrame{
	private PieDataset pieDataSet;
	private String	   cTitulo;
	private JPanel 	   painel;
	private LinkedList<Nodo> list;
	private JFreeChart chart;
	

	// -- construtor
	public Grafico(String cTitulo,LinkedList<Nodo> lista) {
		// TODO Auto-generated constructor stub
		this.cTitulo = cTitulo;
		this.pieDataSet = this.criaDataSet(lista);
	}

	public PieDataset criaDataSet(LinkedList<Nodo> array){
		DefaultPieDataset dataset = new DefaultPieDataset();
		this.list = array;
		Iterator it = array.iterator();

		while (it.hasNext()) {
			Nodo object = (Nodo) it.next();
			dataset.setValue(object.toString(),object.getnValor());

		}
		return dataset;
	}

	public JFreeChart criaGrafico(){

		JFreeChart chart = ChartFactory.createPieChart(
				this.cTitulo,		  // chart title
				this.pieDataSet,      // data
				true,                 // include legend
				true,
				true
				);
		
		 final PiePlot plot = (PiePlot) chart.getPlot();
	     plot.setNoDataMessage("Sem informações para o gráfico"); 
	    this.chart = chart;
		return chart;
	}
	
	public JPanel getGrafico(){
		this.painel = new ChartPanel(criaGrafico());
		return this.painel;
	
	}
	
	public void gera(JFreeChart g){
		JPanel panel = new ChartPanel(g);
		this.setTitle(cTitulo);
		this.pack();
		this.getContentPane().add(panel);
		this.setVisible(true);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth()/2;
		int height = gd.getDisplayMode().getHeight()/2;
		this.setSize(width, height);
	}

	public PieDataset getPieDataSet() {
		return pieDataSet;
	}

	public void setPieDataSet(PieDataset pieDataSet) {
		this.pieDataSet = pieDataSet;
	}

	public String getcTitulo() {
		return cTitulo;
	}

	public void setcTitulo(String cTitulo) {
		this.cTitulo = cTitulo;
	}

	public JPanel getPainel() {
		return painel;
	}

	public void setPainel(JPanel painel) {
		this.painel = painel;
	}

	public LinkedList<Nodo> getList() {
		return list;
	}

	public void setList(LinkedList<Nodo> list) {
		this.list = list;
		this.pieDataSet = this.criaDataSet(list);
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	
	

}






