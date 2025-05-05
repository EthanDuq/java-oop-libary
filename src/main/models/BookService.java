package models;

import enums.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService implements LibraryService<Book>, Loanable, Reservable{
    private List<Book> books = new ArrayList<Book>();

    @Override
    public void addItem(Book item) {
        if (books.contains(item) || item == null) {
            throw new IllegalArgumentException("Book does not exist or is already added");
        }
        books.add((item));
    }

    @Override
    public void removeItem(Book book) {
        books.removeIf(b -> b.equals(book));
    }

    @Override
    public Book getItem(int id) {
        Optional<Book> book = books.stream()
                                   .filter(b -> b.isId(id))
                                   .findFirst();
        Book bookToGet = book.get();
        if (bookToGet == null) {
            throw new IllegalArgumentException("Book does not exist");
        }
        return bookToGet;
    }

    @Override
    public List<Book> getItemByName(String name) {
        List<Book> booksByName = new ArrayList<>();
        books.stream().filter(book -> book.getTitle().equals(name))
                      .forEach((book) -> {
                          booksByName.add(book);
                          System.out.println(book);
                      });
        return booksByName;
    }

    @Override
    public List<Book> getItemByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        books.stream().filter(book -> book.getAuthor().equals(author))
                      .forEach((book) -> {
                         booksByAuthor.add(book);
                         System.out.println(book);
                      });
        return booksByAuthor;
    }

    @Override
    public List<Book> getItems() {
        return new ArrayList<>(books);
    }

    @Override
    public void borrowItem(Book book, Member member) {
        if (member != null && member.borrow(book)){
            System.out.println("Borrowed book : " + book.getTitle());
            book.setStatus(BookStatus.BORROWED);
            book.setBorrowedBy(member);
        }else{
            System.out.println("Can't borrow book : " + book.getTitle());
        }
    }

    @Override
    public void returnItem(Book book) {
        if (book.isBorrowed()){
            Member member = book.getBorrowedBy();
            if (member != null && member.returnBook(book)){
                System.out.println("Returned book : " + book.getTitle());
                book.setBorrowedBy(null);
                book.setStatus(BookStatus.AVAILABLE);
            }else{
                System.out.println("Can't return book : " + book.getTitle());
            }
        }
    }

    @Override
    public void reserveItem(Book book, Member member) {
        if (member != null && member.reserveBook(book)) {
            System.out.println("Reserved book : " + book.getTitle());
            book.setStatus(BookStatus.RESERVED);
            book.setReservedBy(member);
        }else{
            System.out.println("Can't reserve book : " + book.getTitle());
        }
    }

    @Override
    public void cancelReservation(Book book) {
        if (book.isReserved()) {
            Member member = book.getReservedBy();
            if (member != null && member.cancelReserve(book)) {
                System.out.println("Cancel reservation book : " + book.getTitle());
                book.setStatus(BookStatus.AVAILABLE);
                book.setReservedBy(null);
            }else{
                System.out.println("Can't cancel reservation book : " + book.getTitle());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BookService {\n");
        sb.append("  Total books: ").append(books.size()).append("\n");

        // Statistiques par statut
        sb.append("  Book status summary:\n");
        Map<BookStatus, Long> statusCount = books.stream()
                .collect(Collectors.groupingBy(Book::getStatus, Collectors.counting()));

        statusCount.forEach((status, count) ->
                sb.append("    ").append(status).append(": ").append(count).append("\n"));

        // Derniers livres ajoutés (3 max pour éviter des logs trop longs)
        sb.append("  Recent books:\n");
        books.stream()
                .limit(3)
                .forEach(book -> sb.append("    - ").append(book.getTitle())
                        .append(" (").append(book.getId()).append(")\n"));

        sb.append("}");
        return sb.toString();
    }
}
