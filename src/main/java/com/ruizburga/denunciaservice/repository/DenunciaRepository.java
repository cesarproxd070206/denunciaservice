package com.ruizburga.denunciaservice.repository;

import com.ruizburga.denunciaservice.entity.Denuncia;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

    List<Denuncia> findByDni(String dni, Sort sort);

    @Query(value = "SELECT * FROM denuncias ORDER BY id LIMIT :limite OFFSET :desplazamiento", nativeQuery = true)
    List<Denuncia> findRango(@Param("desplazamiento") int desplazamiento,
                             @Param("limite") int limite);
}
