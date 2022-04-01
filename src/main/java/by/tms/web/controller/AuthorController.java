package by.tms.web.controller;

import by.tms.model.dto.AuthorDto;
import by.tms.model.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static by.tms.web.util.PageUtil.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/librarian/addAuthorPage")
    public String addAuthorPage(Model model) {
        model.addAttribute("author", AuthorDto.builder().build());
        return LIBRARIAN_PREFIX + "addAuthor";
    }

    @PostMapping("/librarian/addAuthor")
    public String addAuthor(AuthorDto author) {
        authorService.save(author);
        return REDIRECT + LIBRARIAN_PAGE;
    }

    @GetMapping("/user/allAuthorPage")
    public String allAuthorPage(Model model,
                                @RequestParam(name = OFFSET_PARAMETER, defaultValue = "0") String offset) {
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, authorService.getCountPages());
        model.addAttribute("allAuthors", authorService.findAll(LIMIT_TEN, Integer.parseInt(offset)));
        return USER_PREFIX + "allAuthor";
    }
}
