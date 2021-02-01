package com.gabriel.handyMan.models.entity;

public class HoraExtra {
	private float horas;
	private float aux;

	public HoraExtra() {
		super();
	}

	public HoraExtra(float horas, float aux) {
		super();
		this.horas = horas;
		this.aux = aux;
	}

	public float getHoras() {
		return horas;
	}

	public void setHoras(float horas) {
		this.horas = horas;
	}

	public float getAux() {
		return aux;
	}

	public void setAux(float aux) {
		this.aux = aux;
	}

}
