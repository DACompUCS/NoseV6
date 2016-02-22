package persistence;

import org.omg.CORBA.portable.ValueOutputStream;

import de.toolforge.googlechartwrapper.Color;

public class Nodo {
	private String cTitulo;
	private String cCoparavel;
	private Double nValor;
	private Color  cor;
	
	
	public Nodo() {
		super();
	}
	
	
	public Nodo(String cTitulo, String cCoparavel, Double nValor,Color cor) {
		super();
		this.cTitulo = cTitulo;
		this.cCoparavel = cCoparavel;
		this.nValor = nValor;
		this.cor = cor;
	}


	public String getcTitulo() {
		return cTitulo;
	}
	public void setcTitulo(String cTitulo) {
		this.cTitulo = cTitulo;
	}
	public String getcCoparavel() {
		return cCoparavel;
	}
	public void setcCoparavel(String cCoparavel) {
		this.cCoparavel = cCoparavel;
	}
	public Double getnValor() {
		return nValor;
	}
	public void setnValor(Double nValor) {
		this.nValor = nValor;
	}
	
	public Color getCor() {
		return cor;
	}


	public void setCor(Color cor) {
		this.cor = cor;
	}


	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.cTitulo +"="+String.valueOf(this.nValor) ;
	}
	
	
	
	
	
}
