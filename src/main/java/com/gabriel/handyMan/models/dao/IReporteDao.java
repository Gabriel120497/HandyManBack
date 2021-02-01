package com.gabriel.handyMan.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.handyMan.models.entity.Reporte;

public interface IReporteDao extends JpaRepository<Reporte, Long> {

	public List<Reporte> findByIdTecnico(Long idTecnico);

}
