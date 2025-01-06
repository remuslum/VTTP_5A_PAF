package sg.nus.edu.iss.vttp_5a_day21_practice.util;

public class Queries {
    public static final String QUERY_STRING = """
            select * from kindle where author like ? limit ?  
        """;

    public static final String QUERY_BOOK_STRING = """
            select * from kindle where asin = ?
        """;
}
