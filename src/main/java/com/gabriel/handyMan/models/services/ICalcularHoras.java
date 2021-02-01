package com.gabriel.handyMan.models.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.gabriel.handyMan.models.entity.Calcular;
import com.gabriel.handyMan.models.entity.Hora;
import com.gabriel.handyMan.models.entity.HoraExtra;
import com.gabriel.handyMan.models.entity.Reporte;

public interface ICalcularHoras {

	Hora calcular(List<Reporte> reportes, int numeroSemana);

	// logica para el calculo de horas trabajadas
	Calcular calcularHorasTrabajadas(LocalDateTime fechaInicio, LocalDateTime fechaFin, float hNormal, float hNoche,
			float hDomingo, float auxNormal, float auxNoche, float auxDomingo, float hSemana, boolean flag, float maxH);

	// para las horas extra en un solo turno
	HoraExtra horaExtraTipo1(float hSemana, float aux, float maxH, float horas);

	// para las horas extra con cambio de turno
	HoraExtra horaExtraTipo2(float hSemana, float aux, float aux2, float maxH, float horas);

	Float formatToH(LocalDateTime dateTime, LocalTime time);
}
