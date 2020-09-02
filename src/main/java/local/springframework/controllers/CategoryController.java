package local.springframework.controllers;

import local.springframework.commands.CategoryCommand;
import local.springframework.model.Category;
import local.springframework.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class CategoryController {

    final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @RequestMapping("/categories/{id}/show/")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("category", service.getCategoryById(id));
        return "categories/show";
    }

    @RequestMapping({"/categories/new", "/categories/new/"})
    public String newCategoryForm(Model model){
        model.addAttribute("category", new CategoryCommand());
        return "categories/categoryform";
    }

    @RequestMapping({"/categories", "/categories/"})
    public String listCategories(Model model){
        model.addAttribute("categories", service.findAll());
        return "categories/index";
    }

    @RequestMapping({"/categories/{id}/update/", "/categories/{id}/update/"})
    public String updateCategoryForm(@PathVariable String id, Model model){
        model.addAttribute("category", service.getCategoryById(id));
        return "categories/categoryform";
    }

    @PostMapping("category")
    public String createOrUpdateCategory(@ModelAttribute CategoryCommand command, Model model){
        CategoryCommand savedCategoryCommand = null;
        log.info("Entering category controller");
        Category existing = service.getCategoryById(command.getId());
        if(existing == null){
            savedCategoryCommand = service.createCategory(command);
        } else {
            savedCategoryCommand = service.updateCategory(command);
        }

        model.addAttribute("category", savedCategoryCommand);
        return "redirect:/categories/" + savedCategoryCommand.getId() + "/show/";
    }
}
