package com.example.demo.Model;

import com.example.demo.Enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;

    //No contact can be same and contact cannot be null
    @Column(unique = true, nullable = false)
    private String contact;

    //No Email can be same
    @Column(unique = true)
    private String email;

    //Account Status { Active, Inactive }
    @Enumerated(value = EnumType.STRING)
    private AccountStatus accountStatus;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    //Getting all the books for this student
    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("student")
    private List<Book> bookList;

    //Getting all the txn for this student
    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("student")
    private List<Txn> txnList;

    @OneToOne
    @JoinColumn
    //This annotation will add userId as a foreign key in the Student table
    //why we added userId in the student table and not vice versa
    //Because If we added studentId in the User table then student needed to be created first before user
    //and that's not possible first user will be created then student/admin
    @JsonIgnoreProperties({"student", "admin", "password"})
    private User user;
}
