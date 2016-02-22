package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import persistence.Network;

public class Devices extends JPanel implements ActionListener{
	/**
	 *@author Álisson
	 *@since 14/05/2015
	 * 
	 **/

	private JComboBox<String>cbDevices;
	private JButton bIniciar;
	private JButton bPausar;
	private JButton bContinuar;
	private JPanel  inferior ;
	private Network network;
	private JTable  jTable ;
	private JComponent  graficos;
	private JButton  bBuscar; 
	private String  cFileName;
	private JLabel  lFileName;
	private JCheckBox cbOffline;

	public Devices() {
		// TODO Auto-generated constructor stub
		this.bIniciar = new JButton("Iniciar");
		this.bIniciar.setActionCommand("Iniciar");
		this.bBuscar  = new JButton("Buscar");
		this.bBuscar.setActionCommand("Buscar");
		this.cbOffline = new JCheckBox("Modo Offline");
		//this.bPausar.setActionCommand("Pausar");
		//this.bContinuar  = new JButton("Continuar");
		this.inferior    = new JPanel(new GridLayout(1, 2));
		this.cbDevices   = new JComboBox<String>();

		this.bIniciar.addActionListener(this);
		this.bBuscar.addActionListener(this);

		this.lFileName = new JLabel();

		// -- carrega imagens
		try {
			Image img = ImageIO.read(getClass().getResource("/img/pause.png"));
			//bPausar.setIcon(new ImageIcon(img));

			img = ImageIO.read(getClass().getResource("/img/play.png"));
			bIniciar.setIcon(new ImageIcon(img));

			img = ImageIO.read(getClass().getResource("/img/next.png"));
			bBuscar.setIcon(new ImageIcon(img));

		} catch (IOException ex) {
		}


		this.initPanel();

	}

	public void setNetwork(Network nt){
		this.network = nt;
	}


	public void seGraficos(JComponent graficos){
		this.graficos = graficos;
	}

	public JTable getjTable() {
		return jTable;
	}

	public void setjTable(JTable jTable) {
		this.jTable = jTable;
	}

	private void initPanel(){
		this.setName("Partida");
		this.setLayout(new GridLayout(7,1));
		this.inferior.add(this.bBuscar);
		this.inferior.add(this.lFileName);
		this.add(new JLabel("Interfaces:"));
		this.add(this.cbDevices);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(bIniciar);
		this.add(inferior);
		this.add(this.cbOffline);
	}

	public void setArraytoCombobox(LinkedList<String> list){
		Iterator<String> it = list.iterator();

		while (it.hasNext()) {
			String string = (String) it.next(); 
			this.cbDevices.addItem(string);

		}

	}

	public void setTable(Table table){
		this.jTable = table.gettTable();

	}

	public JComboBox<String> getCbDevices() {
		return cbDevices;
	}

	public void setCbDevices(JComboBox<String> cbDevices) {
		this.cbDevices = cbDevices;
	}

	public JButton getbIniciar() {
		return bIniciar;
	}

	public void setbIniciar(JButton bIniciar) {
		this.bIniciar = bIniciar;
	}

	public JButton getbPausar() {
		return bPausar;
	}

	public void setbPausar(JButton bPausar) {
		this.bPausar = bPausar;
	}

	public JButton getbContinuar() {
		return bContinuar;
	}

	public void setbContinuar(JButton bContinuar) {
		this.bContinuar = bContinuar;
	}

	public String getcFileName() {
		return cFileName;
	}

	public void setcFileName(String cFileName) {
		this.cFileName = cFileName;
	}


	public JCheckBox getCbOffline() {
		return cbOffline;
	}

	public void setCbOffline(JCheckBox cbOffline) {
		this.cbOffline = cbOffline;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Thread t1 = null;
		String  string = e.getActionCommand();
		final Integer nOpc   = cbDevices.getSelectedIndex();;
		final Network networkThread  = this.network;

		t1 = new Thread();


		if(string.equals("Iniciar") ){
			if (this.network != null){;
			this.network.setlOffline(cbOffline.isSelected());
			t1 = new Thread(new Runnable() {

				@Override
				public void run() {
					networkThread.setTextarea(new TextArea());
					networkThread.preparePackage(nOpc);
					graficos.repaint();

				}
			});

			t1.start();

			}


		}

		if(string.equals("Buscar") ){
			if (this.network != null){;
			JFileChooser fileChooser = new JFileChooser();

			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				this.network.setlOffline(cbOffline.isSelected());
				this.cFileName = selectedFile.getAbsolutePath();
				this.lFileName.setText(this.cFileName);
				this.network.setcFileName(this.cFileName);

			}
			}


		}
	}

}
