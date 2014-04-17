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
 * Petmascotahomenaje generated by hbm2java
 */
@Entity
@Table(name = "petmascotahomenaje", schema = "pets")
public class Petmascotahomenaje implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6299529275804346070L;
	private int idmascota;
	private Setestado setestado;
	private Setusuario setusuario;
	private Petespecie petespecie;
	private String nombre;
	private Date fechanacimiento;
	private Date fechafallecimiento;
	private Date fechapublicacion;
	private String familia;
	private String dedicatoria;
	private String tag;
	private String rutafoto;
	private Date fecharegistro;
	private Date fechamodificacion;
	private String iplog;
	private Set<?> petfotomascotas = new HashSet<Object>(0);

	public Petmascotahomenaje() {
	}

	public Petmascotahomenaje(int idmascota, Date fecharegistro) {
		this.idmascota = idmascota;
		this.fecharegistro = fecharegistro;
	}

	public Petmascotahomenaje(int idmascota, Setestado setestado,
			Setusuario setusuario, Petespecie petespecie, String nombre,
			Date fechanacimiento, Date fechafallecimiento, Date fechapublicacion,
			String familia,String dedicatoria, String tag, String rutafoto,Date fecharegistro,
			Date fechamodificacion,String iplog,
			Set<?> petfotomascotas) {
		this.idmascota = idmascota;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.petespecie = petespecie;
		this.nombre = nombre;
		this.fechanacimiento = fechanacimiento;
		this.fechafallecimiento = fechafallecimiento;
		this.fechapublicacion = fechapublicacion;
		this.familia = familia;
		this.dedicatoria = dedicatoria;
		this.tag = tag;
		this.rutafoto = rutafoto;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.iplog = iplog;
		this.petfotomascotas = petfotomascotas;
	}

	@Id
	@Column(name = "idmascota", unique = true, nullable = false)
	public int getIdmascota() {
		return this.idmascota;
	}

	public void setIdmascota(int idmascota) {
		this.idmascota = idmascota;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idespecie")
	public Petespecie getPetespecie() {
		return this.petespecie;
	}

	public void setPetespecie(Petespecie petespecie) {
		this.petespecie = petespecie;
	}

	@Column(name = "nombre", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechanacimiento", length = 29)
	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechafallecimiento", length = 29)
	public Date getFechafallecimiento() {
		return this.fechafallecimiento;
	}

	public void setFechafallecimiento(Date fechafallecimiento) {
		this.fechafallecimiento = fechafallecimiento;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechapublicacion", length = 29)
	public Date getFechapublicacion() {
		return this.fechapublicacion;
	}

	public void setFechapublicacion(Date fechapublicacion) {
		this.fechapublicacion = fechapublicacion;
	}

	@Column(name = "familia", length = 200)
	public String getFamilia() {
		return this.familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	@Column(name = "dedicatoria", length = 2000)
	public String getDedicatoria() {
		return this.dedicatoria;
	}

	public void setDedicatoria(String dedicatoria) {
		this.dedicatoria = dedicatoria;
	}

	@Column(name = "tag", length = 200)
	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Column(name = "rutafoto", length = 100)
	public String getRutafoto() {
		return this.rutafoto;
	}

	public void setRutafoto(String rutafoto) {
		this.rutafoto = rutafoto;
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
	@Column(name = "fechamodificacion",  length = 29)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petmascotahomenaje", targetEntity=Petfotomascota.class)
	public Set<?> getPetfotomascotas() {
		return this.petfotomascotas;
	}

	public void setPetfotomascotas(Set<?> petfotomascotas) {
		this.petfotomascotas = petfotomascotas;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException{
	  Petmascotahomenaje petmascotahomenaje = (Petmascotahomenaje) super.clone();
	  
	  if(petmascotahomenaje.getPetespecie() != null && petmascotahomenaje.getPetespecie().getIdespecie() > 0){
		  petmascotahomenaje.setPetespecie((Petespecie) petmascotahomenaje.getPetespecie().clone());
	  }
	  
	  return petmascotahomenaje;
	}
	
	public Petmascotahomenaje clonar() throws Exception{
		return (Petmascotahomenaje)this.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dedicatoria == null) ? 0 : dedicatoria.hashCode());
		result = prime * result + ((familia == null) ? 0 : familia.hashCode());
		result = prime
				* result
				+ ((fechafallecimiento == null) ? 0 : fechafallecimiento
						.hashCode());
		result = prime
				* result
				+ ((fechamodificacion == null) ? 0 : fechamodificacion
						.hashCode());
		result = prime * result
				+ ((fechanacimiento == null) ? 0 : fechanacimiento.hashCode());
		result = prime
				* result
				+ ((fechapublicacion == null) ? 0 : fechapublicacion.hashCode());
		result = prime * result + idmascota;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((petespecie == null) ? 0 : petespecie.getIdespecie());
		result = prime * result
				+ ((rutafoto == null) ? 0 : rutafoto.hashCode());
		result = prime * result
				+ ((setestado == null) ? 0 : setestado.getIdestado());
		result = prime * result
				+ ((setusuario == null) ? 0 : setusuario.getIdusuario());
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
		Petmascotahomenaje other = (Petmascotahomenaje) obj;
		if (dedicatoria == null) {
			if (other.dedicatoria != null)
				return false;
		} else if (!dedicatoria.equals(other.dedicatoria))
			return false;
		if (familia == null) {
			if (other.familia != null)
				return false;
		} else if (!familia.equals(other.familia))
			return false;
		if (fechafallecimiento == null) {
			if (other.fechafallecimiento != null)
				return false;
		} else if (!fechafallecimiento.equals(other.fechafallecimiento))
			return false;
		if (fechamodificacion == null) {
			if (other.fechamodificacion != null)
				return false;
		} else if (!fechamodificacion.equals(other.fechamodificacion))
			return false;
		if (fechanacimiento == null) {
			if (other.fechanacimiento != null)
				return false;
		} else if (!fechanacimiento.equals(other.fechanacimiento))
			return false;
		if (fechapublicacion == null) {
			if (other.fechapublicacion != null)
				return false;
		} else if (!fechapublicacion.equals(other.fechapublicacion))
			return false;
		if (idmascota != other.idmascota)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (petespecie == null) {
			if (other.petespecie != null)
				return false;
		} else if (petespecie.getIdespecie() != other.petespecie.getIdespecie())
			return false;
		if (rutafoto == null) {
			if (other.rutafoto != null)
				return false;
		} else if (!rutafoto.equals(other.rutafoto))
			return false;
		/*if (setestado == null) {
			if (other.setestado != null)
				return false;
		} else if (setestado.getIdestado() != other.setestado.getIdestado())
			return false;
		if (setusuario == null) {
			if (other.setusuario != null)
				return false;
		} else if (setusuario.getIdusuario() != other.setusuario.getIdusuario())
			return false;*/
		  if (setusuario.getIdusuario() != other.setusuario.getIdusuario())
			return false;*/
		return true;
	}

}
