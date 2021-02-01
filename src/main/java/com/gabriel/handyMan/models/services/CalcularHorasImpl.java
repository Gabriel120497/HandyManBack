package com.gabriel.handyMan.models.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gabriel.handyMan.models.entity.Calcular;
import com.gabriel.handyMan.models.entity.Hora;
import com.gabriel.handyMan.models.entity.HoraExtra;
import com.gabriel.handyMan.models.entity.Reporte;

@Service
public class CalcularHorasImpl implements ICalcularHoras {

	@Override
	public Hora calcular(List<Reporte> reportes, int numeroSemana) {
		// Instanciamos un objeto Calculate para almacenar los calculos de las horas
		Calcular respCalculate = new Calcular();

		// Para cada servicio vamos a calcular las horas trabajadas
		reportes.stream().forEach(reporte -> {
			var fechaInicio = reporte.getFechaInicio();
			var fechaFin = reporte.getFechaFin();

			// numero de la semana del año usando java.time
			int semanaAnioInicio = fechaInicio.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
			int semanaAnioFin = fechaFin.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

			// si la hora de inicio y hora fin están en la misma semana
			if (semanaAnioInicio == numeroSemana && semanaAnioFin == numeroSemana) {

				// Instanciamos un objeto para la respusta de cada servicio
				Calcular resp;
				// Si las horas semanales son menores al maximo de horas semanales definidas
				if (respCalculate.gethSemana() < respCalculate.getMaxH()) {// Calculamos horas normales

					resp = calcularHorasTrabajadas(fechaInicio, fechaFin, respCalculate.gethNormal(),
							respCalculate.gethNoche(), respCalculate.gethDomingo(), respCalculate.getAuxNormal(),
							respCalculate.getAuxNoche(), respCalculate.getAuxDomingo(), respCalculate.gethSemana(),
							respCalculate.getFlag(), respCalculate.getMaxH());

					respCalculate.sethSemana(resp.gethSemana());
					respCalculate.sethNormal(resp.gethNormal());
					respCalculate.sethNoche(resp.gethNoche());
					respCalculate.sethDomingo(resp.gethDomingo());
					respCalculate.sethExtraNormal(resp.getAuxNormal());
					respCalculate.sethExtraNocheH(resp.getAuxNoche());
					respCalculate.sethExtraDomingo(resp.getAuxDomingo());

				} else {
					// Se calculan solo las horas extras
					respCalculate.setFlag(false);
					resp = calcularHorasTrabajadas(fechaInicio, fechaFin, respCalculate.gethExtraNormal(),
							respCalculate.gethExtraNocheH(), respCalculate.gethExtraDomingo(),
							respCalculate.getAuxNormal(), respCalculate.getAuxNoche(), respCalculate.getAuxDomingo(),
							respCalculate.gethSemana(), respCalculate.getFlag(), respCalculate.getMaxH());

					respCalculate.sethSemana(resp.gethSemana());
					respCalculate.sethExtraNormal(resp.getAuxNormal());
					respCalculate.sethExtraNocheH(resp.getAuxNoche());
					respCalculate.sethExtraDomingo(resp.getAuxDomingo());
				}

				// si empieza el servicio Domingo y termina un Lunes de la otra semana
			} else if (semanaAnioInicio == numeroSemana && semanaAnioFin > numeroSemana) {

				// obtenemos las horas del domingo
				float onlySunH = 24 - formatoEnHoras(fechaFin, null); // convertimos todo a horas para operar

				// Si las horas semanales son menores al máximo de horas semanales definidas
				if (respCalculate.gethSemana() < respCalculate.getMaxH()) {

					// acumulamos las horas del domingo
					respCalculate.sethDomingo(respCalculate.gethDomingo() + onlySunH);
					// acumulamos el total de horas en la semana
					respCalculate.sethSemana(respCalculate.gethSemana() + onlySunH);

				} else {
					// Horas extras dominicales
					respCalculate.sethExtraDomingo(respCalculate.gethExtraDomingo() + onlySunH);
					// Horas en la semana
					respCalculate.sethSemana(respCalculate.gethSemana() + onlySunH);
				}

			}

		});

		// RESPUESTA FINAL DEL TOTAL DE CÁLCULOS
		Hora totalWH = new Hora(respCalculate.gethSemana(), respCalculate.gethNormal(), respCalculate.gethNoche(),
				respCalculate.gethDomingo(), respCalculate.gethExtraNormal(), respCalculate.gethExtraNocheH(),
				respCalculate.gethExtraDomingo());

		return totalWH;
	}

