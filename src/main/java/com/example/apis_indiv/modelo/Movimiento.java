package com.example.apis_indiv.modelo;

import com.example.apis_indiv.views.MovimientoView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Calendar;


import java.util.Date;
@NoArgsConstructor
@Entity
@Getter
@Table(name = "movimientos")
public class Movimiento {
	private static int numerador = 0;
	@Id
	@Column(name = "nro_movimiento")
	private int nroMovimiento;
	private Date fecha;
	private String tipoMovimiento;
	private float importe;
	@ManyToOne
	@JoinColumn(name = "nro_cuenta")
	private Cuenta cuenta;
	
	public Movimiento(Date fecha, String tipoMovimiento, float importe) {
		this.nroMovimiento = numerador++;
		this.fecha = fecha;
		this.tipoMovimiento = tipoMovimiento;
		this.importe = importe;
	}

	public int getNroMovimiento() {
		return this.nroMovimiento;
	}

	public boolean soyDeEseMes(int mes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.fecha);
		return cal.get(Calendar.MONTH) == mes;
	}
	public boolean soyDeposito() {
		return this.tipoMovimiento.equalsIgnoreCase("Deposito");
	}
	
	public float obtenerImporte() {
		return this.importe;
	}

	public MovimientoView toView() {
		return new MovimientoView(nroMovimiento,fecha, tipoMovimiento, importe);
	}
}
