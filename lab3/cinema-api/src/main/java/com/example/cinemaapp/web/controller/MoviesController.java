package com.example.cinemaapp.web.controller;

import com.example.cinemaapp.client.FluentdClient;
import com.example.cinemaapp.persistence.model.Movies;
import com.example.cinemaapp.service.MoviesService;
import com.example.cinemaapp.web.dto.MoviesDto;
import com.example.cinemaapp.web.dto.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.example.cinemaapp.client.LogMapper.createIdMap;
import static com.example.cinemaapp.client.LogMapper.createPage;

@RestController
@RequestMapping("movies")
public class MoviesController {

    private static final String MOVIES_TAG = "movies";

    private final MoviesService moviesService;
    private final FluentdClient fluentdClient;

    @Autowired
    private MoviesController(MoviesService moviesService, FluentdClient fluentdClient) {
        this.moviesService = moviesService;
        this.fluentdClient = fluentdClient;
    }

    @GetMapping
    public ResponseEntity<List<MoviesDto>> getAllMovies(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size) {
        List<MoviesDto> moviesDtos;
        if (page != null && size != null) {
            if (page <= 0 || size <= 0) {
                return ResponseEntity.badRequest().build();
            }
            moviesDtos = moviesService.findAll(page, size).stream()
                    .map(DtoMapper::convertToDto)
                    .toList();
        } else {
            moviesDtos = moviesService.findAll().stream()
                    .map(DtoMapper::convertToDto)
                    .toList();
        }
        fluentdClient.send(MOVIES_TAG, createPage("Request list of Movies", page, size));
        return ResponseEntity.ok(moviesDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoviesDto> getMovies(@PathVariable Long id) {
        ResponseEntity<MoviesDto> response = moviesService.findById(id)
                .map(movies -> ResponseEntity.ok(DtoMapper.convertToDto(movies)))
                .orElseGet(() -> ResponseEntity.notFound().build());
        fluentdClient.send(MOVIES_TAG, createIdMap(id, String.format("Movies with id '%s' was create", id)));
        return response;
    }

    @PostMapping
    public ResponseEntity<MoviesDto> createPublisher(@Valid @RequestBody MoviesDto moviesDto) {
        Movies movies = moviesService.create(moviesDto);
        Long id = movies.getId();
        fluentdClient.send(MOVIES_TAG, createIdMap(id, String.format("Movies with id '%s' was create", id)));
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.convertToDto(movies));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoviesDto> updatePublisher(@PathVariable Long id, @Valid @RequestBody MoviesDto moviesDto) {
        moviesService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        moviesDto.setId(id);
        Movies updatedmovies = moviesService.update(moviesDto);
        fluentdClient.send(MOVIES_TAG, createIdMap(id, String.format("Movies with id '%s' was updated", id)));
        return ResponseEntity.ok(DtoMapper.convertToDto(updatedmovies));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovies(@PathVariable Long id) {
        moviesService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        moviesService.delete(id);
        fluentdClient.send(MOVIES_TAG, createIdMap(id, String.format("Movies with id '%s' deleted", id)));
        return ResponseEntity.noContent().build();
    }

}
