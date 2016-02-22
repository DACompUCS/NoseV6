package gui;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import persistence.Grafico;
import persistence.Network;
import persistence.Nodo;

public class Principal extends JFrame{
	private JPanel   painel;
	private Devices  devices;
	private Table    table;
	private TextArea textArea;
	private Network  network;
	private Grafico  grafico1;
	private Grafico  grafico2;
	private Grafico  grafico3;
	private JTabbedPane tabbed;
	private final String cTitleG1 = "Tipos de Endereços";
	private final String cTitleG2 = "Tipos de Classe de Tráfego";
	private final String cTitleG3 = "Proximo Cabeçalho";
	private JTabbedPane tabbedBaixo;
	private String  cTitleBaixo1 = "Informações Pacote";
	private String  cTitleBaixo2 = "Próximo Cabeçalho";
	private TextArea textAreaEst;
	
	public Principal() {
		
		this.initComponente();
		
		// -- inicia conexao com rede
		network = new Network();
		network.setTable(this.table.gettTable());
		network.setTextarea(textArea);
		network.setTextEst(textAreaEst);
		
		this.grafico1.setList(this.network.getListG1());
		this.grafico2.setList(this.network.getListG2());
		this.grafico3.setList(this.network.getListG3());
		
		network.setG1(grafico1);
		network.setG2(grafico2);
		network.setG3(grafico3);

		
		LinkedList<String> listCB = network.getDevices();
		this.devices.setNetwork(network);
		this.devices.setTable(table);
		this.devices.seGraficos(tabbed);
	
		this.setTitle("NoseV6");
		
		// -- seta opcoes de interfaces no combobox
		this.devices.setArraytoCombobox(listCB);

		painel = new JPanel(new GridLayout(2,1));
		painel.add(devices);
		painel.add(tabbed);
		
		this.tabbed.add(this.cTitleG1,grafico1.getGrafico());
		this.tabbed.add(this.cTitleG2,grafico2.getGrafico());
		this.tabbed.add(this.cTitleG3,grafico3.getGrafico());

		this.tabbedBaixo.add(this.cTitleBaixo1,this.textArea);
		this.tabbedBaixo.add(this.cTitleBaixo2,this.textAreaEst);
		
		
		// -- carrega imagens
		try {
		    Image img = ImageIO.read(getClass().getResource("/img/icon.png"));
		    this.setIconImage(img);
		   
		  } catch (IOException ex) {
		  
		  }		
		
		//GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		//int width = gd.getDisplayMode().getWidth()/2;
		//int height = gd.getDisplayMode().getHeight()/2;
		
		this.getContentPane().setLayout(new BorderLayout());
		this.add(table,BorderLayout.CENTER);
		this.add(painel,BorderLayout.WEST);
		this.add(tabbedBaixo,BorderLayout.SOUTH);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	private void initComponente(){
		this.devices  = new Devices();
		this.table    = new Table();
		this.textArea = new TextArea();
		this.textAreaEst   = new TextArea();
		this.tabbed   	   = new JTabbedPane();
		this.tabbedBaixo   = new JTabbedPane();
		this.grafico1 = new Grafico("", new LinkedList<Nodo>());
		this.grafico2 = new Grafico("", new LinkedList<Nodo>());
		this.grafico3 = new Grafico("", new LinkedList<Nodo>());
		
	}
	
	public static void main(String[] args) {
	
		
		// -- define aparencia do programa
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Principal t = new Principal();
		
		
	}
	
}
