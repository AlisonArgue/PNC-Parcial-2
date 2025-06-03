package com.example.demo.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
@Getter
public class SaveBookDto {
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private String genre;
    private String description;
    private Integer pages;
}
