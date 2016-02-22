package persistence;
import java.awt.TextArea;
import java.awt.font.NumericShaper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JHeader;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import de.toolforge.googlechartwrapper.Color;


public class Network{  
	//Cria um list para armazenar as interface
	List<PcapIf> alldevs = null;

	//Para armazenar os erros
	StringBuilder errbuf = null;

	JTable   table    			= null;
	TextArea textarea 			= null;
	LinkedList<String>listheader= null;
	LinkedList<Nodo> listG1     = null;
	LinkedList<Nodo> listG2     = null;
	LinkedList<Nodo> listG3     = null;
	LinkedList<Nodo> listT1     = null;
	TextArea  textEstat         = null;
	Boolean   lOffline          = false; 
	private String  cFileName;

	Grafico g1;
	Grafico g2;
	Grafico g3;
	int contaIp6 = 1;
	int totalNroCab = 0;
	int nNo = 1;
	float mediaPct = 0;

	Pcap pcap = null;

	public Network() {
		this.alldevs = new ArrayList<PcapIf>();
		this.listheader = new LinkedList<String>();
		this.errbuf = new StringBuilder();
		// -- retorna 0 se não deu erro na busca de interfaces
		int r = Pcap.findAllDevs(this.alldevs, this.errbuf);

		// -- gera lista para primeira estatistica
		this.listG1 = new LinkedList<Nodo>();
		this.listG2 = new LinkedList<Nodo>();
		this.listG3 = new LinkedList<Nodo>();
		this.listT1 = new LinkedList<Nodo>();


		this.listG1.add(new Nodo("NSAP", "", new Double(0),Color.BLUE));
		this.listG1.add(new Nodo("IPX", "", new Double(0),Color.CYAN));
		this.listG1.add(new Nodo("Aggregatable Global Unicast", "", new Double(0),Color.DARK_GRAY));
		this.listG1.add(new Nodo("Link Local Unicast Addresses", "", new Double(0),Color.ORANGE));
		this.listG1.add(new Nodo("Site Local Unicast Addrasses", "", new Double(0),Color.PINK));
		this.listG1.add(new Nodo("Multicast Addrasses", "", new Double(0),Color.WHITE));


		this.listG2.add(new Nodo("Routine", "", new Double(0),Color.BLUE));
		this.listG2.add(new Nodo("Priority", "", new Double(0),Color.BLUE));
		this.listG2.add(new Nodo("Immediate", "", new Double(0),Color.BLUE));
		this.listG2.add(new Nodo("Flash", "", new Double(0),Color.BLUE));
		this.listG2.add(new Nodo("Flash-Override", "", new Double(0),Color.BLUE));
		this.listG2.add(new Nodo("Critical", "", new Double(0),Color.BLUE));
		this.listG2.add(new Nodo("Internet", "", new Double(0),Color.BLUE));
		this.listG2.add(new Nodo("Network", "", new Double(0),Color.BLUE));


		this.listG3.add(new Nodo("Hop-by-Hop Options Header","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("TCP","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("UDP","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("Encapsulated IPv6 Header","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("Routing Header","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("Fragment Header","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("Resource ReSerVation Protocol","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("Encapsulating Security Payload","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("Authentication Header","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("ICMPv6","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("No next header","",new Double(0), Color.BLUE));
		this.listG3.add(new Nodo("Destination Options Header","",new Double(0), Color.BLUE));


		this.listT1.add(new Nodo("Hop-by-Hop Options Header","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("TCP","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("UDP","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("Encapsulated IPv6 Header","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("Routing Header","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("Fragment Header","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("Resource ReSerVation Protocol","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("Encapsulating Security Payload","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("Authentication Header","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("ICMPv6","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("No next header","",new Double(0), Color.BLUE));
		this.listT1.add(new Nodo("Destination Options Header","",new Double(0), Color.BLUE));


	}

	public int converteIndice(int header){

		int indiceNodo = -1;

		switch (header){
		case 0:
			indiceNodo = 0;
			break;
		case 6:
			indiceNodo = 1;
			break;
		case 17:
			indiceNodo = 2;
			break;
		case 41:
			indiceNodo = 3;
			break;
		case 43:
			indiceNodo = 4;
			break;
		case 44:
			indiceNodo = 5;
			break;
		case 46:
			indiceNodo = 6;
			break;
		case 50:
			indiceNodo = 7;
			break;
		case 51:
			indiceNodo = 8;
			break;
		case 58:
			indiceNodo = 9;
			break;
		case 59:
			indiceNodo = 10;
			break;
		case 60:
			indiceNodo = 11;
			break;
		}

		return indiceNodo;

	}

	public LinkedList<String> getDevices(){

		Iterator it = alldevs.iterator();
		LinkedList<String> llReturn = new LinkedList<String>();


		while (it.hasNext()) {

			PcapIf pCapDevice = (PcapIf) it.next();

			llReturn.add(pCapDevice.getName() +" - " +(pCapDevice.getDescription()) );

		}

		return llReturn;
	}


	public void preparePackage (int nOPC){

		//System.out.println(nOPC);
		PcapIf device = alldevs.get(nOPC);  


		//Para abrir comunicacao com o dispositivo 
		int snaplen = 64 * 1024;           // Capture all packets, no trucation  
		int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
		int timeout = 10 * 1000;           // 10 seconds in millis    

		if (!this.lOffline){
			pcap =  Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
		}else{
			pcap = Pcap.openOffline(cFileName, errbuf);
		}

		//Caso der erro
		if (pcap == null) {  
			System.err.printf("Erro na abertura da interface para a captura: "  + errbuf.toString());  
			return;  
		}

		capturePackages();
	}


	public  void  capturePackages(){
		PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {  

			public void nextPacket(PcapPacket packet, String user) {  

				//Funcionalidade 1
				garimpaPacote(packet);

				//int classe = classeTrafego(packet);
				//System.out.println(classe);


			}  
		};

		pcap.loop(10000, jpacketHandler, "jNetPcap rocks!");  

		pcap.close();

	}

	//funcionalidade 1:número IP da máquina origem, número IP da máquina destino.
	public void garimpaPacote (PcapPacket packet){

		Ip6 ip6 = new Ip6();
		Ip4 ip4 = new Ip4();
		Tcp tcp = new Tcp();
		Udp udp = new Udp();


		JPacket.State state = packet.getState();

		long bitmap = state.get64BitHeaderMap(0);


		// --se ipv6 continua o garimpo
		if 	(packet.hasHeader(ip6)) {

			contaIp6++;

			// -- captura dadps de origem e destino
			String source 	   = FormatUtils.ip(ip6.source());
			String destination = FormatUtils.ip(ip6	.destination());
			String protocol    = String.valueOf(ip6.getNicname());
			int    nProx       = this.proximoCabecalho(packet);
			String nextHeader  = descrCabecalho(nProx);
			String number      = String.valueOf(packet.getFrameNumber());
			String cTipTraf    = "";
			int nroCab = retornaNroCabecalhos(packet);
			totalNroCab = totalNroCab + nroCab;
			System.out.println("numero pacote"+number);


			// -- pega dados para estatistica 1
			int nEstatitica = this.estatisticaEndereco(packet);

			// -- atuaiza dados de graficos
			Nodo nodoAux = listG1.get(nEstatitica);
			nodoAux.setnValor(new Double(nodoAux.getnValor() + 1));
			listG1.set(nEstatitica, nodoAux);

			PieDataset dataset1 = g1.criaDataSet(listG1);

			PiePlot plot = (PiePlot) g1.getChart().getPlot();
			plot.setDataset(dataset1);

			int nClassTraf = this.classeTrafego(packet);
			// -- atuaiza dados de graficos
			Nodo nodoAux2 = listG2.get(nClassTraf);
			nodoAux2.setnValor(new Double(nodoAux2.getnValor() + 1));
			listG2.set(nClassTraf, nodoAux2);

			PieDataset dataset2 = g2.criaDataSet(listG2);

			PiePlot plot2 = (PiePlot) g2.getChart().getPlot();
			plot2.setDataset(dataset2);

			cTipTraf = nodoAux2.getcTitulo();


			// -- atuaiza dados de graficos


			int indice = this.proximoCabecalho(packet);
			int posicao = converteIndice(indice);
			System.out.println("vai somar na = "+posicao);

			Nodo nodoAux3 = listG3.get(posicao);
			nodoAux3.setnValor(new Double(nodoAux3.getnValor() + 1));

			//listG3.set(posicao, nodoAux3);
			PieDataset dataset3 = g3.criaDataSet(listG3);

			PiePlot plot3 = (PiePlot) g3.getChart().getPlot();
			plot3.setDataset(dataset3);








			// -- atualiza tabela
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[]{nNo++, source, destination, protocol,nextHeader, number,cTipTraf,nroCab});
			// -- preenche textarea com informacoes do pacote
			this.listheader.add(packet.toString());

		}

		else{
			if (packet.hasHeader(udp)){

				String source 	   =  String.valueOf(udp.source());
				String destination = String.valueOf(udp.destination());
				String protocol    = String.valueOf(udp.getNicname());
				String nextHeader  = descrCabecalho(this.proximoCabecalho(packet));
				String number      = String.valueOf(packet.getFrameNumber());
				String cTipTraf    = "";
				int nroCab = retornaNroCabecalhos(packet);



				// -- atualiza tabela
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{nNo++, source, destination, protocol,nextHeader, number,cTipTraf, nroCab});
				// -- preenche textarea com informacoes do pacote
				this.listheader.add(packet.toString());



			}
			/*			else{
				if (packet.hasHeader(ip4)){
					String source 	   =  String.valueOf(ip4.source());
					String destination = String.valueOf(ip4.destination());
					String protocol    = String.valueOf(ip4.getNicname());
					String nextHeader  = descrCabecalho(this.proximoCabecalho(packet));
					String number      = String.valueOf(packet.getFrameNumber());
					String cTipTraf    = "";



					// -- atualiza tabela
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.addRow(new Object[]{nNo++, source, destination, protocol,nextHeader, number,cTipTraf});
					// -- preenche textarea com informacoes do pacote
					this.listheader.add(packet.toString());
					System.out.println("pacote = "+number);
					}

			}*/
		}

		this.textEstat.setText(this.escreveEst());
		mediaPct = totalNroCab/contaIp6;
		System.out.println("Media pacotes = "+mediaPct);

	}


	//funcionalidade 2: estatísticas referentes aos tipos de endereços utilizados: NSAP, IPX, Aggregatable Global Unicast, Link Local Unicast Addresses...
	/*
		https://en.wikipedia.org/wiki/Reserved_IP_addresses
		https://www.ultratools.com/tools/ipv6CIDRToRange

	1	NSAP                          200:: - 21ff:	ffff:ffff:ffff:ffff:ffff:ffff:ffff = 512<>8703
	2	IPX                           400:: - 5ff:	ffff:ffff:ffff:ffff:ffff:ffff:ffff = 1024<>1535
	3	Aggregatable Global Unicast  2000:: - 3fff:	ffff:ffff:ffff:ffff:ffff:ffff:ffff = 8192<>16383
	4	Link Local Unicast Adresses  fe80:: - febf:	ffff:ffff:ffff:ffff:ffff:ffff:ffff = 65152<>65215
	5	Site Local Unicast Addresses fec0:: - feff:	ffff:ffff:ffff:ffff:ffff:ffff:ffff = 65216<>65279
	6	Multicast Adresses           ff00:: – ffff:	ffff:ffff:ffff:ffff:ffff:ffff:ffff = 65280<>65535
	 */
	public String escreveEst(){
		String cReturn = "";

		cReturn += "Media de pacotes = "+mediaPct+"\r\n-------------------------------------------\r\n";

		Iterator<Nodo> it = listG3.iterator();

		while (it.hasNext()) {
			Nodo nodo = (Nodo) it.next();

			cReturn += nodo.getcTitulo() + " = " + String.valueOf(nodo.getnValor()) + "\r\n";
		}



		return cReturn;
	}

	public int estatisticaEndereco (PcapPacket packet){
		Ip6 ip6 = new Ip6();
		int tipo = 0;

		if 	(packet.hasHeader(ip6)) {  

			String ipSource = FormatUtils.ip(ip6.source());

			char[] endereco = ipSource.toCharArray();

			//System.out.printf("IP SOURCE:%s %n ",ipSource);

			int index = 0;
			for (int i = 0; i< endereco.length; i++){

				if (endereco[i] == ':'){
					index = i;
					break;
				}	
			}
			String comparacao = ipSource.substring(0, index);

			int decimal = Integer.parseInt(comparacao, 16);

			//System.out.println ("Decimal "+decimal);
			//Verifica em qual tipo se encaixa
			if (decimal >= 512 && decimal <= 8703){
				tipo =  1;
			}
			else{
				if (decimal >= 1024 && decimal <= 1535){
					tipo =  2;
				}
				else{
					if (decimal >= 8192 && decimal <= 16383){
						tipo =  3;
					}
					else{
						if (decimal >= 65152 && decimal <= 65215){
							tipo =  4;
						}
						else{
							if (decimal >= 65216 && decimal <= 65279){
								tipo =  5;
							}
							else{
								if (decimal >= 65280 && decimal <= 65535){
									tipo =  6;
								}

							}

						}

					}

				}
			}

		}    	
		return tipo;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}






	/*
	//Funcionalidade 1. Tipo do proximo cabeçalho
	 //BIT número 6 contem o valor do próximo cabeçalho

	 */

	public int proximoCabecalho(PcapPacket packet){
		int nProximo = 0;

		Ip6 ip6 = new Ip6();
		if  (packet.hasHeader(ip6)) {  
			byte[] header = ip6.getHeader();
			nProximo = header[6];
		}

		return nProximo;

	}

	//Recebe um valor de cabeçalho e converte em uma string
	public String descrCabecalho (int valor){

		String descr = null;

		switch (valor){
		case 0:
			descr = "Hop-by-Hop Options Header";
			break;
		case 6:
			descr = "TCP";
			break;
		case 17:
			descr = "UDP";
			break;
		case 41:
			descr = "Encapsulated IPv6 Header";
			break;
		case 43:
			descr = "Routing Header";
			break;
		case 44:
			descr = "Fragment Header";
			break;
		case 46:
			descr = "Resource ReSerVation Protocol";
			break;
		case 50:
			descr = "Encapsulating Security Payload";
			break;
		case 51:
			descr = "Authentication Header";
			break;
		case 58:
			descr = "ICMPv6";
			break;
		case 59:
			descr = "No next header";
			break;
		case 60:
			descr = "Destination Options Header";
			break;
		}
		return descr;
	}





	public TextArea getTextarea() {
		return textarea;
	}

	public void setTextarea(final TextArea textarea) {
		this.textarea = textarea;

		// TODO Auto-generated method stub
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				// do some actions here, for example
				// print first column value from selected row
				//System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
				textarea.setText(listheader.get(table.getSelectedRow()));
			}
		});

	}

	public void setTextEst(final TextArea textarea) {
		this.textEstat = textarea;
	}



	/*funcionalidade 3: estatÌsticas referentes ao tipo de classe de tr·fego constante em cada pacote.
    http://jnetpcap.com/docs/javadocs/jnetpcap-1.3/index.html

    =========================================================
    -> 6 bits mais significativos para servicos diferenciados
    000 - 0 - Routine
    001 - 1 - Priority
    010 - 2 - Immediate
    011 - 3 - Flash
    100 - 4 - Flash-override
    101 - 5 - Critical
    110 - 6 - Internet
    111 - 7 - Network

    -> 2 bits menos significativos usados para ECN.
    00 - Sistema final n„o entende ECN
    01 - Sistema final n„o entende ECN
    10 - N„o h· congestionamento
    11 - NotificaÁ„o de congestionamento
    ==========================================================
	 */

	public int classeTrafego (PcapPacket packet){

		//Para a an·lise, ser„o considerados somente os 3 primeiros bits (mais significativos)
		//para a identificaÁ„o da classe.


		int classeTrafego = -1;
		int tipo = -1;

		Ip6 ip6 = new Ip6();
		if  (packet.hasHeader(ip6)) {  
			int nParte1 = 0;
			int nParte2 = 0;

			// -- carrega valores rais 
			byte[] header = ip6.getHeader();

			nParte1 = header[0];
			nParte1 = (nParte1 & Integer.parseInt("11110000", 2)) >> 4;
			nParte2 = header[1];
			nParte2 = (nParte2 & Integer.parseInt("00001111", 2)) << 4;

			System.out.println("nparte1 = "+nParte1+" - nparte2 = "+nParte2);

			classeTrafego = nParte1 | nParte2;
			System.out.println("ClasseTrafego = "+classeTrafego);

			//classeTrafego = ip6.trafficClass();


			if (classeTrafego>=0 && classeTrafego<=31){
				tipo = 0;
			}
			else{
				if (classeTrafego>=32 && classeTrafego<=63){
					tipo = 1;
				}
				else{
					if (classeTrafego>=64 && classeTrafego<=95){
						tipo = 2;
					}
					else{
						if (classeTrafego>=96 && classeTrafego<=127){
							tipo = 3;
						}
						else{
							if (classeTrafego>=128 && classeTrafego<=159){
								tipo = 4;
							}
							else{
								if (classeTrafego>=160 && classeTrafego<=191){
									tipo = 5;

								}
								else{
									if (classeTrafego>=192 && classeTrafego<=223){
										tipo = 6;
									}
									else{
										if (classeTrafego>=224 && classeTrafego<=255){
											tipo = 7;
										}
									}
								}
							}
						}
					}
				}

			}

		}
		return tipo;
	}



	/*

	  Funcionalidade 5: Estatística sobre o Próximo Cabeçalho
		O software deverá mostrar estatísticas referentes:
		- ao tipo do próximo cabeçalho (opção nó a nó, fragmentação,etc.) encontrados na amostra;
		- média da quantidade de cabeçalhos presentes em cada pacote IPv6	
	 */
	public int retornaNroCabecalhos(PcapPacket packet){


		int numeroCabecalhos = 0;

		numeroCabecalhos = packet.getHeaderCount();

		return numeroCabecalhos;

	}




	public LinkedList<Nodo> getListG3() {
		return listG3;
	}

	public void setListG3(LinkedList<Nodo> listG3) {
		this.listG3 = listG3;
	}

	public Grafico getG3() {
		return g3;
	}

	public void setG3(Grafico g3) {
		this.g3 = g3;
	}

	public LinkedList<Nodo> getListG1() {
		return listG1;
	}

	public void setListG1(LinkedList<Nodo> listG1) {
		this.listG1 = listG1;
	}

	public LinkedList<Nodo> getListG2() {
		return listG2;
	}

	public void setListG2(LinkedList<Nodo> listG2) {
		this.listG2 = listG2;
	}

	public Grafico getG1() {
		return g1;
	}

	public void setG1(Grafico g1) {
		this.g1 = g1;
	}

	public Grafico getG2() {
		return g2;
	}

	public void setG2(Grafico g2) {
		this.g2 = g2;
	}

	public String getcFileName() {
		return cFileName;
	}

	public void setcFileName(String cFileName) {
		this.cFileName = cFileName;
	}

	public Boolean getlOffline() {
		return lOffline;
	}

	public void setlOffline(Boolean lOffline) {
		this.lOffline = lOffline;
	}	





}