package sg.nus.edu.iss.vttp_5a_day21_lecture.util;

public class Queries {
    public static final String SQL_SELECT_GAME = """
            select * from game limit 10;
        """;

    public static final String SQL_SELECT_GAME_LIMIT = """
            select * from game limit ?
        """;
}
