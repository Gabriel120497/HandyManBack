package com.gabriel.handyMan.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.handyMan.models.entity.Hora;
import com.gabriel.handyMan.models.entity.Reporte;
import com.gabriel.handyMan.models.services.ICalcularHoras;
import com.gabriel.handyMan.models.services.IReporteService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CalcularHorasController {
	
	@Autowired
	private IReporteService reporteService;
	@Autowired
	private ICalcularHoras horasTrabajadas;
	
	@GetMapping("/calcularHoras")
	public ResponseEntity<?> calcular(@Valid @RequestParam(value = "tecnicoId") long id, @RequestParam(value = "numeroSemana") int semana) {
		Map<String, Object> response = new HashMap<>();
		List<Reporte> reportesTecnico = reporteService.findByIdTecnico(id);
		Hora resp = horasTrabajadas.calcular(reportesTecnico, semana);
		response.put("horas", resp);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
