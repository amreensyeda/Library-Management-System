package BookSpehere2;

import java.io.Serializable;

class IssueBook implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID
    private int bookId;
    private int userId;

    public IssueBook(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }
}
