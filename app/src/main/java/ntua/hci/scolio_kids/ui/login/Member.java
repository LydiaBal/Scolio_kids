package ntua.hci.scolio_kids.ui.login;

public class Member {
    private String Doctor;
    private String Patient;

    public Member(){}

    public String getUser1() {
        return Doctor;
    }

    public void setUser1(String user1) {
        Doctor = user1;
    }

    public String getUser2() {
        return Patient;
    }

    public void setUser2(String user2) {
        Patient = user2;
    }
}
