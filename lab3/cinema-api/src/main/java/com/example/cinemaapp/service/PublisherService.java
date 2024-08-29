package com.example.cinemaapp.service;

import com.example.cinemaapp.persistence.model.Publisher;
import com.example.cinemaapp.web.dto.PublisherDto;

import java.util.List;
import java.util.Optional;

public interface PublisherService {

    List<Publisher> findAll();

    List<Publisher> findAll(int page, int size);

    Optional<Publisher> findById(Long id);

    Publisher create(PublisherDto publisher);

    Publisher update(PublisherDto publisher);

    void delete(Long id);

}
