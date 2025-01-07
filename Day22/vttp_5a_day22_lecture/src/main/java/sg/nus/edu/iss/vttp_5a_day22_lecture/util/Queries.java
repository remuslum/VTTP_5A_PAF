package sg.nus.edu.iss.vttp_5a_day22_lecture.util;

public class Queries {
    public static final String QUERY_BOOKS = 
            """
            select author, count(title) books_published, avg(stars) book_average
            from kindle
            where author != ""
            group by author
            having books_published >= ?
            order by books_published desc
            limit ?;
            """;
    
}
