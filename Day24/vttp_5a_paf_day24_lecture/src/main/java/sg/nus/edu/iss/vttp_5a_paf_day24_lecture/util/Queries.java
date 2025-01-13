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

    public static final String QUERY_TO_CREATE_BOOK = 
    """
        insert into Book (title, quantity) values (?,?);        
    """;

    public static final String QUERY_TO_GET_BOOKS = 
    """
        select * from Book;     
    """;

    public static final String QUERY_TO_GET_BOOK_BY_ID = 
    """
        select * from Book where id = ?;     
    """;

    public static final String QUERY_TO_UPDATE_BOOK_BY_ID = 
    """
        update Book set title = ?, quantity = ? where id = ?;        
    """;

    public static final String QUERY_TO_UPDATE_BOOK_STATUS_BY_ID = 
    """
        update Book set isActive = ? where id = ?;    
    """;

    public static final String QUERY_TO_INSERT_RESERVATION = 
    """
        insert into Reservation (full_name, reserve_date) values (?,?);
    """;

    public static final String QUERY_TO_INSERT_RESERVATION_DETAIL = 
    """
        insert into ReservationDetails (book_id, reservation_id) values (?,?);        
    """;
}
