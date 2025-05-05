import models.Book;
import models.BookService;
import models.Member;

public class MainLibrary {

    public static void main(String[] args) {
        Book book = new Book("Test 1", "Mega auteur");
        Book book2 = new Book("Test 2", "Mega auteur2");

        System.out.println(book);
        System.out.println(book2);

        BookService bookService = new BookService();
        bookService.addItem(book);
        bookService.addItem(book2);

        Member member = new Member("Moi");
        bookService.borrowItem(book, member);

        System.out.println("Location");
        System.out.println(book);
        System.out.println(book2);

        System.out.println("Rendre le livre");
        bookService.returnItem(book);
        System.out.println(book);

        System.out.println("Reservation");
        bookService.reserveItem(book, member);
        System.out.println(book);

        System.out.println("Cancel Reservation");
        bookService.cancelReservation(book);
        System.out.println(book);

        System.out.println("Voir tous les livres");
        System.out.println(bookService);

    }
}
