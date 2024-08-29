package com.example.cinemaapp.service.impl;

import com.example.cinemaapp.persistence.model.Movies;
import com.example.cinemaapp.persistence.model.Publisher;

import com.example.cinemaapp.persistence.repository.MoviesRepository;
import com.example.cinemaapp.service.MoviesService;
import com.example.cinemaapp.service.PublisherService;
import com.example.cinemaapp.web.dto.MoviesDto;
import com.example.cinemaapp.web.dto.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepository moviesRepository;
    private final PublisherService publisherService;

    @Autowired
    private MoviesServiceImpl(MoviesRepository moviesRepository, PublisherService publisherService) {
        this.moviesRepository = moviesRepository;
        this.publisherService = publisherService;
    }


    @Override
    public List<Movies> findAll() {
        Iterator<Movies> moviess = moviesRepository.findAll().iterator();
        List<Movies> moviesList = new ArrayList<>();
        while (moviess.hasNext()) {
            moviesList.add(moviess.next());
        }
        return moviesList;
    }

    @Override
    public List<Movies> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Iterator<Movies> moviess = moviesRepository.findAll(pageRequest).iterator();
        List<Movies> moviesList = new ArrayList<>();
        while (moviess.hasNext()) {
            moviesList.add(moviess.next());
        }
        return moviesList;
    }

    @Override
    public List<Movies> findByPublisher(Publisher publisher) {
        Iterator<Movies> moviess = moviesRepository.findByPublisher(publisher).iterator();
        List<Movies> moviesList = new ArrayList<>();
        while (moviess.hasNext()) {
            moviesList.add(moviess.next());
        }
        return moviesList;
    }

    @Override
    public Optional<Movies> findById(Long id) {
        return moviesRepository.findById(id);
    }

    @Override
    public Movies create(MoviesDto moviesDto) {
        Long publisherId = moviesDto.getPublisherId();
        if (publisherService.findById(publisherId).isEmpty()) {
            throw new IllegalArgumentException(String.format("Publisher with id %s does not exist", publisherId));
        }
        Movies movies = DtoMapper.convertToEntity(moviesDto);
        return moviesRepository.save(movies);
    }

    @Override
    public Movies update(MoviesDto moviesDto) {
        Long publisherId = moviesDto.getPublisherId();
        if (publisherService.findById(publisherId).isEmpty()) {
            throw new IllegalArgumentException(String.format("Publisher with id %s does not exist", publisherId));
        }
        Long id = moviesDto.getId();
        if (!moviesRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Movies with id %s does not exist", id));
        }
        Movies movies = DtoMapper.convertToEntity(moviesDto);
        return moviesRepository.save(movies);
    }

    @Override
    public void delete(Long id) {
        moviesRepository.deleteById(id);
    }

}
