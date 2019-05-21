package com.vasiliska.WFlibrary.repository;


import com.vasiliska.WFlibrary.domain.Author;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRep extends CrudRepository<Author, Long> {

    void delete(Author author);

    @Query("SELECT a FROM Author a  WHERE a.authorName = :name")
    Author getAuthorByName(@Param(value = "name") String name);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Author b SET b.authorName = :authorName WHERE b.authorId = :authorId")
    int updateAuthorNameById(@Param(value = "authorId") Long id, @Param(value = "authorName") String newAuthorName);
}
