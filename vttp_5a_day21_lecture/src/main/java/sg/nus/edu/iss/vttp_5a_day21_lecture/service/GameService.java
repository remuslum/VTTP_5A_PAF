package sg.nus.edu.iss.vttp_5a_day21_lecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_day21_lecture.model.Game;
import sg.nus.edu.iss.vttp_5a_day21_lecture.repo.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;

    public List<Game> getGameList(int limit){
        return gameRepository.selectGames(limit);
    }


    
}
