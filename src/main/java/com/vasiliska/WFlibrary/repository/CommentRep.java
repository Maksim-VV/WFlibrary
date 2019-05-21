package com.vasiliska.WFlibrary.repository;


import com.vasiliska.WFlibrary.domain.Book;
import com.vasiliska.WFlibrary.domain.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRep extends CrudRepository<Comment, Long> {

    @Query("SELECT d FROM Comment d WHERE d.book.bookId = :bookId")
    List<Comment> getCommentByBook(@Param(value = "bookId") Long bookId);

}
