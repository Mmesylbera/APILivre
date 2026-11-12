package org.sylbera.apilivres.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sylbera.apilivres.models.Livre;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/Livres")
@Tag(name="Livres", description="Gestion d'une petite bibliotheque")
public class LivreController {

    private final List<Livre> livres = new ArrayList<>(List.of(
            new Livre(1L, "Clean Code", "Robert C. Martin"),
            new Livre(2L, "Effective Java", "Joshua Bloch")
    ));

    @Operation(summary = "Lister tous les livres")
    @GetMapping
    public  List<Livre> lister() {
        return livres;
    }

    @Operation(summary = "Trouver un livres par son id")
    @ApiResponse(responseCode = "200", description = "Livre trouver")
    @ApiResponse(responseCode = "404", description = "Livre introuvable", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<Livre> trouver(
            @Parameter(description = "Identifiant du livre", example = "1") @PathVariable Long id
    ) {
        return  livres.stream()
                .filter(l -> l.id().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @Operation(summary = "Ajouter un livre")
    @ApiResponse(responseCode = "201", description = "livre creé")
    @PostMapping
    public ResponseEntity<Livre> ajouter(@RequestBody Livre livre) {
        Livre nouveau = new Livre((long) livres.size() + 1, livre.titre(), livre.auteur());
        livres.add(nouveau);
        return  ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
    }

}

