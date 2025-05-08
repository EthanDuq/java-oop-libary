package models;

import enums.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Book {
    public static int idSuivante = 0;
    private final int id;
    private final String title;
    private final String author;

    private BookStatus status;
    private Member borrowedBy;
    private final List<Member> reservedMembers = new ArrayList<Member>();

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.id = idSuivante;
        idSuivante++;

        // New book
        status = BookStatus.AVAILABLE;
        borrowedBy = null;
    }

    public void addReservedMember(Member member) {
        reservedMembers.add(member);
    }

    public void removeReservedMember(Member member) {
        reservedMembers.remove(member);
    }

    public boolean isReservedMember() {
        return reservedMembers.isEmpty();
    }

    public Member getBorrowedBy() {
        return borrowedBy;
    }

    public Member getReservedBy() {
        return reservedMembers.get(0);
    }

    public void setBorrowedBy(Member borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public boolean isId(int id) {
        return this.id == id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;
        return id == book.id;
    }

    @Override
    public String toString() {
        return  String.format("Book[id=%s, title='%s', author='%s', status=%s]",
                id, title, author, status);
    }

    public boolean isReserved() {
        return status == BookStatus.RESERVED;
    }

    public boolean isReservedBy(Member member) {
        return reservedMembers.contains(member);
    }

    public boolean isBorrowed() {
        return status == BookStatus.BORROWED;
    }

    public boolean isAvailable() {
        return status == BookStatus.AVAILABLE;
    }
}
