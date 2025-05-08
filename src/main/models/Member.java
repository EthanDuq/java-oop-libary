package models;

import enums.MemberStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Member {
    public static int idSuivante = 0;
    private final int id;
    private final String name;

    private MemberStatus status;

    private final int MAX_BOOKS_TO_BORROWED = 5;
    private final int MAX_BOOKS_TO_RESERVED = 5;

    private final List<Book> borrowedBooks = new ArrayList<>();
    private final List<Book> reservedBooks = new ArrayList<>();

    public Member(String name) {
        this.name = name;
        this.id = idSuivante;
        idSuivante++;
        this.status = MemberStatus.ACTIVE;
    }

    public boolean isId(int id) {
        return this.id == id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return id == member.id;
    }

    private boolean canBorrow(Book book) {
        return borrowedBooks.size() < MAX_BOOKS_TO_BORROWED
                && !borrowedBooks.contains(book)
                && status != MemberStatus.BANNED;
    }

    public boolean borrow(Book book) {
        if (canBorrow(book)) {
            borrowedBooks.add(book);
            return true;
        }
        return false;
    }

    private boolean haveBook(Book book) {
        return borrowedBooks.contains(book);
    }

    public boolean returnBook(Book book) {
        if (haveBook(book)){
            borrowedBooks.remove(book);
            return true;
        }
        return false;
    }

    private boolean canReserve(Book book) {
        return MAX_BOOKS_TO_RESERVED > reservedBooks.size()
                && !reservedBooks.contains(book)
                && status != MemberStatus.BANNED;
    }

    public boolean reserveBook(Book book) {
        if (canReserve(book)) {
            reservedBooks.add(book);
            return true;
        }
        return false;
    }

    private boolean isReserved(Book book) {
        return reservedBooks.contains(book);
    }

    public boolean cancelReserve(Book book) {
        if (isReserved(book)) {
            reservedBooks.remove(book);
            return true;
        }
        return false;
    }

    public void cleanBookLists(){
        borrowedBooks.clear();
        reservedBooks.clear();
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", borrowedBooks=" + borrowedBooks +
                ", reservedBooks=" + reservedBooks +
                '}';
    }
}