	@Override
	public Calcular calcularHorasTrabajadas(LocalDateTime fechaInicio, LocalDateTime fechaFin, float hNormal,
			float hNoche, float hDomingo, float auxNormal, float auxNoche, float auxDomingo, float hSemana,
			boolean flag, float maxH) {
		// Definimos horarios
		LocalTime noI = LocalTime.of(7, 0, 0), noF = LocalTime.of(20, 0, 0), niF = LocalTime.from(noI),
				niI = LocalTime.from(noF);

		// Formateamos todo a horas
		float normI = formatoEnHoras(null, noI), normF = formatoEnHoras(null, noF), nightI = formatoEnHoras(null, niI),
				nightF = formatoEnHoras(null, niF);

		// Transformamos la fecha de inicio y fin en horas
		float startH = formatoEnHoras(fechaInicio, null), endH = formatoEnHoras(fechaFin, null);

		var hours = endH - startH; // Horas de trabajo en cada servicio

		if (fechaInicio.getDayOfWeek().getValue() > 0 && fechaFin.getDayOfWeek().getValue() > 0) { // Lunes a Sabado

			// Horario normal
			if ((startH >= normI && startH < normF) && (endH > normI && endH <= normF)) { /// 7AM a 8 PM
				hNormal = hNormal + hours;
				hSemana = hSemana + hours; // aumentamos las horas semanales

				if (flag) {// significa que las semanas no habian llegado a 48 pero pudieron llegarlo en
							// esta iteracion, entoces se confirma y si es positivo se efectua la operación
							// de horas extras
					var resp = horaExtraTipo1(hSemana, auxNormal, maxH, hNormal);
					auxNormal = resp.getAux();
					hNormal = resp.getHoras();
				}

			} else if ((startH >= normI && startH < normF) && endH >= nightI) { // empezó hora normal y termino en
																				// nocturna del mismo dia

				// 7AM a 8PM + 8pm a 11pm
				// separamos las horas normales de las nocturnas
				hNormal = hNormal + (nightI - startH);
				hSemana = hSemana + (nightI - startH);

				if (flag) {
					var resp = horaExtraTipo1(hSemana, auxNormal, maxH, hNormal);
					auxNormal = resp.getAux();
					hNormal = resp.getHoras();
				}

				// Normal Nocturnas
				hNoche = hNoche + (endH - nightI);
				hSemana = hSemana + (endH - nightI);

				if (flag) {
					var resp = horaExtraTipo2(hSemana, auxNormal, auxNoche, maxH, hNoche);
					auxNoche = resp.getAux();
					hNoche = resp.getHoras();
				}

			} else if ((startH >= normI && startH < normF) && endH <= nightF) { // empezó hora hormal y terminó hora
																				// nocturna del dia siguiente de la
																				// misma semana
				// 7AM a 8PM + 8pm a 12am + 12am a 7am
				// separamos las horas normales de las nocturnas
				hNormal = hNormal + (nightI - startH);// normales
				hSemana = hSemana + (nightI - startH);

				if (flag) {
					var resp = horaExtraTipo1(hSemana, auxNormal, maxH, hNormal);
					auxNormal = resp.getAux();
					hNormal = resp.getHoras();
				}

				// Normal Nocturnas
				hNoche = hNoche + (4 + endH); // se suman las primeras nocturnas (8pm hasta las 12pm) y de 12am hasta
												// las 7am;
				hSemana = hSemana + (4 + endH);

				if (flag) {
					var resp = horaExtraTipo2(hSemana, auxNormal, auxNoche, maxH, hNoche);
					auxNoche = resp.getAux();
					hNoche = resp.getHoras();
				}
			}

			else if ((startH >= nightI && endH > nightI) || (startH >= nightI && endH == 0)) {
				// Hora nocturna de 8pm a 12am
				if (hours < 0) {
					hNoche = hNoche + (24 + hours);
					hSemana = hSemana + (24 + hours);

					if (flag) {
						var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
						auxNoche = resp.getAux();
						hNoche = resp.getHoras();
					}

				} else {
					hNoche = hNoche + hours;
					hSemana = hSemana + hours;
					if (flag) {
						var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
						auxNoche = resp.getAux();
						hNoche = resp.getHoras();
					}
				}

			} else if (startH >= nightI && endH <= nightF) {
				// de 8pm a 7am
				hNoche = hNoche + (24 - startH) + (endH); // de 8pm a 12am + de 12am a 7am
				hSemana = hSemana + (24 - startH) + (endH);
				if (flag) {
					var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
					auxNoche = resp.getAux();
					hNoche = resp.getHoras();
				}
			}

			else if (startH >= nightI && (endH >= normI && endH <= nightI)) {
				// de 8pm a 8pm del dia sig
				hNoche = hNoche + (24 - startH) + 7; // horas nocturnas del dia anterior mas todas del siguiente dia
				hSemana = hSemana + (24 - startH) + 7;

				if (flag) {
					var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
					auxNoche = resp.getAux();
					hNoche = resp.getHoras();
				}

				hNormal = hNormal + (endH - 7); // las horas normales del dia siguiente
				hSemana = hSemana + (endH - 7);
				if (flag) {
					var resp = horaExtraTipo2(hSemana, auxNoche, auxNormal, maxH, hNormal);
					auxNormal = resp.getAux();
					hNormal = resp.getHoras();
				}

			} else if ((startH >= 0 && startH <= normI) && (endH > 0 && endH <= normI)) { // inició y termino de 12 am a
																							// 7 am del mismo dia
				// Nocturnas de 12am a 7am
				hNoche = hNoche + hours;
				hSemana = hSemana + hours;
				if (flag) {
					var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
					auxNoche = resp.getAux();
					hNoche = resp.getHoras();
				}
			} else if ((startH >= 0 && startH <= normI) && (endH > normI && endH <= normF)) {
				// inició de 12 am y terminó al dia siguiente en hora normal nocturnas
				hNoche = hNoche + (nightF - startH);
				hSemana = hSemana + (nightF - startH);

				if (flag) {
					var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
					auxNoche = resp.getAux();
					hNoche = resp.getHoras();
				}

				// normales
				hNormal = hNormal + (endH - normI);
				hSemana = hSemana + (endH - normI);
				if (flag) {
					var resp = horaExtraTipo2(hSemana, auxNoche, auxNormal, maxH, hNormal);
					auxNormal = resp.getAux();
					hNormal = resp.getHoras();
				}

			} else if ((startH >= 0 && startH <= normI) && (endH >= nightI || endH == 0)) {
				hNoche = hNoche + (nightF - startH);// nocturnas de 12am hasta 7am
				hSemana = hSemana + (nightF - startH);

				if (flag) {
					var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
					auxNoche = resp.getAux();
					hNoche = resp.getHoras();
				}

				hNormal = hNormal + (nightI - normI); // 7am a 8pm
				hSemana = hSemana + (nightI - normI);

				if (flag) {
					var resp = horaExtraTipo2(hSemana, auxNoche, auxNormal, maxH, hNormal);
					auxNormal = resp.getAux();
					hNormal = resp.getHoras();
				}

				// las nocturnas de 8pm a 12am
				if (endH == 0) {
					hNoche = hNoche + 4;
					hSemana = hSemana + 4;

					if (flag) {
						var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
						auxNoche = resp.getAux();
						hNoche = resp.getHoras();
					}

				} else {
					hNoche = hNoche + (endH - nightI);
					hSemana = hSemana + (endH - nightI);
					if (flag) {
						var resp = horaExtraTipo1(hSemana, auxNoche, maxH, hNoche);
						auxNoche = resp.getAux();
						hNoche = resp.getHoras();
					}
				}

			}

		} else {
			// Domingos cuando la hora fin finaliza ,(misma semana)
			hDomingo = hDomingo + hours;
			hSemana = hSemana + hours;

			if (flag) {
				var resp = horaExtraTipo1(hSemana, auxDomingo, maxH, hDomingo);
				auxDomingo = resp.getAux();
				hDomingo = resp.getHoras();

			}
		}

		Calcular calcResp = new Calcular(hSemana, hNormal, hNoche, hDomingo, 0, 0, 0, auxNormal, auxNoche, auxDomingo,
				0, null);

		return calcResp;

	}

