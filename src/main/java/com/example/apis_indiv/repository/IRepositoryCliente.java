package com.example.apis_indiv.repository;

import com.example.apis_indiv.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRepositoryCliente extends JpaRepository<Cliente, Integer> {
    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    Cliente findByNombre(String nombre);

    @Query("SELECT c FROM Cliente c WHERE c.documento = :documento")
    Cliente findByDocumento(String documento);
    @Query("SELECT COALESCE(MAX(c.numero), 0) FROM Cliente c")
    int findMaxNumero();
}
