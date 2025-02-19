package com.ubbcluj.book.persistence;

import com.ubbcluj.book.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT td FROM BookEntity td WHERE td.assignedTo.username = :username")
    List<BookEntity> findByAssignedTo(String username);

}
