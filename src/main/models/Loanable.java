package models;

public interface Loanable {
    void borrowItem(Book book, Member member);
    void returnItem(Book book);
}
