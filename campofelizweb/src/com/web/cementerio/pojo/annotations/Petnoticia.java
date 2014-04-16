package com.web.cementerio.pojo.annotations;

// Generated 05/03/2014 11:20:16 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Petnoticia generated by hbm2java
 */
@Entity
@Table(name = "petnoticia", schema = "pets")
public class Petnoticia implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 28485907092748267L;
	private int idnoticia;
	private Setestado setestado;
	private Setusuario setusuario;
	private String titulo;
	private String descripcion;
	private String tag;
	private Date fecharegistro;
	private String iplog;
	private String rutafoto;
	private Set<Petfotonoticia> petfotonoticias = new HashSet<Petfotonoticia>(0);

	public Petnoticia() {
	}

	public Petnoticia(int idnoticia, Date fecharegistro) {
		this.idnoticia = idnoticia;
		this.fecharegistro = fecharegistro;
	}

	public Petnoticia(int idnoticia, Setestado setestado,
			Setusuario setusuario, String titulo, String descripcion,
			String tag, Date fecharegistro, String iplog, Set<Petfotonoticia> petfotonoticias) {
		this.idnoticia = idnoticia;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tag = tag;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.petfotonoticias = petfotonoticias;
	}

	@Id
	@Column(name = "idnoticia", unique = true, nullable = false)
	public int getIdnoticia() {
		return this.idnoticia;
	}

	public void setIdnoticia(int idnoticia) {
		this.idnoticia = idnoticia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idestado")
	public Setestado getSetestado() {
		return this.setestado;
	}

	public void setSetestado(Setestado setestado) {
		this.setestado = setestado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	public Setusuario getSetusuario() {
		return this.setusuario;
	}

	public void setSetusuario(Setusuario setusuario) {
		this.setusuario = setusuario;
	}

	@Column(name = "titulo", length = 1000)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "descripcion", length = 10000)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "tag", length = 200)
	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharegistro", nullable = false, length = 29)
	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	@Column(name = "iplog", length = 20)
	public String getIplog() {
		return this.iplog;
	}

	public void setIplog(String iplog) {
		this.iplog = iplog;
	}

	@Column(name = "rutafoto", length = 100)
	public String getRutafoto() {
		return rutafoto;
	}

	public void setRutafoto(String rutafoto) {
		this.rutafoto = rutafoto;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petnoticia", targetEntity=Petfotonoticia.class)
	public Set<Petfotonoticia> getPetfotonoticias() {
		return this.petfotonoticias;
	}

	public void setPetfotonoticias(Set<Petfotonoticia> petfotonoticias) {
		this.petfotonoticias = petfotonoticias;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Petnoticia petnoticia = (Petnoticia)super.clone();
		return petnoticia;
	}
	
	public Petnoticia clonar() throws Exception {
		return (Petnoticia)this.clone();
	}

}
