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
 * Petfotoinformacion generated by hbm2java
 */
@Entity
@Table(name = "petfotoinformacion")
public class Petfotoinformacion implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4749413840012515794L;
	private int idfotoinformacion;
	private Setestado setestado;
	private Petinformacion petinformacion;
	private Setusuario setusuario;
	private String descripcion;
	private String ruta;
	private String nombrearchivo;
	private Integer perfil;
	private Date fecharegistro;
	private Date fechamodificacion;
	private String iplog;

	public Petfotoinformacion() {
	}

	public Petfotoinformacion(int idfotoinformacion,
			Petinformacion petinformacion, Date fecharegistro) {
		this.idfotoinformacion = idfotoinformacion;
		this.petinformacion = petinformacion;
		this.fecharegistro = fecharegistro;
	}

	public Petfotoinformacion(int idfotoinformacion, Setestado setestado,
			Petinformacion petinformacion, Setusuario setusuario,
			String descripcion, String ruta, String nombrearchivo,
			Integer perfil, Date fecharegistro, Date fechamodificacion, String iplog) {
		this.idfotoinformacion = idfotoinformacion;
		this.setestado = setestado;
		this.petinformacion = petinformacion;
		this.setusuario = setusuario;
		this.descripcion = descripcion;
		this.ruta = ruta;
		this.nombrearchivo = nombrearchivo;
		this.perfil = perfil;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.iplog = iplog;
	}

	@Id
	@Column(name = "idfotoinformacion", unique = true, nullable = false)
	public int getIdfotoinformacion() {
		return this.idfotoinformacion;
	}

	public void setIdfotoinformacion(int idfotoinformacion) {
		this.idfotoinformacion = idfotoinformacion;
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
	@JoinColumn(name = "idinformacion", nullable = false)
	public Petinformacion getPetinformacion() {
		return this.petinformacion;
	}

	public void setPetinformacion(Petinformacion petinformacion) {
		this.petinformacion = petinformacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	public Setusuario getSetusuario() {
		return this.setusuario;
	}

	public void setSetusuario(Setusuario setusuario) {
		this.setusuario = setusuario;
	}

	@Column(name = "descripcion", length = 500)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "ruta", length = 100)
	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Column(name = "nombrearchivo", length = 50)
	public String getNombrearchivo() {
		return this.nombrearchivo;
	}

	public void setNombrearchivo(String nombrearchivo) {
		this.nombrearchivo = nombrearchivo;
	}

	@Column(name = "perfil")
	public Integer getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharegistro", nullable = false, length = 29)
	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion", nullable = false, length = 29)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	@Column(name = "iplog", length = 20)
	public String getIplog() {
		return this.iplog;
	}

	public void setIplog(String iplog) {
		this.iplog = iplog;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException{
	  Petfotoinformacion petfotoinformacion = (Petfotoinformacion) super.clone();
	  return petfotoinformacion;
	}
	
	public Petfotoinformacion clonar() throws Exception{
		return (Petfotoinformacion)this.clone();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((nombrearchivo == null) ? 0 : nombrearchivo.hashCode());
		result = prime * result + ((ruta == null) ? 0 : ruta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Petfotoinformacion other = (Petfotoinformacion) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (nombrearchivo == null) {
			if (other.nombrearchivo != null)
				return false;
		} else if (!nombrearchivo.equals(other.nombrearchivo))
			return false;
		if (ruta == null) {
			if (other.ruta != null)
				return false;
		} else if (!ruta.equals(other.ruta))
			return false;
		return true;
	}

	
	
}
