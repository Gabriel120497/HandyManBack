package com.gabriel.handyMan.models.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.handyMan.models.entity.Reporte;

public interface IReporteDao extends JpaRepository<Reporte, Long> {

	public List<Reporte> findByIdTecnico(Long idTecnico);

	Optional<Reporte> findByIdServicioAndIdTecnicoAndFechaInicioAndFechaFin(Long idServicio, Long IdTecnico, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Optional<Reporte> findByIdTecnicoAndFechaInicioAndFechaFin(Long IdTecnico, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
