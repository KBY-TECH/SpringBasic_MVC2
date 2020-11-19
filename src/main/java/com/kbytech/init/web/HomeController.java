package com.kbytech.init.web;

import com.kbytech.init.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("")
    public String Home(Model model)
    {
        model.addAttribute("boardlist",boardRepository.findAll());
        return "index";
    }


}
