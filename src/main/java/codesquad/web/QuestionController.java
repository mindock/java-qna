package codesquad.web;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping({"/", "/questions"})
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        question.setWriter((User) session.getAttribute(UserController.SESSION_NAME));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", findById(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", findById(id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question, HttpSession session, Model model){
        User user = getUser(session).orElseThrow(NullPointerException::new);
        Question questionOrigin = findById(id);
        if (questionOrigin.isWriter(user)) {
            questionOrigin.update(question);
            questionRepository.save(questionOrigin);
            return "redirect:/";
        }
        model.addAttribute("alert", new Alert("다른 사람의 글을 수정할 수 없습니다.", "/"));
        return "/qna/alert";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        User user = getUser(session).orElseThrow(NullPointerException::new);
        Question questionOrigin = findById(id);
        if (questionOrigin.isWriter(user)) {
            questionRepository.delete(questionOrigin);
            return "redirect:/";
        }
        model.addAttribute("alert", new Alert("다른 사람의 글을 삭제할 수 없습니다.", "/"));
        return "/qna/alert";
    }

    @GetMapping("/form")
    public String showForm(HttpSession session) {
        getUser(session).orElseThrow(NullPointerException::new);
        return "qna/form";
    }

    public Question findById(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        questionOptional.orElseThrow(() -> new IllegalArgumentException());
        return questionOptional.get();
    }

    private Optional<User> getUser(HttpSession session) {
        return Optional.of((User) session.getAttribute(UserController.SESSION_NAME));
    }
}
