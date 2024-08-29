package com.example.cinemaapp.service.impl;

import com.example.cinemaapp.persistence.model.Publisher;
import com.example.cinemaapp.persistence.repository.MoviesRepository;
import com.example.cinemaapp.persistence.repository.PublisherRepository;
import com.example.cinemaapp.service.PublisherService;
import com.example.cinemaapp.web.dto.DtoMapper;
import com.example.cinemaapp.web.dto.PublisherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final MoviesRepository moviesRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, MoviesRepository moviesRepository) {
        this.publisherRepository = publisherRepository;
        this.moviesRepository = moviesRepository;
    }

    @Override
    public List<Publisher> findAll() {
        Iterator<Publisher> publishers = publisherRepository.findAll().iterator();
        List<Publisher> publisherList = new ArrayList<>();
        while (publishers.hasNext()) {
            publisherList.add(publishers.next());
        }
        return publisherList;
    }

    @Override
    public List<Publisher> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Iterator<Publisher> publishers = publisherRepository.findAll(pageRequest).iterator();
        List<Publisher> publisherList = new ArrayList<>();
        while (publishers.hasNext()) {
            publisherList.add(publishers.next());
        }
        return publisherList;
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }

    @Override
    public Publisher create(PublisherDto publisherDto) {
        Publisher publisher = DtoMapper.convertToEntity(publisherDto);
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher update(PublisherDto publisherDto) {
        Long id = publisherDto.getId();
        if (findById(id).isEmpty()) {
            throw new IllegalArgumentException(String.format("Publisher with id %s does not exist", id));
        }
        Publisher publisher = DtoMapper.convertToEntity(publisherDto);
        return publisherRepository.save(publisher);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Publisher> publisher = this.findById(id);
        if (publisher.isPresent()) {
            moviesRepository.deleteAll(moviesRepository.findByPublisher(publisher.get()));
            publisherRepository.deleteById(id);
        }
    }

}
