package com.web.cementerio.pojo.annotations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "petvenunciado", schema = "pets")
public class Petvenunciado implements java.io.Serializable {
	private static final long serialVersionUID = 3054030871709700295L; 

	private Integer idenunciado;
	private String descripcion;
	private Character tipo;
	private Integer idpadre;
	private String tag;
	
	public Petvenunciado(){
		
	}
	
    public Petvenunciado(Integer idenunciado,String descripcion,Character tipo,Integer idpadre, String tag){
		this.idenunciado = idenunciado;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.idpadre = idpadre;
		this.tag = tag;
	}

    @Id
	@Column(name = "idenunciado")
	public int getIdenunciado() {
		return idenunciado;
	}

	public void setIdenunciado(Integer idenunciado) {
		this.idenunciado = idenunciado;
	}

	@Column(name = "descripcion", length = 2000)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "tipo", length = 1)
	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	@Column(name = "idpadre",  nullable = false)
	public int getIdpadre() {
		return idpadre;
	}

	public void setIdpadre(Integer idpadre) {
		this.idpadre = idpadre;
	}
	@Column(name = "tag", length = 200)
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
