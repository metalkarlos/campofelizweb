package com.web.cementerio.pojo.annotations;

// Generated 05/03/2014 11:20:16 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Petenunciado generated by hbm2java
 */
@Entity
@Table(name = "petenunciado", schema = "pets")
public class Petenunciado implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2633452523039079616L;
	private int idenunciado;
	private Setestado setestado;
	private Setusuario setusuario;
	private Character tipo;
	private String descripcion;
	private Integer idpadre;
	private Date fecharegistro;
	private String iplog;
	private String tag;

	public Petenunciado() {
	}

	public Petenunciado(int idenunciado, Date fecharegistro) {
		this.idenunciado = idenunciado;
		this.fecharegistro = fecharegistro;
	}

	public Petenunciado(int idenunciado, Setestado setestado,
			Setusuario setusuario, Character tipo, String descripcion,
			Integer idpadre, Date fecharegistro, String iplog, String tag) {
		this.idenunciado = idenunciado;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.idpadre = idpadre;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.tag = tag;
	}

	@Id
	@Column(name = "idenunciado", unique = true, nullable = false)
	public int getIdenunciado() {
		return this.idenunciado;
	}

	public void setIdenunciado(int idenunciado) {
		this.idenunciado = idenunciado;
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

	@Column(name = "tipo", length = 1)
	public Character getTipo() {
		return this.tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	@Column(name = "descripcion", length = 2000)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "idpadre")
	public Integer getIdpadre() {
		return this.idpadre;
	}

	public void setIdpadre(Integer idpadre) {
		this.idpadre = idpadre;
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

	@Column(name = "tag", length = 200)
	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
