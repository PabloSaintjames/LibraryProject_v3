package com.pol.springboot.app.librarydemo.repository;


import com.pol.springboot.app.librarydemo.model.Cd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CdRepository extends JpaRepository<Cd,Long> {

    // Aquí puedes agregar consultas personalizadas si las necesitas

    //Voy a dejar el CD Repository por si quiero buscar por estilo musical.
    //List<Cd> findByEstiloMusical(String estilo);
}
