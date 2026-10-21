package com.example.apirestwithspring.controllers;

import com.example.apirestwithspring.entities.Pokemon;
import com.example.apirestwithspring.services.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokeApi")
public class PokemonController {

    private final PokemonService service;

    public PokemonController(PokemonService service) {
        this.service = service;
    }

    // GET na PokeAPI por nome
    @GetMapping("/pokemon/{pokemon_name}")
    public String whoIsThatPokemon(@PathVariable String pokemon_name) {
        return service.getPokemonByName(pokemon_name);
    }

    // GET lista
    @GetMapping("/pokemon")
    public String listarTodos() {
        return service.listarTodos();
    }

    // GET lista local
    @GetMapping("/locais")
    public List<Pokemon> listarLocais() {
        return service.listarLocais();
    }

    // POST
    @PostMapping("/pokemon")
    public Pokemon criar(@RequestBody Pokemon pokemon) {
        return service.criar(pokemon);
    }

    // PUT
    @PutMapping("/pokemon/{id}")
    public ResponseEntity<Pokemon> atualizar(@PathVariable Integer id, @RequestBody Pokemon pokemon) {
        Pokemon atualizado = service.atualizar(id, pokemon);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    // DELETE
    @DeleteMapping("/pokemon/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!service.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}