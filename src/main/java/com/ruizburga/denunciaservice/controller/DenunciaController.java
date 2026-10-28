package com.ruizburga.denunciaservice.controller;

import com.ruizburga.denunciaservice.entity.Denuncia;
import com.ruizburga.denunciaservice.service.DenunciaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@Validated
@RequestMapping("/denuncias")
public class DenunciaController {

    private final DenunciaService service;

    public DenunciaController(DenunciaService service) {
        this.service = service;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Denuncia> crear(@Valid @RequestBody Denuncia denuncia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(denuncia));
    }

    @GetMapping({"", "/"})
    public List<Denuncia> listar(@RequestParam(defaultValue = "0") @Min(0) int offset,
                                 @RequestParam(defaultValue = "5") @Positive int limit) {
        return service.listar(offset, limit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ciudadano/{dni}")
    public List<Denuncia> buscarPorDni(@PathVariable String dni) {
        return service.buscarPorDni(dni);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Denuncia> actualizar(@PathVariable Long id,
                                                @Valid @RequestBody Denuncia denuncia) {
        return service.actualizar(id, denuncia).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
