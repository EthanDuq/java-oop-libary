package models;

import java.util.ArrayList;
import java.util.List;

public class MemberService implements LibraryService<Member>, Bannable{
    private List<Member> members = new ArrayList<Member>();

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
    public void banMember() {

    }

    @Override
    public void unBanMember() {

    }
}
