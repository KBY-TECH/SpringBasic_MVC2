package com.kbytech.init.web;

import com.kbytech.init.domain.Board;
import com.kbytech.init.domain.BoardRepository;
import com.kbytech.init.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Board newboard= new Board(sessionedUser,title,contents);
        System.out.println(newboard);
        boardRepository.save(newboard);

        return "redirect:/";
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable Long id, Model model, HttpSession session)
    {
        if(!HttpSessionUtils.isLoginUser(session))  //tempUser==null
        {
            System.out.println("로그인이 안되어 있습니다.");
            return "redirect:/user/signIn";
        }

//        model.addAttribute("board",boardRepository.findByOne(id));

        model.addAttribute("board",boardRepository.findById(id).get());

        return "board/board_detail";
    }

    @GetMapping("/{id}/board_updateForm")
    public String updateForm(@PathVariable Long id,Model model,HttpSession session)
    {
        if(!HttpSessionUtils.isLoginUser(session))  //tempUser==null
        {
            System.out.println("로그인이 안되어 있습니다.");
            return "redirect:/user/signIn";
        }
        User sessionndUser=HttpSessionUtils.getUserFromSession(session);

        model.addAttribute("board",boardRepository.findById(id).get());
        return "board/board_updateForm";
    }

    @PutMapping("/{id}/updated")
    public String updated(@PathVariable Long id,String title,String contents)
    {
        Board board=boardRepository.findById(id).get();
        board.update(title,contents);
        boardRepository.save(board);
        return String.format("redirect:/board/%d/detail",id);

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id)
    {
        boardRepository.delete(boardRepository.findById(id).get());
        return "redirect:/";
    }




}
