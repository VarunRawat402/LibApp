package com.example.demo.Controllers;

import com.example.demo.Exceptions.TxnException;
import com.example.demo.Service.TxnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Transaction APIs",description = "Create a Transaction for issue and return of books")
public class TxnController {
    @Autowired
    TxnService ts;

    //Issue the Book
    //Need to pass Student ID and Book ID
    @PostMapping("/txn/issue/{bookId}")
    @Operation(summary = "Issue a Book")
    public String issueTxn(@PathVariable int bookId) throws TxnException {
        return ts.issueTxn(bookId);
    }

    //Return the Book
    //Need to pass Student ID and Book ID
    @PostMapping("/txn/return/{bookId}")
    @Operation(summary = "Return the Book")
    public String returnTxn(@PathVariable int bookId) throws TxnException {
        return ts.returnTxn(bookId);
    }
}
