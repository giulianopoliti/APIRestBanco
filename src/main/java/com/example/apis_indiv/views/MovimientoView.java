package com.example.apis_indiv.views;

import java.io.Serializable;
import java.util.Date;

public class MovimientoView implements Serializable{

	private static final long serialVersionUID = 139014362110072106L;
	private int nroMovimiento;
	private Date fecha;
	private String tipoMovimiento;
	private float importe;
	
	public MovimientoView(int nroMovimiento, Date fecha, String tipoMovimiento, float importe) {
		this.nroMovimiento = nroMovimiento;
		this.fecha = fecha;
		this.tipoMovimiento = tipoMovimiento;
		this.importe = importe;
	}

	public MovimientoView() {}

	public int getNroMovimiento() {
		return nroMovimiento;
	}

	public void setNroMovimiento(int nroMovimiento) {
		this.nroMovimiento = nroMovimiento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

}
