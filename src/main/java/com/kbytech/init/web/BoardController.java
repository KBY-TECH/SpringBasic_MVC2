package com.kbytech.init.web;

import com.kbytech.init.domain.Board;
import com.kbytech.init.domain.BoardRepository;
import com.kbytech.init.domain.Result;
import com.kbytech.init.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
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
        //중복 코드
       /* if(!HttpSessionUtils.isLoginUser(session))  //tempUser==null
        {
            System.out.println("로그인이 안되어 있습니다.");
            return "redirect:/user/signIn";
        }
        User sessionndUser=HttpSessionUtils.getUserFromSession(session);
        //나의 글인지 아닌지. check
        Board myboard=boardRepository.findById(id).get();
        // 현재 작성자와 글쓰기가 일치하는 여부확인 , 그런데
        //sessionndUser.equals(myboard.getUser()) 직접 꺼내다 쓰면 안좋음...
        if(!myboard.isSameWriter(sessionndUser))
        {
            return "redirect:/";
        }
        // 중복제거
        model.addAttribute("board",myboard);*/

        // 중복제거 2
        /*try {
            Board board=boardRepository.findById(id).get();
            hasPermission(session,board);
            model.addAttribute("board",board);
            return "board/board_updateForm";
        }
        catch (IllegalStateException e)
        {
            model.addAttribute("errorMsg",e.getMessage());
            return "index";
        }*/

        Board board=boardRepository.findById(id).get();
        Result result=valid(session,board);
        if(!result.isValid())
        {
            model.addAttribute("errorMsg",result.getErrorMsg());
            return "/user/signIn";
        }
        model.addAttribute("board",board);
        return "board/board_updateForm";
    }
    private Result valid(HttpSession session, Board board)
    {
        if(!HttpSessionUtils.isLoginUser(session))
        {
           return Result.fail("required login");
        }
        User sessionndUser=HttpSessionUtils.getUserFromSession(session);
        if(!board.isSameWriter(sessionndUser))
        {
            return Result.fail("Data Integrity Violation!");
        }
        return Result.ok();
    }
    // 중복 세션로그인, 해당 게시판 삭제 수정 본인인지 확인 하는 코드 중복 제거.
    private boolean hasPermission(HttpSession session, Board board)
    {

        if(!HttpSessionUtils.isLoginUser(session))
        {
            throw new IllegalStateException("required login");
        }
        User sessionndUser=HttpSessionUtils.getUserFromSession(session);
        if(!board.isSameWriter(sessionndUser))
        {
            throw new IllegalStateException("Data Integrity Violation!");
        }
        return true;
    }

    @PutMapping("/{id}/updated")
    public String updated(Model model,@PathVariable Long id,String title,String contents,HttpSession session)
    {
        Board board=boardRepository.findById(id).get();
        Result result=valid(session,board);
        if(!result.isValid())
        {
            model.addAttribute("errorMsg",result.getErrorMsg());
            return "/user/signIn";
        }
        board.update(title,contents);
        boardRepository.save(board);
        return String.format("redirect:/board/%d/detail",id);
        /*try {
            Board board=boardRepository.findById(id).get();
            hasPermission(session,board);
            model.addAttribute("board",board);
            board.update(title,contents);
            boardRepository.save(board);
            return String.format("redirect:/board/%d/detail",id);
        }
        catch (IllegalStateException e)
        {

            model.addAttribute("errorMsg",e.getMessage());
            return "index";
        }*/

        /*User sessionndUser=HttpSessionUtils.getUserFromSession(session);
        //나의 글인지 아닌지. check
        Board myboard=boardRepository.findById(id).get();
        // 현재 작성자와 글쓰기가 일치하는 여부확인 , 그런데
        //sessionndUser.equals(myboard.getUser()) 직접 꺼내다 쓰면 안좋음...
        if(!myboard.isSameWriter(sessionndUser))
        {
            return "redirect:/";
        }


        myboard.update(title,contents);
        boardRepository.save(myboard);
        return String.format("redirect:/board/%d/detail",id);*/

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,Model model,HttpSession session)
    {
        Board board=boardRepository.findById(id).get();
        Result result=valid(session,board);
        if(!result.isValid())
        {
            model.addAttribute("errorMsg",result.getErrorMsg());
            return "/user/signIn";
        }
        boardRepository.delete(board);
        return "redirect:/";
       /* try {
            Board board=boardRepository.findById(id).get();
            hasPermission(session,board);
            boardRepository.delete(board);
            return "redirect:/";
        }
        catch (IllegalStateException e)
        {
            System.out.println("illigal Access");
            model.addAttribute("errorMsg",e.getMessage());
            return "/user/signIn";
        }*/
       /* User sessionndUser=HttpSessionUtils.getUserFromSession(session);
        //나의 글인지 아닌지. check
        Board myboard=boardRepository.findById(id).get();
        // 현재 작성자와 글쓰기가 일치하는 여부확인 , 그런데
        //sessionndUser.equals(myboard.getUser()) 직접 꺼내다 쓰면 안좋음...
        if(!myboard.isSameWriter(sessionndUser))
        {
            return "redirect:/";
        }

        boardRepository.delete(boardRepository.findById(id).get());
        return "redirect:/";*/
    }




}
