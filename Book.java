import java.io.Serializable;

class Book implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID
    private static int nextBookId = 1;
    private int bookId;
    private String bookName;
    private String writerName;
    private double price;
    private int quantity;

    public Book(String bookName, String writerName, double price, int quantity) {
        this.bookId = nextBookId++;
        this.bookName = bookName;
        this.writerName = writerName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getWriterName() {
        return writerName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
