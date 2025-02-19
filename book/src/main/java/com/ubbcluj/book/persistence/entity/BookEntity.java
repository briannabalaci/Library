package com.ubbcluj.book.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ubbcluj.book.persistence.entity.enums.BookStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonBackReference
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    @JsonBackReference
    private UserEntity assignedTo;
}
