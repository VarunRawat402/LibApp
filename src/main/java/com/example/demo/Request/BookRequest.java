package com.example.demo.Request;

import com.example.demo.Enums.Genre;
import com.example.demo.Model.Author;
import com.example.demo.Model.Book;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookRequest {

    private String name;
    private Genre genre;
    private int cost;

    @NotNull
    private Author author;

    public Book to(ModelMapper modelMapper){
        return modelMapper.map(this,Book.class);
    }
}
