package com.gabriel.handyMan.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reportes")
public class Reporte implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "La identificación del técnico es obligatoria")
	@Positive(message = "La identificación del técnico debe ser mayor que 0")
	private Long idTecnico;

	@NotNull(message = "La identificación del servicio es obligatoria")
	@Positive(message = "La identificación del servicio debe ser mayor que 0")
	private Long idServicio;

	@DateTimeFormat(pattern = "yyyy-mm-dd hh:mm")
	@NotNull(message = "La fecha inicio del servicio es obligatoria")
	@Column(name = "fecha_inicio")
	private LocalDateTime fechaInicio;

	@DateTimeFormat(pattern = "yyyy-mm-dd hh:mm")
	@NotNull(message = "La fecha fin del servicio es obligatoria")
	@Column(name = "fecha_fin")
	private LocalDateTime fechaFin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(Long idTecnico) {
		this.idTecnico = idTecnico;
	}

	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	private static final long serialVersionUID = 1L;

}
