package com.example.apis_indiv.dao;

import com.example.apis_indiv.modelo.Movimiento;
import com.example.apis_indiv.repository.IRepositoryMovimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MovimientoDAO {
    @Autowired
    private IRepositoryMovimiento repositoryMovimiento;

    public void save (Movimiento movimiento) {
        repositoryMovimiento.save(movimiento);
    }
    public void delete (Movimiento movimiento) {
        repositoryMovimiento.delete(movimiento);
    }
    public void deleteById (int id) {
        repositoryMovimiento.deleteById(id);
    }
    public Optional<Movimiento> getMovimientoById(int id) {
        return repositoryMovimiento.findById(id);
    }
    public List<Movimiento> getAllMovimientosByNroCuenta(String nroCuenta) {
        return repositoryMovimiento.findAllByNroCuenta(nroCuenta);
    }
    public List<Movimiento> getMovimientosByMesAndNroCuenta(int mes, String nroCuenta) {
        return repositoryMovimiento.findMovimientosByMesAndCuenta(mes, nroCuenta);
    }
}
