package com.example.demo.Service;

import com.example.demo.Enums.TransactionType;
import com.example.demo.Exceptions.TxnException;
import com.example.demo.Model.Book;
import com.example.demo.Model.Student;
import com.example.demo.Model.Txn;
import com.example.demo.Model.User;
import com.example.demo.Repository.TxnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {

    @Autowired
    StudentService ss;

    @Autowired
    BookService bs;

    @Autowired
    TxnRepository tr;

    @Value("${book.return.due_date}")
    int due_date;

    public String issueTxn(int bID) throws TxnException {

        int sId = getStudentId();
        Student student = ss.findStudentById(sId);

        Book book = bs.findBookById(bID);
        if(book==null) throw new TxnException("Book is not present in the Library");
        if(book.getStudent()!=null) throw new TxnException("Book is already issued by other student");

        Txn txn = Txn.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .cost(book.getCost())
                .transactionType(TransactionType.ISSUE)
                .student(student)
                .book(book)
                .build();

        tr.save(txn);

        //Setting the student to the book
        book.setStudent(student);
        //saving the book again to update the details
        bs.create(book);
        return txn.getExternalTxnId();
    }

    public String returnTxn(int bID) throws TxnException {

        /*
        Student who is returning should be valid
        Check if this book is issued to this student only
        Calculate the Fine
        Create a Txn for the return
        Make the Book available again for next student
         */

        int sId = getStudentId();
        Student student = ss.findStudentById(sId);

        Book book = bs.findBookById(bID);
        if (book == null) throw new TxnException("Book is not present in the Library");
        if (book.getStudent().getId() != sId) throw new TxnException("Book is not issued to this student");

        Txn issueTxn = tr.findTopByBookAndStudentAndTransactionTypeOrderByIdDesc(book, student, TransactionType.ISSUE);

        Txn transaction = Txn.builder()
                .transactionType(TransactionType.RETURN)
                .externalTxnId(UUID.randomUUID().toString())
                .book(book)
                .student(student)
                .cost(calculateFine(issueTxn))
                .build();

        tr.save(transaction);
        book.setStudent(null);
        bs.create(book);
        return transaction.getExternalTxnId();

    }

    private double calculateFine(Txn issueTxn){
        long issueTime = issueTxn.getTxnDate().getTime();
        long returnTime = System.currentTimeMillis();

        long diff = returnTime - issueTime;
        long daysPassed = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);


        if(daysPassed >= due_date) {
            return (daysPassed - due_date) * 1.0;
        }

        return 0.0;
    }

    int getStudentId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getStudent().getId();
    }
}
