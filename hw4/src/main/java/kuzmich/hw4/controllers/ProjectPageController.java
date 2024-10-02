package kuzmich.hw4.controllers;

import kuzmich.hw4.services.MyProjectPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home/projects")
@RequiredArgsConstructor
public class ProjectPageController {

    private final MyProjectPageService projectPageService;

    @GetMapping
    public String getAllProjectsPage (Model model) {
        List<ProjectPageDto> projects = projectPageService.findAllProjects();
        model.addAttribute("projects", projects);
        return "projects-page.html";
    }

    @GetMapping("/{id}")
    public String getProjectPage(@PathVariable Long id, Model model) {
        Optional<ProjectPageDto> projectOptional = projectPageService.findProjectById(id);
        if(projectOptional.isEmpty()) {
            return "not-found.html";
        }
        model.addAttribute("project", projectOptional.get());
        return "project-page.html";
    }
}
