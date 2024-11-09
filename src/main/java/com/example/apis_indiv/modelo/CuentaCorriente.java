package com.example.apis_indiv.modelo;

import com.example.apis_indiv.exceptions.CuentaException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import java.util.Calendar;

@NoArgsConstructor

@Entity
@Table(name="cuentas")
@DiscriminatorValue("CUENTA_CORRIENTE")
public class CuentaCorriente extends Cuenta {

	private float descubiertoHabilitado;
	private float costoMantenimiento;
	private float tasaDiariaDescubierto;

	public CuentaCorriente(Cliente cliente,float descubiertoHabilitado, float costoMantenimiento, float tasaDiariaDescubierto) {
		super(cliente);
		this.descubiertoHabilitado = descubiertoHabilitado;
		this.costoMantenimiento = costoMantenimiento;
		this.tasaDiariaDescubierto = tasaDiariaDescubierto;
	}

	@Override
	public Movimiento extraer(float importe) throws CuentaException {
		if(this.saldo + this.descubiertoHabilitado > importe) {
			this.saldo -= importe;
			Movimiento movimiento = new Movimiento(Calendar.getInstance().getTime(), "Extraccion", importe);
			this.movimientos.add(movimiento);
			return movimiento;
		}
		else
			throw new CuentaException("El saldo es insuficiente");
	}

	@Override
	public Movimiento depositar(float importe) {
		return super.depositar(importe);
	}

	@Override
	public float disponible() {
		return this.saldo + this.descubiertoHabilitado;
	}

	public float getDescubiertoHabilitado() {
		return descubiertoHabilitado;
	}

	public float getCostoMantenimiento() {
		return costoMantenimiento;
	}

	public float getTasaDiariaDescubierto() {
		return tasaDiariaDescubierto;
	}

}
