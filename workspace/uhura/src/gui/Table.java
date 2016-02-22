package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JPanel{
/**
 *@author Álisson
 *@since 14/05/2015
 * 
 **/
	
	private JTable tTable;
	private	JScrollPane scrollPane;
	
	// coluna
	private String columnNames[] = { "No.","IP Origem","IP Destino","Protocolo","Tipo Prox. Cabec.","Numero Pacote","Classe Tráfego", "Nro. de Cabec."};
	// dados
	private String dataValues[][] = {};
	
	public Table() {
		// TODO Auto-generated constructor stub
		tTable = new JTable(new DefaultTableModel(columnNames, 0));
		this.initPanel();
	}
	
	
	private void initPanel(){
		this.setName("Partida");
		this.setLayout(new BorderLayout());
		// adiciona scroll 
		scrollPane = new JScrollPane( tTable );
		this.add( scrollPane, BorderLayout.CENTER);
		
		
	}


	public JTable gettTable() {
		return tTable;
	}


	public void settTable(JTable tTable) {
		this.tTable = tTable;
	}


	public JScrollPane getScrollPane() {
		return scrollPane;
	}


	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}


	public String[] getColumnNames() {
		return columnNames;
	}


	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}


	public String[][] getDataValues() {
		return dataValues;
	}


	public void setDataValues(String[][] dataValues) {
		this.dataValues = dataValues;
	}
	
	
	
	
	
}
