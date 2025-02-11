package vttp2023.batch4.paf.assessment.util;

public class UserSQLQueries {
    
    public static final String USER_TABLE = "users";
    public static final String INSERT_USER = 
    """
       INSERT INTO users (email, name)
       VALUES (?,?);
    """;
    
    public static final String SELECT_USER =
    """
        SELECT * FROM users WHERE email = ?        
    """;
}
