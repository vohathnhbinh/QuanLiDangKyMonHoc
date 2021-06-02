package additionals;

public class Account_Info {
    public String username = null;
    public String fullname = null;
    public String old_password = null;
    public String new_password = null;
    public String retype_password = null;

    public Account_Info() {}

    public Account_Info(String username, String fullname, String old_password, String new_password, String retype_password) {
        this.username = username;
        this.fullname = fullname;
        this.old_password = old_password;
        this.new_password = new_password;
        this.retype_password = retype_password;
    }
}
