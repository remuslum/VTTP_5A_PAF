package sg.nus.edu.iss.vttp_5a_day21_lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.edu.iss.vttp_5a_day21_lecture.service.GameService;

@Controller
@RequestMapping
public class GameController {
    
    @Autowired
    GameService gameService;

    @GetMapping("/games")
    public ModelAndView getGames(@RequestParam(defaultValue="5") int count){
        ModelAndView mav = new ModelAndView();
        mav.addObject("games", gameService.getGameList(count));
        mav.setViewName("game");
        return mav;
    }
}
