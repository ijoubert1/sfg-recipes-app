package local.springframework.controllers;

import local.springframework.commands.CategoryCommand;
import local.springframework.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class CategoryController {

    final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("categories")
    public String createCategory(@ModelAttribute CategoryCommand command, Model model){
        log.info("Entering category controller");

        CategoryCommand savedCategoryCommand = service.createCategory(command);

        model.addAttribute("category", savedCategoryCommand);
        return "categories/show";
    }

    @RequestMapping("/categories/new")
    public String newCategory(Model model){
        model.addAttribute("category", new CategoryCommand());

        return "categories/categoryform";
    }
}
