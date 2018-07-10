package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users") // 대표 URL을 요청에 매핑
public class UserController {
    public static final String SESSION_NAME = "sessionedUser";

    @Autowired // Spring framework이 자동으로 인터페이스 - 객체 매핑을 해줍니다.
    private UserRepository userRepository;

    // index 값이 동적으로 달라진다.
    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(SESSION_NAME);
        if (sessionedUser == null) {
            model.addAttribute("users", userRepository.findAll());
            return "/user/list";
        }
        model.addAttribute("users", userRepository.findByIdNot(sessionedUser.getId()));
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/updateForm";
    }

    @PutMapping("/{userId}")
    public String update(User user, HttpSession session) {
        User userOrigin = userRepository.findByUserId(user.getUserId());
        userOrigin.update(user);
        session.removeAttribute(SESSION_NAME);
        session.setAttribute(SESSION_NAME, userOrigin);
        userRepository.save(userOrigin);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> optionalUser = userRepository.findByUserIdAndPassword(userId, password);
        if (User.isCorrectUser(optionalUser)) {
            session.setAttribute(SESSION_NAME, optionalUser.get());
            return "redirect:/";
        }
        return "/user/login_failed";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SESSION_NAME);
        return "redirect:/";
    }
}
