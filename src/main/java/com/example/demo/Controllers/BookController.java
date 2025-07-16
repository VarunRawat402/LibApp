package com.example.demo.Controllers;

import com.example.demo.Enums.BookFilter;
import com.example.demo.Model.Book;
import com.example.demo.Request.BookRequest;
import com.example.demo.Service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name="Book APIs",description = "Create/Get/Delete Books")
public class BookController {

    @Autowired
    BookService bs;

    //Create Book
    //Need to pass BookRequest and Author Details in the postman
    @PostMapping("/admin/book")
    @Operation(summary = "Add the Book")
    public String createBook(@RequestBody @Valid BookRequest bookRequest){
        bs.create(bookRequest);
        return "The book has been successfully added to the library.";
    }

    //To get the details of the book by Book ID
    @GetMapping("/book/{id}")
    @Operation(summary = "Get Book By Id")
    public Book getBookById(@PathVariable int id){
        return bs.findBookById(id);
    }

    //Delete the book
    @DeleteMapping("/admin/book/{id}")
    @Operation(summary = "Delete Book By Id")
    public String deleteBook(@PathVariable int id){
        bs.delete(id);
        return "The book with ID " + id + " has been successfully removed from the library.";
    }

    //To get the List of Books based on filter
    //Need to pass filter and the value of the filter
    //Ex - Genre --> Romance
    @GetMapping("/book/filter")
    public List<Book> getBookByFilter(@RequestParam("filter") BookFilter bookFilter, @RequestParam("value") String value){
        return bs.find(bookFilter,value);
    }
}