	@Override
	public Float formatoEnHoras(LocalDateTime dateTime, LocalTime time) {
		if (dateTime != null) {
			return ((float) (dateTime.getHour()) + (float) (dateTime.getMinute() / 60)
					+ (float) (dateTime.getSecond() / 3600));
		}

		return ((float) (time.getHour()) + (float) (time.getMinute() / 60) + (float) (time.getSecond() / 3600));
	}

	@Override
	public HoraExtra horaExtraTipo1(float hSemana, float aux, float maxH, float horas) {
		if (hSemana > maxH) {
			aux = hSemana - maxH; // obtenemos las extras
			horas = horas - aux; // le resto las extras a las normales
			return new HoraExtra(horas, aux);
		}
		return new HoraExtra(horas, aux);
	}

	@Override
	public HoraExtra horaExtraTipo2(float hSemana, float aux, float aux2, float maxH, float horas) {
		if ((hSemana > maxH) && aux == 0) {
			aux2 = hSemana - maxH; // obtenemos las extras
			horas = horas - aux2; // le resto las extras a las normales

			return new HoraExtra(horas, aux2);
		} else if ((hSemana > maxH) && aux > 0) {
			// solo se tienen en cuenta las extras
			aux2 = hSemana - aux - maxH; // las semanas menos las extras anteriores menos el tope(48)
			return new HoraExtra(horas, aux2);
		}
		// los devuelve como estaban
		return new HoraExtra(horas, aux2);
	}

}
