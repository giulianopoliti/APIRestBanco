package com.example.apis_indiv.modelo;

import com.example.apis_indiv.exceptions.CuentaException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	private int numero;
	private String nombre;
	private String documento;
	@ManyToMany(mappedBy = "clientes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Cuenta> cuentas;


	public Cliente(String nombre, String documento, int numero) {
		this.numero = numero;
		this.nombre = nombre;
		this.documento = documento;
		this.cuentas = new ArrayList<Cuenta>();
	}

	public float saldoEnCuenta(String nroCuenta) throws CuentaException {
		for(Cuenta c : cuentas)
			if(c.soyLaCuenta(nroCuenta))
				return c.obtenerSaldo();
	    throw new CuentaException("No existe esa cuenta para ese cliente");
	}
	
	public float posicion() {
		float resultado = 0;
		for(Cuenta c : cuentas)
			resultado += c.obtenerSaldo();
		return resultado;
	}
	
	public List<Movimiento> movimientosMes(int mes, String nroCuenta) throws CuentaException{
		for(Cuenta c : cuentas)
			if(c.soyLaCuenta(nroCuenta)) {
				return c.movimientosDelMes(mes);
			}
		throw new CuentaException("No existe esa cuenta para ese cliente");
	}

	
	public boolean tieneDocumento(String documento) {
		return this.documento.equalsIgnoreCase(documento);
	}

	public boolean tieneNumero(int numero) {
		return this.numero == numero;
	}

	public void agregarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
		cuenta.agregarClienteCuenta(this);
	}
	
}
