INSERT INTO genres (genre_name) VALUES ('Драма');
INSERT INTO authors (author_name) VALUES ('Достоевский');
INSERT INTO books (`name`,`author_id`,`genre_id`) values ('Преступление и наказание',1,1);
INSERT INTO comments (`comment_text`,`book_id`) values ('Очень крутая книжка, есть о чем подумать',1);
INSERT INTO comments (`comment_text`,`book_id`) values ('Плакал с первых страниц',1);

INSERT INTO genres (genre_name) VALUES ('Роман');
INSERT INTO authors (author_name) VALUES ('М.Булгаков');
INSERT INTO books (`name`,`author_id`,`genre_id`) values ('Мастер и Маргарита',2,2);

