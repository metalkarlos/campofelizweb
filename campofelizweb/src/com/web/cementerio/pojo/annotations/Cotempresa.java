package com.web.cementerio.pojo.annotations;

// Generated 20/08/2015 11:11:47 PM by Hibernate Tools 3.4.0.CR1

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
 * Cotempresa generated by hbm2java
 */
@Entity
@Table(name = "cotempresa", catalog = "campofelizweb")
public class Cotempresa implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4076824671037676991L;
	private int idempresa;
	private Setestado setestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private String paginaweb;
	private Date fecharegistro;
	private String iplog;
	private Date fechamodificacion;

	public Cotempresa() {
	}

	public Cotempresa(int idempresa, Date fecharegistro) {
		this.idempresa = idempresa;
		this.fecharegistro = fecharegistro;
	}

	public Cotempresa(int idempresa, Setestado setestado,
			Setusuario setusuario, String nombre, String descripcion,
			String paginaweb, Date fecharegistro, String iplog,
			Date fechamodificacion) {
		this.idempresa = idempresa;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.paginaweb = paginaweb;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.fechamodificacion = fechamodificacion;
	}

	@Id
	@Column(name = "idempresa", unique = true, nullable = false)
	public int getIdempresa() {
		return this.idempresa;
	}

	public void setIdempresa(int idempresa) {
		this.idempresa = idempresa;
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

	@Column(name = "nombre", length = 200)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 500)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "paginaweb", length = 200)
	public String getPaginaweb() {
		return this.paginaweb;
	}

	public void setPaginaweb(String paginaweb) {
		this.paginaweb = paginaweb;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharegistro", nullable = false, length = 19)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion", length = 19)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

}