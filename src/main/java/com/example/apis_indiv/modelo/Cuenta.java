package com.example.apis_indiv.modelo;

import java.time.LocalDateTime;
import java.util.*;

import com.example.apis_indiv.exceptions.CuentaException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cuentas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cuenta", discriminatorType = DiscriminatorType.STRING)
public abstract class Cuenta {
	@Id
	@Column(name = "nro_cuenta")
	protected String nroCuenta;
	protected float saldo;
	@JoinTable(
			name = "cuenta_cliente",
			joinColumns = @JoinColumn(name = "nro_cuenta"),
			inverseJoinColumns = @JoinColumn(name = "numero")
	)
	@ManyToMany(cascade = CascadeType.ALL)
	protected List<Cliente> clientes;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta")
	protected List<Movimiento> movimientos;

	
	public Cuenta(Cliente cliente) {
		this.nroCuenta = UUID.randomUUID().toString();
		this.saldo = 0;
		this.clientes = new ArrayList<Cliente>();
		this.clientes.add(cliente);
		this.movimientos = new ArrayList<Movimiento>();
	}

	public void agregarClienteCuenta(Cliente cliente){
		this.clientes.add(cliente);
	}
	
	public Movimiento depositar(float importe) {
		if(importe > 0) {
			this.saldo += importe;
			Movimiento movimiento = new Movimiento(Calendar.getInstance().getTime(), "Deposito", importe);
			movimientos.add(movimiento);
			return movimiento;
		}
		else
			return null;
	}
	
	public float obtenerSaldo() {
		return this.saldo;
	} 
	
	public abstract Movimiento extraer(float importe) throws CuentaException, CuentaException;
	
	public abstract float disponible();
	
	public List<Movimiento> verDepositosEntreFechas(Date fechaDesde, Date fechaHasta){
		List<Movimiento> resultado = new ArrayList<Movimiento>();
		for(Movimiento m : movimientos)
			if(m.soyDeposito())
				resultado.add(m);
		return resultado;
	}
	
	public List<Movimiento> verExtraccionesEntreFechas(Date fechaDesde, Date fechaHasta){
		List<Movimiento> resultado = new ArrayList<Movimiento>();
		for(Movimiento m : movimientos)
			if(!m.soyDeposito())
				resultado.add(m);
		return resultado;
	}
	
	public boolean soyLaCuenta(String nroCuenta) {
		return this.nroCuenta.equalsIgnoreCase(nroCuenta);
	}

	public List<Movimiento> movimientosDelMes(int mes) {
		List<Movimiento> movimientosFiltrados = new ArrayList<>();
		LocalDateTime ahora = LocalDateTime.now();
		for (Movimiento movimiento : this.movimientos) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(movimiento.getFecha());
			int mesMovimiento = cal.get(Calendar.MONTH);
			int anoMovimiento = cal.get(Calendar.YEAR);

			if (mesMovimiento == mes && anoMovimiento == ahora.getYear()) {
				movimientosFiltrados.add(movimiento);
			}
		}
		return movimientosFiltrados;
	}

	public boolean tieneNumero(String nroCuenta) {
		return this.nroCuenta.equalsIgnoreCase(nroCuenta);
	}
}
