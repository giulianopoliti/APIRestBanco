package com.example.apis_indiv.controlador;

import com.example.apis_indiv.dao.ClienteDAO;
import com.example.apis_indiv.dao.CuentaDAO;
import com.example.apis_indiv.dao.MovimientoDAO;
import com.example.apis_indiv.exceptions.CuentaException;
import com.example.apis_indiv.modelo.*;
import com.example.apis_indiv.modelo.requests.CuentaRequest;
import com.example.apis_indiv.modelo.requests.MovimientoRequest;
import com.example.apis_indiv.views.MovimientoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controlador {
	@Autowired
	private ClienteDAO clienteDAO;
	@Autowired
	private CuentaDAO cuentaDAO;
	@Autowired
	private MovimientoDAO movimientoDAO;

	@PostMapping("/crearCuenta") // funca ok
	public ResponseEntity<String> crearCuenta(@RequestBody CuentaRequest cuentaRequest) {
		Cliente cliente = clienteDAO.getClienteByDocumento(cuentaRequest.getDocumento());
		if (cliente != null) {
			if (cuentaRequest.getTipoCuenta().equals("Cuenta Corriente")) {
				cuentaDAO.save(new CuentaCorriente(cliente, 50000f, 1500f, 0.023f));
			} else {
				cuentaDAO.save(new CajaAhorro(cliente));
			}
			return ResponseEntity.ok("Cuenta creada correctamente");
		}
		return ResponseEntity.badRequest().body("Cliente no encontrado");
	}

	@PostMapping("/crearCliente") // funca ok
	public ResponseEntity<String> crearCliente(@RequestBody Cliente cliente) {
		if (!clienteDAO.existsByDocumento(cliente.getDocumento())) {
			clienteDAO.save(new Cliente(cliente.getNombre(), cliente.getDocumento(), clienteDAO.findMaxNumero()+1));
			return ResponseEntity.ok("Cliente " + cliente.getNombre() +" creado correctamente");
		} else {
			return ResponseEntity.badRequest().body("El cliente ya existe");
		}
	}

	@PostMapping("/agregarCuentaCliente")
	public ResponseEntity<String> agregarCuentaCliente(@RequestParam String documento, @RequestParam String nroCuenta) {
		Cliente cliente = clienteDAO.getClienteByDocumento(documento);
		Cuenta cuenta = cuentaDAO.getCuentaById(nroCuenta);
		if (cliente != null && cuenta != null) {
			cliente.agregarCuenta(cuenta);
			clienteDAO.save(cliente);
			return ResponseEntity.ok("Cuenta agregada al cliente correctamente");
		}
		return ResponseEntity.badRequest().body("Cliente o cuenta no encontrados");
	}

	@PutMapping("/depositar")
	public ResponseEntity<Integer> depositar(@RequestBody MovimientoRequest depositoRequest) {
		Cuenta cuenta = cuentaDAO.getCuentaById(depositoRequest.getNroCuenta());
		if (cuenta != null) {
			Movimiento movimiento = cuenta.depositar(depositoRequest.getImporte());
			movimientoDAO.save(movimiento);
			return ResponseEntity.ok(movimiento.getNroMovimiento());
		}
		return ResponseEntity.badRequest().body(-1);
	}

	@PutMapping("/extraer")
	public ResponseEntity<Integer> extraer(@RequestBody MovimientoRequest movimientoRequest) {
		Cuenta cuenta = cuentaDAO.getCuentaById(movimientoRequest.getNroCuenta());
		if (cuenta != null) {
			try {
				Movimiento movimiento = cuenta.extraer(movimientoRequest.getImporte());
				movimientoDAO.save(movimiento);
				return ResponseEntity.ok(movimiento.getNroMovimiento());
			} catch (CuentaException e) {
				return ResponseEntity.badRequest().body(0);
			}
		}
		return ResponseEntity.badRequest().body(-1);
	}

	@GetMapping("/disponible") // http://localhost:8080/api/disponible?nroCuenta=2c896410-9499-4862-9b61-5d0aef21a85c
	public ResponseEntity<Float> disponible(@RequestParam String nroCuenta) {
		Cuenta cuenta = cuentaDAO.getCuentaById(nroCuenta);
		if (cuenta != null) {
			return ResponseEntity.ok(cuenta.disponible());
		}
		return ResponseEntity.badRequest().body(0f);
	}

	@GetMapping("/posicion") //http://localhost:8080/api/posicion?numero=1
	public ResponseEntity<Float> posicion(@RequestParam int numero) {
		Cliente cliente = clienteDAO.getClienteById(numero);
		if (cliente != null) {
			return ResponseEntity.ok(cliente.posicion());
		}
		return ResponseEntity.badRequest().body(0f);
	}

	@GetMapping("/saldoEnCuenta") // http://localhost:8080/api/saldoEnCuenta?nroCuenta=2c896410-9499-4862-9b61-5d0aef21a85c
	public ResponseEntity<Float> saldoEnCuenta(@RequestParam String nroCuenta) {
		Cuenta cuenta = cuentaDAO.getCuentaById(nroCuenta);
		if (cuenta != null) {
			return ResponseEntity.ok(cuenta.obtenerSaldo());
		}
		return ResponseEntity.badRequest().body(0f);
	}

	@GetMapping("/movimientos") // http://localhost:8080/api/movimientos?nroCuenta=2c896410-9499-4862-9b61-5d0aef21a85c&mes=0
	public ResponseEntity<List<MovimientoView>> obtenerMovimientos(@RequestParam String nroCuenta, @RequestParam int mes) {
		Cuenta cuenta = cuentaDAO.getCuentaById(nroCuenta);
		if (cuenta != null) {
			List<MovimientoView> movimientoViews = movimientoDAO.getMovimientosByMesAndNroCuenta(mes, nroCuenta).stream().map(Movimiento::toView).toList();
			return ResponseEntity.ok(movimientoViews);
		}
		return ResponseEntity.badRequest().body(new ArrayList<>());
	}

	@DeleteMapping("/eliminarCliente")
	@Transactional
	public ResponseEntity<String> eliminarCliente(@RequestParam String documento) {
		Cliente cliente = clienteDAO.getClienteByDocumento(documento);
		if (cliente != null) {
			// elimino relaciones tabla intermedia
			cliente.getCuentas().forEach(cuenta -> cuenta.getClientes().remove(cliente));

			// limpio la lista de cuentas del cliente
			cliente.getCuentas().clear();

			// elimino el cliente de la base de datos
			clienteDAO.deleteById(cliente.getNumero());

			return ResponseEntity.ok("Cliente " + cliente.getNombre() + " eliminado correctamente");
		}
		return ResponseEntity.badRequest().body("Cliente no encontrado");
	}


}