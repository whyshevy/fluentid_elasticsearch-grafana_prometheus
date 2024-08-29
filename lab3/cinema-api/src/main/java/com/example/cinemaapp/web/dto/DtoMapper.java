package com.example.cinemaapp.web.dto;

import com.example.cinemaapp.persistence.model.Movies;
import com.example.cinemaapp.persistence.model.Publisher;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static Publisher convertToEntity(PublisherDto publisherDto) {
        return Publisher.builder()
                .id(publisherDto.getId())
                .name(publisherDto.getName())
                .build();
    }

    public static PublisherDto convertToDto(Publisher publisher) {
        return PublisherDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }

    public static Movies convertToEntity(MoviesDto moviesDto) {
        return Movies.builder()
                .id(moviesDto.getId())
                .title(moviesDto.getTitle())
                .author(moviesDto.getAuthor())
                .isbn(moviesDto.getIsbn())
                .year(moviesDto.getYear())
                .publisher(Publisher.builder()
                        .id(moviesDto.getPublisherId())
                        .build())
                .build();
    }

    public static MoviesDto convertToDto(Movies movies) {
        return MoviesDto.builder()
                .id(movies.getId())
                .title(movies.getTitle())
                .author(movies.getAuthor())
                .isbn(movies.getIsbn())
                .year(movies.getYear())
                .publisherId(movies.getPublisher() == null ? null : movies.getPublisher().getId())
                .build();
    }

}
