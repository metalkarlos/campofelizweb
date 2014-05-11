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
 * Petguia generated by hbm2java
 */
@Entity
@Table(name = "petguia", schema = "pets")
public class Petguia implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9018132792136466347L;
	private int idguia;
	private Setestado setestado;
	private Setusuario setusuario;
	private String titulo;
	private String descripcion;
	private String tag;
	private String rutafoto;
	private Date fecharegistro;
	private Date fechamodificacion;
	private Date fechapublicacion;
	private String iplog;
	private boolean principal;
	private Set<Petfotoguia> petfotoguias = new HashSet<Petfotoguia>(0);

	public Petguia() {
	}

	public Petguia(int idguia, Date fecharegistro) {
		this.idguia = idguia;
		this.fecharegistro = fecharegistro;
	}

	public Petguia(int idguia, Setestado setestado, Setusuario setusuario,
			String titulo, String descripcion, String tag,  String rutafoto,
			Date fecharegistro,	Date fechamodificacion,Date fechapublicacion,
			String iplog, boolean principal, Set<Petfotoguia> petfotoguias) {
		this.idguia = idguia;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tag = tag;
		this.rutafoto = rutafoto;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.fechapublicacion = fechapublicacion;
		this.iplog = iplog;
		this.principal = principal;
		this.petfotoguias = petfotoguias;
		
		
	}

	@Id
	@Column(name = "idguia", unique = true, nullable = false)
	public int getIdguia() {
		return this.idguia;
	}

	public void setIdguia(int idguia) {
		this.idguia = idguia;
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

	@Column(name = "descripcion", length = 5000)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "rutafoto", length = 100)
	public String getRutafoto() {
		return this.rutafoto;
	}

	public void setRutafoto(String rutafoto) {
		this.rutafoto = rutafoto;
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion", length = 29)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechapublicacion",  length = 29)
	public Date getFechapublicacion() {
		return this.fechapublicacion;
	}

	public void setFechapublicacion(Date fechapublicacion) {
		this.fechapublicacion = fechapublicacion;
	}
	
	@Column(name = "iplog", length = 20)
	public String getIplog() {
		return this.iplog;
	}

	public void setIplog(String iplog) {
		this.iplog = iplog;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petguia", targetEntity=Petfotoguia.class)
	public Set<Petfotoguia> getPetfotoguias() {
		return this.petfotoguias;
	}

	public void setPetfotoguias(Set<Petfotoguia> petfotoguias) {
		this.petfotoguias = petfotoguias;
	}

	
	@Override
	protected Object clone() throws CloneNotSupportedException{
	  Petguia petguia = (Petguia) super.clone();
	  
	  return petguia;
	}
	
	public Petguia clonar() throws Exception{
		return (Petguia)this.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime
				* result
				+ ((fechapublicacion == null) ? 0 : fechapublicacion.hashCode());
		result = prime * result + (principal ? 1231 : 1237);
		result = prime * result
				+ ((rutafoto == null) ? 0 : rutafoto.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Petguia other = (Petguia) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechapublicacion == null) {
			if (other.fechapublicacion != null)
				return false;
		} else if (!fechapublicacion.equals(other.fechapublicacion))
			return false;
		if (principal != other.principal)
			return false;
		if (rutafoto == null) {
			if (other.rutafoto != null)
				return false;
		} else if (!rutafoto.equals(other.rutafoto))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	
	
	
}
