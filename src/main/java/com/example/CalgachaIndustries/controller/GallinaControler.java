package com.example.CalgachaIndustries.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.example.CalgachaIndustries.model.Gallina;
import com.example.CalgachaIndustries.repository.GallinaRepository;

@RestController
@RequestMapping("/gallinas")
public class GallinaControler {

    private final GallinaRepository repoGallina;

    public GallinaControler(GallinaRepository repoGallina) {
        this.repoGallina = repoGallina;
    }

    @GetMapping
    public List<Gallina> obtenerGallinas() {
        return repoGallina.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallina> obtenerGallinaPorId(@PathVariable Long id) {
        return repoGallina.findById(id)
            .map(gallina -> ResponseEntity.ok(gallina))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Gallina crearGallina(@RequestBody Gallina gallina) {
        return repoGallina.save(gallina);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Gallina> actualizarGallina(@PathVariable Long id, @RequestBody Gallina gallinaActualizada) {
        return repoGallina.findById(id)
            .map(gallina -> {
                if (gallinaActualizada.getNombre() != null) {
                    gallina.setNombre(gallinaActualizada.getNombre());
                }
                if (gallinaActualizada.getEdad() > 0) {
                    gallina.setEdad(gallinaActualizada.getEdad());
                }
                if (gallinaActualizada.getRaza() != null) {
                    gallina.setRaza(gallinaActualizada.getRaza());
                }
                if (gallinaActualizada.getDescripcion() != null) {
                    gallina.setDescripcion(gallinaActualizada.getDescripcion());
                }
                return ResponseEntity.ok(repoGallina.save(gallina));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGallina(@PathVariable Long id) {
        if (repoGallina.existsById(id)) {
            repoGallina.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    
}
