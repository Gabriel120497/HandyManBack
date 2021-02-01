package com.gabriel.handyMan.models.services;

import java.util.List;

import com.gabriel.handyMan.models.entity.Reporte;

public interface IReporteService {
	
	public List<Reporte> findByIdTecnico(Long idTecnico);
	public Reporte save(Reporte reporte);
	public Reporte getReport(Reporte serviceReport);
	public Reporte getReportByTecnicoAndDate(Reporte serviceReport);

}
