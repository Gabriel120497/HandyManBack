package com.gabriel.handyMan.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.handyMan.models.entity.Reporte;
import com.gabriel.handyMan.models.services.IReporteService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ReporteRestController {

	@Autowired
	private IReporteService reporteService;

	@PostMapping("/reportes")
	public ResponseEntity<?> create(@Valid @RequestBody Reporte reporte, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Reporte nuevoReporte = null;

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			nuevoReporte = reporteService.save(reporte);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error guardando en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El reporte se guardó con éxito.");
		response.put("reporte", nuevoReporte);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
