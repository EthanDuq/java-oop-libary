package models;

public interface Reservable {
    void reserveItem(Book book, Member member);
    void cancelReservation(Book book);
}
