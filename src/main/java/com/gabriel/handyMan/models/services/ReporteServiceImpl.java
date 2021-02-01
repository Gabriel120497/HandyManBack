package com.gabriel.handyMan.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.handyMan.models.dao.IReporteDao;
import com.gabriel.handyMan.models.entity.Reporte;

@Service
public class ReporteServiceImpl implements IReporteService {

	@Autowired
	private IReporteDao reporteDao;

	@Override
	@Transactional
	public Reporte save(Reporte reporte) {
		return reporteDao.save(reporte);
	}

	@Override
	public List<Reporte> findByIdTecnico(Long idTecnico) {
		return reporteDao.findByIdTecnico(idTecnico);
	}

}
