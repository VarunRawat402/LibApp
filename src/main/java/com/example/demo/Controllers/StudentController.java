package com.example.demo.Controllers;

import com.example.demo.Model.Book;
import com.example.demo.Model.Student;
import com.example.demo.Model.Txn;
import com.example.demo.Model.User;
import com.example.demo.Request.StudentRequest;
import com.example.demo.Service.StudentService;
import com.example.demo.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.SeekableByteChannel;
import java.util.List;

@RestController
@Tag(name="Student APIs",description = "Create/Get/Delete/GetTxns of Student")
public class StudentController {

    @Autowired
    StudentService ss;

    @Autowired
    UserService userService;

    //Create Student
    @PostMapping("/student")
    @Operation(summary = "Create a Student")
    public String createStudent(@RequestBody @Valid StudentRequest studentRequest){
        ss.create(studentRequest);
        return "Your account has been created successfully.";
    }

    //Check Own Details
    @GetMapping("/student")
    @Operation(summary = "Get Your own Details")
    public Student getStudent() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int studentId = user.getStudent().getId();
        return ss.findStudentById(studentId);
    }

    //To check the details of any student in the DB ( need to pass studentId )
    @GetMapping("/admin/student/{studentId}")
    @Operation(summary = "Get Student By Id")
    public Student getStudentForAdmin(@PathVariable int studentId){
        return ss.findStudentById(studentId);
    }

    //To check the books issued by the Student (own) ( No need to pass anything )
    @GetMapping("/student/books")
    @Operation(summary = "Get all your issued Books")
    public List<Book> getBooks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int studentId = user.getStudent().getId();
        Student student = ss.findStudentById(studentId);
        return student.getBookList();
    }

    //To check the txn done by the student (own) ( No need to pass anything )
    @GetMapping("/student/txn")
    @Operation(summary = "Get all your transactions")
    public List<Txn> getTxn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int studentId = user.getStudent().getId();
        Student student = ss.findStudentById(studentId);
        return student.getTxnList();
    }

    //Delete the student (own)
    @DeleteMapping("/student/self")
    @Operation(summary = "Delete your account")
    public String deleteSelf(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int studentId = user.getStudent().getId();
        ss.delete(studentId);
        userService.delete(user.getId());
        return "Student has been deleted Successfully";
    }

    //Delete any student ( Need to pass the student ID )
    @DeleteMapping("/admin/student/{studentId}")
    @Operation(summary = "Delete Student By Id")
    public String deleteStudent(@PathVariable int studentId) throws Exception {
        Student student = ss.findStudentById(studentId);
        int userId = student.getUser().getId();
        ss.delete(studentId);
        userService.delete(userId);
        return "Student has been deleted Successfully";
    }
}
