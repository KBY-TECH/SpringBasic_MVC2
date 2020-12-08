package com.kbytech.init.web;

import com.kbytech.init.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

// 어디에 종속되어있다. board id가 무조건 필요하다.

@RestController // Json controller
@RequestMapping("/api/boards/{boardId}/answer")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long boardId, String contents2,HttpSession session)
    {
        if(!HttpSessionUtils.isLoginUser(session))  //tempUser==null
        {
            System.out.println("로그인이 안되어 있습니다.");
//            return "redirect:/user/signIn";
            return null;
        }

        User loginUser=HttpSessionUtils.getUserFromSession(session);
        Board board=boardRepository.findById(boardId).get();
        Answer answer=new Answer(loginUser,board,contents2);
        board.addAnswer();
        System.out.println("현재 댓글 수==>"+board.counterOfanswer);
//        answerRepository.save(answer);
        return  answerRepository.save(answer);
//        return String.format("redirect:/board/%d/detail",boardId);
    }
    @DeleteMapping("/temp/{id}")
    public Result delete(@PathVariable Long boardId,@PathVariable Long id,HttpSession session)
    {
        if(!HttpSessionUtils.isLoginUser(session))  //tempUser==null
        {
            System.out.println("로그인이 안되어 있습니다.");
            return Result.fail("Login Required");
        }
        User loginUser=HttpSessionUtils.getUserFromSession(session);
        Answer answer=answerRepository.findById(id).get();
        if(!answer.isSameWriter(loginUser))
        {
            return Result.fail("작성자와 로그인사용자가 다릅니다.");
        }
        System.out.println("db 제거.");
        answerRepository.delete(answer);
        Board board=boardRepository.findById(boardId).get();
        board.deleteAnswer();
        System.out.println("현재 댓글 수==>"+board.counterOfanswer);
        boardRepository.save(board);
        return Result.ok();
    }
    @DeleteMapping("/{id}")
    public Result CommentDelete(@PathVariable Long boardId,@PathVariable Long id,HttpSession session)
    {
        Board board=boardRepository.findById(boardId).get();
        if(!HttpSessionUtils.isLoginUser(session))  //tempUser==null
        {
            System.out.println("로그인이 안되어 있습니다.");
            return Result.fail("Login Required");
        }
        User loginUser=HttpSessionUtils.getUserFromSession(session);
        Answer answer=answerRepository.findById(id).get();
        if(!answer.isSameWriter(loginUser))
        {
            return Result.fail("작성자와 로그인사용자가 다릅니다.");
        }
        answerRepository.delete(answer);
        return Result.ok();
    }


}
