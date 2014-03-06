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
 * Petplantillaencuestadet generated by hbm2java
 */
@Entity
@Table(name = "petplantillaencuestadet", schema = "pets")
public class Petplantillaencuestadet implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6753702737408700281L;
	private int idplantillaencuestadet;
	private Setestado setestado;
	private Petplantillaencuesta petplantillaencuesta;
	private Petplantillaencuestagrupo petplantillaencuestagrupo;
	private Setusuario setusuario;
	private String enunciado;
	private String descripcion;
	private String tag;
	private Date fecharegistro;
	private String iplog;
	private Set<?> petplantillaencuestadetcasillas = new HashSet<Object>(0);

	public Petplantillaencuestadet() {
	}

	public Petplantillaencuestadet(int idplantillaencuestadet,
			Petplantillaencuesta petplantillaencuesta,
			Petplantillaencuestagrupo petplantillaencuestagrupo,
			Date fecharegistro) {
		this.idplantillaencuestadet = idplantillaencuestadet;
		this.petplantillaencuesta = petplantillaencuesta;
		this.petplantillaencuestagrupo = petplantillaencuestagrupo;
		this.fecharegistro = fecharegistro;
	}

	public Petplantillaencuestadet(int idplantillaencuestadet,
			Setestado setestado, Petplantillaencuesta petplantillaencuesta,
			Petplantillaencuestagrupo petplantillaencuestagrupo,
			Setusuario setusuario, String enunciado, String descripcion,
			String tag, Date fecharegistro, String iplog,
			Set<?> petplantillaencuestadetcasillas) {
		this.idplantillaencuestadet = idplantillaencuestadet;
		this.setestado = setestado;
		this.petplantillaencuesta = petplantillaencuesta;
		this.petplantillaencuestagrupo = petplantillaencuestagrupo;
		this.setusuario = setusuario;
		this.enunciado = enunciado;
		this.descripcion = descripcion;
		this.tag = tag;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.petplantillaencuestadetcasillas = petplantillaencuestadetcasillas;
	}

	@Id
	@Column(name = "idplantillaencuestadet", unique = true, nullable = false)
	public int getIdplantillaencuestadet() {
		return this.idplantillaencuestadet;
	}

	public void setIdplantillaencuestadet(int idplantillaencuestadet) {
		this.idplantillaencuestadet = idplantillaencuestadet;
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
	@JoinColumn(name = "idusuario")
	public Setusuario getSetusuario() {
		return this.setusuario;
	}

	public void setSetusuario(Setusuario setusuario) {
		this.setusuario = setusuario;
	}

	@Column(name = "enunciado", length = 500)
	public String getEnunciado() {
		return this.enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petplantillaencuestadet", targetEntity=Petplantillaencuestadetcasilla.class)
	public Set<?> getPetplantillaencuestadetcasillas() {
		return this.petplantillaencuestadetcasillas;
	}

	public void setPetplantillaencuestadetcasillas(
			Set<?> petplantillaencuestadetcasillas) {
		this.petplantillaencuestadetcasillas = petplantillaencuestadetcasillas;
	}

}
