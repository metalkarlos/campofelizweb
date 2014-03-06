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
 * Petplantillaencuestadetcasilla generated by hbm2java
 */
@Entity
@Table(name = "petplantillaencuestadetcasilla", schema = "pets")
public class Petplantillaencuestadetcasilla implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -741640324038378159L;
	private int idplantillaencuestadetcasilla;
	private Setestado setestado;
	private Petplantillaencuesta petplantillaencuesta;
	private Petplantillaencuestagrupo petplantillaencuestagrupo;
	private Petencuestacasilla petencuestacasilla;
	private Setusuario setusuario;
	private Petplantillaencuestadet petplantillaencuestadet;
	private String descripcion;
	private String tag;
	private Date fecharegistro;
	private String iplog;
	private Set<?> petresultadoencuestadetalles = new HashSet<Object>(0);

	public Petplantillaencuestadetcasilla() {
	}

	public Petplantillaencuestadetcasilla(int idplantillaencuestadetcasilla,
			Petplantillaencuesta petplantillaencuesta,
			Petplantillaencuestagrupo petplantillaencuestagrupo,
			Petencuestacasilla petencuestacasilla,
			Petplantillaencuestadet petplantillaencuestadet, Date fecharegistro) {
		this.idplantillaencuestadetcasilla = idplantillaencuestadetcasilla;
		this.petplantillaencuesta = petplantillaencuesta;
		this.petplantillaencuestagrupo = petplantillaencuestagrupo;
		this.petencuestacasilla = petencuestacasilla;
		this.petplantillaencuestadet = petplantillaencuestadet;
		this.fecharegistro = fecharegistro;
	}

	public Petplantillaencuestadetcasilla(int idplantillaencuestadetcasilla,
			Setestado setestado, Petplantillaencuesta petplantillaencuesta,
			Petplantillaencuestagrupo petplantillaencuestagrupo,
			Petencuestacasilla petencuestacasilla, Setusuario setusuario,
			Petplantillaencuestadet petplantillaencuestadet,
			String descripcion, String tag, Date fecharegistro, String iplog,
			Set<?> petresultadoencuestadetalles) {
		this.idplantillaencuestadetcasilla = idplantillaencuestadetcasilla;
		this.setestado = setestado;
		this.petplantillaencuesta = petplantillaencuesta;
		this.petplantillaencuestagrupo = petplantillaencuestagrupo;
		this.petencuestacasilla = petencuestacasilla;
		this.setusuario = setusuario;
		this.petplantillaencuestadet = petplantillaencuestadet;
		this.descripcion = descripcion;
		this.tag = tag;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.petresultadoencuestadetalles = petresultadoencuestadetalles;
	}

	@Id
	@Column(name = "idplantillaencuestadetcasilla", unique = true, nullable = false)
	public int getIdplantillaencuestadetcasilla() {
		return this.idplantillaencuestadetcasilla;
	}

	public void setIdplantillaencuestadetcasilla(
			int idplantillaencuestadetcasilla) {
		this.idplantillaencuestadetcasilla = idplantillaencuestadetcasilla;
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
	@JoinColumn(name = "idplantillaencuesta", nullable = false)
	public Petplantillaencuesta getPetplantillaencuesta() {
		return this.petplantillaencuesta;
	}

	public void setPetplantillaencuesta(
			Petplantillaencuesta petplantillaencuesta) {
		this.petplantillaencuesta = petplantillaencuesta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idplantillaencuestagrupo", nullable = false)
	public Petplantillaencuestagrupo getPetplantillaencuestagrupo() {
		return this.petplantillaencuestagrupo;
	}

	public void setPetplantillaencuestagrupo(
			Petplantillaencuestagrupo petplantillaencuestagrupo) {
		this.petplantillaencuestagrupo = petplantillaencuestagrupo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idencuestacasilla", nullable = false)
	public Petencuestacasilla getPetencuestacasilla() {
		return this.petencuestacasilla;
	}

	public void setPetencuestacasilla(Petencuestacasilla petencuestacasilla) {
		this.petencuestacasilla = petencuestacasilla;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	public Setusuario getSetusuario() {
		return this.setusuario;
	}

	public void setSetusuario(Setusuario setusuario) {
		this.setusuario = setusuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idplantillaencuestadet", nullable = false)
	public Petplantillaencuestadet getPetplantillaencuestadet() {
		return this.petplantillaencuestadet;
	}

	public void setPetplantillaencuestadet(
			Petplantillaencuestadet petplantillaencuestadet) {
		this.petplantillaencuestadet = petplantillaencuestadet;
	}

	@Column(name = "descripcion", length = 600)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petplantillaencuestadetcasilla", targetEntity=Petresultadoencuestadetalle.class)
	public Set<?> getPetresultadoencuestadetalles() {
		return this.petresultadoencuestadetalles;
	}

	public void setPetresultadoencuestadetalles(Set<?> petresultadoencuestadetalles) {
		this.petresultadoencuestadetalles = petresultadoencuestadetalles;
	}

}
