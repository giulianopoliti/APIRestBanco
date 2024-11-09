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
@DiscriminatorValue("CAJA_AHORRO")
public class CajaAhorro extends Cuenta {
	private float tasaInteres;
	
	public CajaAhorro(Cliente cliente) {
		super(cliente);
		this.tasaInteres = 1.8f;
	}

	@Override
	public Movimiento extraer(float importe) throws CuentaException {
		if(this.saldo > importe) {
			this.saldo -= importe;
			Movimiento movimiento = new Movimiento(Calendar.getInstance().getTime(), "Extraccion", importe);
			this.movimientos.add(movimiento);
			return movimiento;
		}
		else
			throw new CuentaException("Saldo Insuficiente");
	}

	@Override
	public float disponible() {
		return this.saldo;
	}

	@Override
	public Movimiento depositar (float importe) {
		return super.depositar(importe);
	}

	public float getTasaInteres() {
		return tasaInteres;
	}

}
