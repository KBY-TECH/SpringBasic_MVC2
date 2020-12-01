package com.kbytech.init.web;

import com.kbytech.init.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

// 어디에 종속되어있다. board id가 무조건 필요하다.

@Controller
@RequestMapping("/boards/{boardId}/answer")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping("")
    public String create(@PathVariable Long boardId, String contents,HttpSession session)
    {
        if(!HttpSessionUtils.isLoginUser(session))  //tempUser==null
        {
            System.out.println("로그인이 안되어 있습니다.");
            return "redirect:/user/signIn";
        }

        User loginUser=HttpSessionUtils.getUserFromSession(session);
        Board board=boardRepository.findById(boardId).get();
        Answer answer=new Answer(loginUser,board,contents);
        answerRepository.save(answer);

        return String.format("redirect:/board/%d/detail",boardId);
    }



}
