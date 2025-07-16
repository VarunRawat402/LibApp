package com.example.demo.Repository;

import com.example.demo.Enums.TransactionType;
import com.example.demo.Model.Book;
import com.example.demo.Model.Student;
import com.example.demo.Model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn,Integer> {
    Txn findTopByBookAndStudentAndTransactionTypeOrderByIdDesc(
            Book book, Student student, TransactionType transactionType);
}
