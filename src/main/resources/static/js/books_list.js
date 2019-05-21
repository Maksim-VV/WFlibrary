$(document).ready(function () {
    listAllBooks();
    var book = {};
    var url = "";
    var method = "";
    $('#btnAddBook').click(function () {
        book.bookId = $('#bookId').val();
        book.bookName = $('#bookName').val();
        book.authorName = $('#authorName').val();
        book.genreName = $('#genreName').val();
        var bookObj = JSON.stringify(book);
        if (validateForm(book.authorName) || validateForm(book.bookName) || validateForm(book.genreName)) {
            if (book.bookId) {
                url = "/books/" + book.bookId;
                method = 'PUT';
            } else {
                url = "/books";
                method = 'POST'
            }
            $.ajax({
                url: url,
                method: method,
                data: bookObj,
                contentType: 'application/json; charset=utf-8',
                success: function () {
                    listAllBooks();
                    reset();
                },
                error: function (error) {
                    alert(error);
                }
            })
        }
        else {
            alert('Не все поля заполнены!');
            return;
        }

    });
});

function validateForm(s) {
    s = s.replace(/^\s+|\s+$/g, '')
    if (!s) {
        return false;
    }

    return true;
}

function listAllBooks() {
    $.get('/books').done(function (books) {
        $('#booksTableBody').empty();
        books.forEach(function (book) {
            $('#booksTableBody').append(`
                    <tr>
                        <td>${book.bookId}</td>
                        <td>${book.bookName}</td>
                        <td>${book.author.authorName}</td>
                        <td>${book.genre.genreName}</td>
                        <td><button onclick="updateBook(${book.bookId})">Изменить</button></td>
                        <td><button onclick="deleteBook(${book.bookId})">Удалить</button></td>
                    </tr>
                `)
        });
    })
}

function deleteBook(bookId) {
    $.ajax({
        url: '/books/' + bookId,
        method: 'DELETE',
        success: function () {
            listAllBooks();
        },
        error: function (error) {
            alert(error);
        }
    })
}

function updateBook(bookId) {
    $.ajax({
        url: '/books/' + bookId,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#bookId').val(data.bookId);
            $('#bookName').val(data.bookName);
            $('#authorName').val(data.author.authorName);
            $('#genreName').val(data.genre.genreName);
            document.getElementById("authorName").disabled = true;
            document.getElementById("genreName").disabled = true;
        },
        error: function (error) {
            alert(error);
        }
    })
}

function reset() {
    $('#bookId').val('');
    $('#bookName').val('');
    $('#authorName').val('');
    $('#genreName').val('');
    document.getElementById("authorName").disabled = false;
    document.getElementById("genreName").disabled = false;
}
