package com.vasiliska.WFlibrary.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "01", id = "initiator", author = "addAuthorities")
    public void insert(DB db) {
        DBCollection myCollection = db.getCollection("books");

        BasicDBObject author1 = new BasicDBObject().append("lastName", "Достоевский").append("firstName", "Федор");
        BasicDBObject author2 = new BasicDBObject().append("lastName", "Булгаков").append("firstName", "Михаил");

        BasicDBObject genre1 = new BasicDBObject().append("name", "Драма");
        BasicDBObject genre2 = new BasicDBObject().append("name", "Роман");

        BasicDBObject c1 = new BasicDBObject().append("text", "Очень крутая книжка, есть о чем подумать");
        BasicDBObject c2 = new BasicDBObject().append("text", "Плакал с первых страниц");

        BasicDBObject book1 = new BasicDBObject().append("_id", "111")
                .append("name", "Преступление и наказание")
                .append("author", author1)
                .append("genre", genre1)
                .append("comments", new BasicDBObject[]{c1});

        BasicDBObject book2 = new BasicDBObject().append("_id", "112")
                .append("name", "Мастер и Маргарита")
                .append("author", author2)
                .append("genre", genre2)
                .append("comments", new BasicDBObject[]{c2});

        myCollection.insert(book1, book2);
    }
}
