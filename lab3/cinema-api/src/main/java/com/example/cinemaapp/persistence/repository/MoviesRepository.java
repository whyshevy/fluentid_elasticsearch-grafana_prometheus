package com.example.cinemaapp.persistence.repository;

import com.example.cinemaapp.persistence.model.Movies;
import com.example.cinemaapp.persistence.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

    List<Movies> findByPublisher(Publisher publisher);

}
