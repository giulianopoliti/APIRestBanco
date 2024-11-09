package com.example.apis_indiv.dao;

import com.example.apis_indiv.modelo.Cuenta;
import com.example.apis_indiv.repository.IRepositoryCuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CuentaDAO {
    @Autowired
    private IRepositoryCuenta repositoryCuenta;
    public void save (Cuenta cuenta) {
        repositoryCuenta.save(cuenta);
    }
    public Cuenta getCuentaById(String id) {
        return repositoryCuenta.findById(id).orElseThrow();
    }
    public void delete (Cuenta cuenta) {
        repositoryCuenta.delete(cuenta);
    }
    public List<Cuenta> getAllCuentas() {
        return repositoryCuenta.findAll();
    }
    public Cuenta getByClienteId(String id) {
        return repositoryCuenta.findById(id).orElseThrow();
    }
    public void updateCuentas (List<Cuenta> cuentas) {
        repositoryCuenta.saveAll(cuentas);
    }

}
