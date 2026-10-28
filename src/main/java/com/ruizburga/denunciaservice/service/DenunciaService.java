package com.ruizburga.denunciaservice.service;

import com.ruizburga.denunciaservice.entity.Denuncia;
import com.ruizburga.denunciaservice.repository.DenunciaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DenunciaService {

    private final DenunciaRepository repository;

    public DenunciaService(DenunciaRepository repository) {
        this.repository = repository;
    }

    public Denuncia crear(Denuncia denuncia) {
        denuncia.setId(null);
        if (denuncia.getEstado() == null) {
            denuncia.setEstado(1);
        }
        return repository.save(denuncia);
    }

    @Transactional(readOnly = true)
    public List<Denuncia> listar(int offset, int limit) {
        if (offset < 0 || limit < 1) {
            throw new IllegalArgumentException("offset debe ser >= 0 y limit debe ser > 0");
        }
        return repository.findRango(offset, limit);
    }

    @Transactional(readOnly = true)
    public Optional<Denuncia> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Denuncia> buscarPorDni(String dni) {
        return repository.findByDni(dni, Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Denuncia> actualizar(Long id, Denuncia datos) {
        return repository.findById(id).map(denuncia -> {
            denuncia.setDni(datos.getDni());
            denuncia.setFecha(datos.getFecha());
            denuncia.setTipo(datos.getTipo());
            denuncia.setDireccion(datos.getDireccion());
            denuncia.setDescripcion(datos.getDescripcion());
            denuncia.setEstado(datos.getEstado() == null ? denuncia.getEstado() : datos.getEstado());
            return repository.save(denuncia);
        });
    }
}
