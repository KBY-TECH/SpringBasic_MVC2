package com.kbytech.init.web;

import com.kbytech.init.domain.Board;
import com.kbytech.init.domain.BoardRepository;
import com.kbytech.init.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/createForm")
    public String createForm(HttpSession session)  //board 작성은 회원만 가능.
    {

        if(!HttpSessionUtils.isLoginUser(session))
        {
            System.out.println("로그인이 안되어 있습니다.");
            return "redirect:/user/signIn";
        }
        return "board/boardForm";
    }

    @PostMapping("/create")
    public String create(String title,String contents,HttpSession session)
    {
        System.out.println("creat 시작");
        if(!HttpSessionUtils.isLoginUser(session))
        {
            System.out.println("로그인이 안되어 있습니다. Create 부분");
            return "redirect:/user/signIn";
        }

        User sessionedUser=HttpSessionUtils.getUserFromSession(session);
        Board newboard= new Board(sessionedUser.getUsername(),title,contents);
        System.out.println(newboard);
        boardRepository.save(newboard);

        return "redirect:/";
    }




}
