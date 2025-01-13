package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.util;

public class Queries {
    public static final String QUERY_TO_CREATE_ACCOUNT = 
    """
        insert into BankAccount (fullName, isActive, balance) values (?, ?, ?);   
    """;

    public static final String QUERY_TO_GET_ALL_RECORDS = 
    """
        select * from BankAccount;        
    """;

    public static final String QUERY_TO_SELECT_BANKACCOUNT_BY_ID = 
    """
        select * from BankAccount where id = ?;   
    """;

    public static final String QUERY_TO_UPDATE_BANK_ACCOUNT =
    """
        update BankAccount set balance = ? where id = ?;        
    """;

    public static final String QUERY_TO_DELETE_BANK_ACCOUNT =
    """
        update BankAccount set isActive = false where id = ?;        
    """;

    public static final String QUERY_TO_CHECK_ACCOUNT_EXISTS = 
    """
        select count(*) as count from BankAccount where id = ?;        
    """;
}
