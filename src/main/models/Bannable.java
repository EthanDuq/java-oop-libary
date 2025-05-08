package models;

public interface Bannable {
    void banMember(Member member);
    void unBanMember(Member member);
}
