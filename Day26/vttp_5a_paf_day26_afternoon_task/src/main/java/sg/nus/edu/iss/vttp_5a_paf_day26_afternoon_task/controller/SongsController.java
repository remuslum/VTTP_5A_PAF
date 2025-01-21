package sg.nus.edu.iss.vttp_5a_paf_day26_afternoon_task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.edu.iss.vttp_5a_paf_day26_afternoon_task.model.Song;
import sg.nus.edu.iss.vttp_5a_paf_day26_afternoon_task.repo.SongsRepository;

@Controller
@RequestMapping
public class SongsController {

    @Autowired
    private SongsRepository songsRepository;
    
    @GetMapping
    public ModelAndView homepage(){
        ModelAndView mav = new ModelAndView("landingpage");
        mav.addObject("years", songsRepository.getAllYears());
        return mav;
    }

    @PostMapping("/songs")
    public ModelAndView findSongByYear(@RequestParam int year){
        List<Song> results = songsRepository.findSongsByYear(year);
        ModelAndView mav = new ModelAndView("songpage");
        mav.addObject("year", year);
        mav.addObject("songs", results);
        return mav;
    }

    // @PostMapping("/songs")
    // public ModelAndView findSongByYear(@RequestParam int year){
    //     ModelAndView mav = new ModelAndView("documentpage");
    //     List<Document> results = songsRepository.findSongDocument(year);
    //     mav.addObject("documents", results);
    //     mav.addObject("year", year);
    //     return mav;
    // }

    
}
