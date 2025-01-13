package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model;

public class BankAccount {
    private int id;
    private String fullName;
    private boolean isActive;
    private float balance;
    
    public BankAccount() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public float getBalance() {
        return balance;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }

    
}
