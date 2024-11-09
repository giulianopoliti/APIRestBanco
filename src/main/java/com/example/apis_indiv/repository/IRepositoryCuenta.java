package com.example.apis_indiv.repository;

import com.example.apis_indiv.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRepositoryCuenta extends JpaRepository<Cuenta, String> {
    @Query("SELECT c FROM Cuenta c JOIN c.clientes cl WHERE cl.numero = :id")
    List<Cuenta> findByClienteId(@Param("id") int id);

}
