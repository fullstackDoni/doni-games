package repackgamesbydoni.donigames.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repackgamesbydoni.donigames.company.Developers;
import repackgamesbydoni.donigames.company.Game;
import repackgamesbydoni.donigames.model.Users;
import repackgamesbydoni.donigames.repository.DeveloperRepository;
import repackgamesbydoni.donigames.repository.GameRepository;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DeveloperRepository developerRepository;


    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "login";
    }

    @GetMapping(value = "/profile")
    public String profile(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        List<Game> game = gameRepository.findAll();
        model.addAttribute("game", game);
        return "profile";
    }
    @GetMapping(value = "/moderatorpanel")
    public String moderator(Model model) {
        List<Developers> developers = developerRepository.findAll();
        model.addAttribute("developers",developers);
        List<Game> games = gameRepository.findAll();
        model.addAttribute("games",games);
        model.addAttribute("currentUser", getCurrentUser());
        return "moderatorpanel";
    }

    @GetMapping(value = "/adminpanel")
    public String admin(Model model) {
        List<Developers> developers = developerRepository.findAll();
        model.addAttribute("developers",developers);
        List<Game> games = gameRepository.findAll();
        model.addAttribute("games",games);
        model.addAttribute("currentUser", getCurrentUser());
        return "adminpanel";
    }
    @GetMapping(value = "/403")
    public String accessDeniedPage(Model model) {
        model.addAttribute("currentUser",getCurrentUser());
        return "403";
    }

    private Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users currentUser = (Users) authentication.getPrincipal();
            return currentUser;
        }
        return null;
    }
    @PostMapping(value="/addgame")
    public String addGame(@RequestParam(name="name") String name,
                         @RequestParam(name="god") int god,
                         @RequestParam(name="developer_id") Long developer_id,
                         Model model){
        Developers developer = developerRepository.findById(developer_id).orElse(null);
        model.addAttribute("currentUser", getCurrentUser());
        if(developer!=null){
            Game game = new Game();
            game.setName(name);
            game.setGod(god);
            game.setDevelopers(developer);
            gameRepository.save(game);
        }
        return "redirect:/adminpanel";
    }

    @PostMapping(value="/savegame")
    public String saveGame(@RequestParam(name = "id") Long id,
                           @RequestParam(name="name") String name,
                           @RequestParam(name="god") int god,
                           @RequestParam(name="developer_id") Long developerId,Model model
    ){
        Developers developers = developerRepository.findById(developerId).orElse(null);
        Game game = gameRepository.findById(id).orElse(null);
        model.addAttribute("currentUser", getCurrentUser());
        if(developers!=null && game!=null){
            game.setName(name);
            game.setGod(god);
            game.setDevelopers(developers);
            gameRepository.save(game);
            return "redirect:/details/" + id;
        }
        return "redirect:/";
    }

    @PostMapping(value="/deletedevelopers")
    public String deleteDevelopers(@RequestParam(name="id") Long id,
                                   Model model){
        Developers developers=developerRepository.findById(id).orElse(null);
        model.addAttribute("currentUser", getCurrentUser());
        if(developers!=null){
            developerRepository.delete(developers);
        }else{
            return "redirect:/403";
        }
        return "redirect:/developers";
    }

    @PostMapping(value = "/adddeveloper")
    public String addDeveloper(@RequestParam(name="name") String name,Model model){
        Developers developers = new Developers();
        developers.setName(name);
        model.addAttribute("currentUser",getCurrentUser());
        developerRepository.save(developers);
        return "redirect:/developer";
    }
    @GetMapping(value = "/developer")
    public String developers(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        List<Developers> developers = developerRepository.findAll();
        model.addAttribute("developer", developers);
        return "redirect:/";
    }

    @GetMapping(value="/game_details/{id}")
    public String gameDetails(@PathVariable(name="id") Long id, Model model){
        Game game = gameRepository.findById(id).orElse(null);
        model.addAttribute("game", game);
        model.addAttribute("currentUser", getCurrentUser());
        return "details";
    }
    @PostMapping(value="deletegame")
    public String deleteGame(@RequestParam(name="id") Long id,
                             Model model){
        Game games = gameRepository.findById(id).orElse(null);
        model.addAttribute("game",games);
        model.addAttribute("currentUser", getCurrentUser());
        if(games!=null){
            gameRepository.delete(games);
        }else{
            return "redirect:/moderatorpanel";
        }
        return "redirect:/adminpanel";
    }
    @GetMapping(value="/details/{id}")
    public String GameDetails(@PathVariable(name="id") Long id, Model model){
        List<Developers> developers= developerRepository.findAll();
        model.addAttribute("developers", developers);
        Game game = gameRepository.findById(id).orElse(null);
        model.addAttribute("game", game);
        model.addAttribute("currentUser", getCurrentUser());
        return "details";
    }

}
