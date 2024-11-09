package com.example.apis_indiv.dao;

import com.example.apis_indiv.modelo.Cliente;
import com.example.apis_indiv.modelo.Cuenta;
import com.example.apis_indiv.repository.IRepositoryCliente;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteDAO {
    @Autowired
    private IRepositoryCliente repositoryCliente;
    public List<Cliente> getAllClientes() {
        return repositoryCliente.findAll();
    }
    public void save (Cliente cliente) {
        repositoryCliente.save(cliente);
    }
    public void delete (Cliente cliente) {
        repositoryCliente.delete(cliente);
    }
    public void deleteById (int id) {
        repositoryCliente.deleteById(id);
    }

    public Cliente getClienteById(int id) {
        return repositoryCliente.findById(id).orElseThrow();
    }

    public Cliente getClienteByNombre (String nombre) {
        return repositoryCliente.findByNombre(nombre);
    }
    public Cliente getClienteByDocumento (String documento) {
        return repositoryCliente.findByDocumento(documento);
    }
    public boolean existsByDocumento (String documento) {
        return repositoryCliente.findByDocumento(documento) != null;
    }
    public int findMaxNumero() {
        return repositoryCliente.findMaxNumero();
    }
}
