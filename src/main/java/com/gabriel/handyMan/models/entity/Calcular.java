package com.gabriel.handyMan.models.entity;

public class Calcular {
	float hSemana = 0, hNormal = 0, hNoche = 0, hDomingo = 0, hExtraNormal = 0, hExtraNocheH = 0, hExtraDomingo = 0,
			auxNormal = 0, auxNoche = 0, auxDomingo = 0, maxH = 48;
	Boolean flag = true;

	

	public Calcular(float hSemana, float hNormal, float hNoche, float hDomingo, float hExtraNormal, float hExtraNocheH,
			float hExtraDomingo, float auxNormal, float auxNoche, float auxDomingo, float maxH, Boolean flag) {
		super();
		this.hSemana = hSemana;
		this.hNormal = hNormal;
		this.hNoche = hNoche;
		this.hDomingo = hDomingo;
		this.hExtraNormal = hExtraNormal;
		this.hExtraNocheH = hExtraNocheH;
		this.hExtraDomingo = hExtraDomingo;
		this.auxNormal = auxNormal;
		this.auxNoche = auxNoche;
		this.auxDomingo = auxDomingo;
		this.maxH = maxH;
		this.flag = flag;
	}

	public Calcular() {
	}

	public float gethSemana() {
		return hSemana;
	}

	public void sethSemana(float hSemana) {
		this.hSemana = hSemana;
	}

	public float gethNormal() {
		return hNormal;
	}

	public void sethNormal(float hNormal) {
		this.hNormal = hNormal;
	}

	public float gethNoche() {
		return hNoche;
	}

	public void sethNoche(float hNoche) {
		this.hNoche = hNoche;
	}

	public float gethDomingo() {
		return hDomingo;
	}

	public void sethDomingo(float hDomingo) {
		this.hDomingo = hDomingo;
	}

	public float gethExtraNormal() {
		return hExtraNormal;
	}

	public void sethExtraNormal(float hExtraNormal) {
		this.hExtraNormal = hExtraNormal;
	}

	public float gethExtraNocheH() {
		return hExtraNocheH;
	}

	public void sethExtraNocheH(float hExtraNocheH) {
		this.hExtraNocheH = hExtraNocheH;
	}

	public float gethExtraDomingo() {
		return hExtraDomingo;
	}

	public void sethExtraDomingo(float hExtraDomingo) {
		this.hExtraDomingo = hExtraDomingo;
	}

	public float getAuxNormal() {
		return auxNormal;
	}

	public void setAuxNormal(float auxNormal) {
		this.auxNormal = auxNormal;
	}

	public float getAuxNoche() {
		return auxNoche;
	}

	public void setAuxNoche(float auxNoche) {
		this.auxNoche = auxNoche;
	}

	public float getAuxDomingo() {
		return auxDomingo;
	}

	public void setAuxDomingo(float auxDomingo) {
		this.auxDomingo = auxDomingo;
	}

	public float getMaxH() {
		return maxH;
	}

	public void setMaxH(float maxH) {
		this.maxH = maxH;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
