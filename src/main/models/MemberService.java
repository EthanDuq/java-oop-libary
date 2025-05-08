package models;

import enums.MemberStatus;

import java.util.ArrayList;
import java.util.List;

public class MemberService implements LibraryService<Member>, Bannable{
    private final List<Member> members = new ArrayList<Member>();
    private final List<Member> bannedMembers = new ArrayList<Member>();

    @Override
    public void addItem(Member item) {
        if (members.contains(item) || item == null) {
            throw new IllegalArgumentException("Membre is null or already added");
        }
        members.add(item);
    }

    @Override
    public void removeItem(Member item) {
        members.removeIf(m -> m.equals(item));
    }

    @Override
    public Member getItem(int id) {
        return members.stream()
                               .filter(m -> m.isId(id))
                               .findFirst()
                               .get();
    }

    @Override
    public List<Member> getItems() {
        return new ArrayList<>(members);
    }

    @Override
    public void banMember(Member member) {
        member.cleanBookLists();
        member.setStatus(MemberStatus.BANNED);
        members.remove(member);
        bannedMembers.add(member);
    }

    @Override
    public void unBanMember(Member member) {
        member.setStatus(MemberStatus.ACTIVE);
        bannedMembers.remove(member);
        members.add(member);
    }

    public List<Member> getBannedMembers() {
        return new ArrayList<>(bannedMembers);
    }
}
