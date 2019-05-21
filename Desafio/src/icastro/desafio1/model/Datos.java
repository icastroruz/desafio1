package icastro.desafio1.model;

import java.util.Date;
import java.util.List;

public class Datos {
	
	private Integer id;
	private Date fechaCreacion;
	private Date fechaFin;
	private List<Date> fehas;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public List<Date> getFehas() {
		return fehas;
	}
	public void setFehas(List<Date> fehas) {
		this.fehas = fehas;
	}
	
	

}
