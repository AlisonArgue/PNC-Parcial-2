package com.example.demo.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Data
@Getter
public class UpdateBookDto {

    private UUID id;
    private String newTitle;
    private String newLanguage;


}
