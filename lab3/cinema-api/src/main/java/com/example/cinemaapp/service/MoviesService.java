package com.example.cinemaapp.service;

import com.example.cinemaapp.persistence.model.Movies;
import com.example.cinemaapp.persistence.model.Publisher;
import com.example.cinemaapp.web.dto.MoviesDto;

import java.util.List;
import java.util.Optional;

public interface MoviesService {

    List<Movies> findAll();

    List<Movies> findAll(int page, int size);

    List<Movies> findByPublisher(Publisher publisher);

    Optional<Movies> findById(Long id);

    Movies create(MoviesDto moviesDto);

    Movies update(MoviesDto moviesDto);

    void delete(Long id);

}
