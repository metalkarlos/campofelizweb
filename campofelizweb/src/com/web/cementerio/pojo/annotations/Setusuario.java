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
 * Setusuario generated by hbm2java
 */
@Entity
@Table(name = "setusuario", schema = "seguridad")
public class Setusuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6052253096281382405L;
	private int idusuario;
	private Setestado setestado;
	private Setperfil setperfil;
	private String nombre;
	private String descripcion;
	private String clave;
	private Date fecharegistro;
	private String iplog;

	public Setusuario() {
	}

	public Setusuario(int idusuario, Date fecharegistro) {
		this.idusuario = idusuario;
		this.fecharegistro = fecharegistro;
	}

	public Setusuario(int idusuario, Setestado setestado, Setperfil setperfil,
			String nombre, String descripcion, String clave,
			Date fecharegistro, String iplog) {
		this.idusuario = idusuario;
		this.setestado = setestado;
		this.setperfil = setperfil;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.clave = clave;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
	}

	@Id
	@Column(name = "idusuario", unique = true, nullable = false)
	public int getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
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
	@JoinColumn(name = "idperfil")
	public Setperfil getSetperfil() {
		return this.setperfil;
	}

	public void setSetperfil(Setperfil setperfil) {
		this.setperfil = setperfil;
	}

	@Column(name = "nombre", length = 20)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "clave", length = 30)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((fecharegistro == null) ? 0 : fecharegistro.hashCode());
		result = prime * result + idusuario;
		result = prime * result + ((iplog == null) ? 0 : iplog.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((setestado == null) ? 0 : setestado.getIdestado());
		result = prime * result
				+ ((setperfil == null) ? 0 : setperfil.getIdperfil());
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
		Setusuario other = (Setusuario) obj;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fecharegistro == null) {
			if (other.fecharegistro != null)
				return false;
		} else if (!fecharegistro.equals(other.fecharegistro))
			return false;
		if (idusuario != other.idusuario)
			return false;
		if (iplog == null) {
			if (other.iplog != null)
				return false;
		} else if (!iplog.equals(other.iplog))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (setestado == null) {
			if (other.setestado != null)
				return false;
		} else if (setestado.getIdestado() != other.setestado.getIdestado())
			return false;
		if (setperfil == null) {
			if (other.setperfil != null)
				return false;
		} else if (setperfil.getIdperfil() != other.setperfil.getIdperfil())
			return false;
		return true;
	}
	
	

}
