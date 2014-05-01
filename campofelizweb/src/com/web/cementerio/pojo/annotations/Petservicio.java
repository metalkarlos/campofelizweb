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
 * Petservicio generated by hbm2java
 */
@Entity
@Table(name = "petservicio", schema = "pets")
public class Petservicio implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6603831901779043690L;
	private int idservicio;
	private Setestado setestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private String tag;
	private Date fecharegistro;
	private String iplog;
	private boolean principal;
	private Date fechamodificacion;
	private String rutafoto;
	private Set<Petfotoservicio> petfotoservicios = new HashSet<Petfotoservicio>(0);

	public Petservicio() {
	}

	public Petservicio(int idservicio, Date fecharegistro) {
		this.idservicio = idservicio;
		this.fecharegistro = fecharegistro;
	}

	public Petservicio(int idservicio, Setestado setestado,
			Setusuario setusuario, String nombre, String descripcion,
			String tag, Date fecharegistro, String iplog, boolean principal,
			Date fechamodificacion, Set<Petfotoservicio> petfotoservicios) {
		this.idservicio = idservicio;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tag = tag;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.principal = principal;
		this.fechamodificacion = fechamodificacion;
		this.petfotoservicios = petfotoservicios;
	}

	@Id
	@Column(name = "idservicio", unique = true, nullable = false)
	public int getIdservicio() {
		return this.idservicio;
	}

	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
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

	@Column(name = "nombre", length = 1000)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	@Column(name = "principal")
	public boolean getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion", length = 29)
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	@Column(name = "rutafoto", length = 100)
	public String getRutafoto() {
		return rutafoto;
	}

	public void setRutafoto(String rutafoto) {
		this.rutafoto = rutafoto;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petservicio", targetEntity=Petfotoservicio.class)
	public Set<Petfotoservicio> getPetfotoservicios() {
		return this.petfotoservicios;
	}

	public void setPetfotoservicios(Set<Petfotoservicio> petfotoservicios) {
		this.petfotoservicios = petfotoservicios;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Petservicio petservicio = (Petservicio)super.clone();
		return petservicio;
	}
	
	public Petservicio clonar() throws Exception {
		return (Petservicio)this.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime
				* result
				+ ((fechamodificacion == null) ? 0 : fechamodificacion
						.hashCode());
		result = prime * result
				+ ((fecharegistro == null) ? 0 : fecharegistro.hashCode());
		result = prime * result + idservicio;
		result = prime * result + ((iplog == null) ? 0 : iplog.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime
				* result
				+ ((petfotoservicios == null) ? 0 : petfotoservicios.hashCode());
		result = prime * result + (principal ? 1231 : 1237);
		result = prime * result
				+ ((rutafoto == null) ? 0 : rutafoto.hashCode());
		result = prime * result
				+ ((setestado == null) ? 0 : setestado.hashCode());
		result = prime * result
				+ ((setusuario == null) ? 0 : setusuario.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
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
		Petservicio other = (Petservicio) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechamodificacion == null) {
			if (other.fechamodificacion != null)
				return false;
		} else if (!fechamodificacion.equals(other.fechamodificacion))
			return false;
		if (fecharegistro == null) {
			if (other.fecharegistro != null)
				return false;
		} else if (!fecharegistro.equals(other.fecharegistro))
			return false;
		if (idservicio != other.idservicio)
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
		if (petfotoservicios == null) {
			if (other.petfotoservicios != null)
				return false;
		} else if (!petfotoservicios.equals(other.petfotoservicios))
			return false;
		if (principal != other.principal)
			return false;
		if (rutafoto == null) {
			if (other.rutafoto != null)
				return false;
		} else if (!rutafoto.equals(other.rutafoto))
			return false;
		if (setestado == null) {
			if (other.setestado != null)
				return false;
		} else if (!setestado.equals(other.setestado))
			return false;
		if (setusuario == null) {
			if (other.setusuario != null)
				return false;
		} else if (!setusuario.equals(other.setusuario))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}
	
	

}
