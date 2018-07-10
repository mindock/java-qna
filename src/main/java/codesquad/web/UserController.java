package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

//
//    @PostMapping("/users")
//    public ModelAndView create2(String userId,
//                                String password,
//                                String name,
//                                String email, Model model){
//        User user = new User(userId,password,name,email);
//        users.add(user);
//        ModelAndView mav = new ModelAndView("/user/list");
//        mav.addObject("users",users);
//        return mav;
//    }
//


//    @PostMapping("/users")
//    public String create(String userId,
//                                String password,
//                                String name,
//                                String email, Model model){
//        User user = new User(userId,password,name,email);
//        users.add(user);
//
//        model.addAttribute("users",users);
//        return "/user/list";
//    }



    @PostMapping("/users")
    public String create(User user){
        //users.add(user);
        userRepository.save(user);
        return "redirect:/users";
    }


    @GetMapping("/users")
    public String list(Model model){
        //model.addAttribute("users",users);
        model.addAttribute("users",userRepository.findAll());
        return "/user/list";
    }

    /*@GetMapping("/users/{index}")
    public String show(@PathVariable long index, Model model){
        //model.addAttribute("user",users.get(index));
        User user=userRepository.findById(index).get();
        model.addAttribute("user",user);
        return "/user/profile";
    }*/

    @GetMapping("/users/{userId}")
    public String show(@PathVariable String userId, Model model){
        //model.addAttribute("user",users.get(index));
        User user=userRepository.findByUserId(userId);
        model.addAttribute("user",user);
        return "/user/profile";
    }
}
