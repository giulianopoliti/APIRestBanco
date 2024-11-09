package com.example.apis_indiv.repository;

import com.example.apis_indiv.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepositoryMovimiento extends JpaRepository<Movimiento, Integer> {
    @Query("SELECT m FROM Movimiento m WHERE m.cuenta = :nroCuenta")
    List<Movimiento> findAllByNroCuenta(String nroCuenta);

    @Query("SELECT m FROM Movimiento m WHERE MONTH(m.fecha) = :mes AND m.cuenta.nroCuenta = :nroCuenta")
    List<Movimiento> findMovimientosByMesAndCuenta(@Param("mes") int mes, @Param("nroCuenta") String nroCuenta);

}
