package com.gabriel.handyMan.models.entity;

public class Hora {

	private float horaSemana;
	private float horaNormal;
	private float horaNoche;
	private float horaDomingo;
	private float horaExtraNormal;
	private float horaExtraNoche;
	private float horaExtraDomingo;

	public Hora(float horaSemana, float horaNormal, float horaNoche, float horaDomingo, float horaExtraNormal,
			float horaExtraNoche, float horaExtraDomingo) {
		super();
		this.horaSemana = horaSemana;
		this.horaNormal = horaNormal;
		this.horaNoche = horaNoche;
		this.horaDomingo = horaDomingo;
		this.horaExtraNormal = horaExtraNormal;
		this.horaExtraNoche = horaExtraNoche;
		this.horaExtraDomingo = horaExtraDomingo;
	}

	public Hora() {
		super();
	}

	public float gethSemana() {
		return horaSemana;
	}

	public void sethSemana(float horaSemana) {
		this.horaSemana = horaSemana;
	}

	public float gethoraNormal() {
		return horaNormal;
	}

	public void sethoraNormal(float horaNormal) {
		this.horaNormal = horaNormal;
	}

	public float gethoraNoche() {
		return horaNoche;
	}

	public void sethoraNoche(float horaNoche) {
		this.horaNoche = horaNoche;
	}

	public float gethoraDomingo() {
		return horaDomingo;
	}

	public void sethoraDomingo(float horaDomingo) {
		this.horaDomingo = horaDomingo;
	}

	public float gethoraExtraNormal() {
		return horaExtraNormal;
	}

	public void sethoraExtraNormal(float horaExtraNormal) {
		this.horaExtraNormal = horaExtraNormal;
	}

	public float gethoraExtraNoche() {
		return horaExtraNoche;
	}

	public void sethoraExtraNoche(float horaExtraNoche) {
		this.horaExtraNoche = horaExtraNoche;
	}

	public float gethoraExtraDomingo() {
		return horaExtraDomingo;
	}

	public void sethoraExtraDomingo(float horaExtraDomingo) {
		this.horaExtraDomingo = horaExtraDomingo;
	}

}
