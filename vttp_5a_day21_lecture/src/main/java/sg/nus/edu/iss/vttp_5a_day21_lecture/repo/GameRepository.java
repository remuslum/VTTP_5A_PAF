package sg.nus.edu.iss.vttp_5a_day21_lecture.repo;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_day21_lecture.model.Game;
import sg.nus.edu.iss.vttp_5a_day21_lecture.util.Queries;

@Repository
public class GameRepository {

    @Autowired
    private JdbcTemplate template;

    public List<Game> selectGames(){
        return selectGames(5);
    }

    public List<Game> selectGames(int limit){
        SqlRowSet rowSet = template.queryForRowSet(Queries.SQL_SELECT_GAME_LIMIT,limit);
        List<Game> results = new LinkedList<>();

        // To read the first row, need to call next
        while (rowSet.next()){
            results.add(Game.toGame(rowSet));
        }

        return results;
    }
}
