package com.kbytech.init.web;

import com.kbytech.init.domain.User;
import com.kbytech.init.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/user") // 메소드 위에 url 의 공통 url 제거.. 분할 원칙.
@Controller
public class UserController {

//    private List<User> userList =new ArrayList<>();
    // autowired 어딘가에서 가져다 쓰겟다.
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signUp")
    public String signUp()
    {
        return "/user/signUp";
    }

    @PostMapping("/create")
    public String register(User user)
    {
//        userList.add(user);
        userRepository.save(user);

        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model)
    {
//        model.addAttribute("user", userList);
        model.addAttribute("user", userRepository.findAll());
        return "/user/userList";
    }

    @GetMapping("/signIn")
    public String signIn()
    {
        return "/user/signIn";
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session){
        User user=userRepository.findByUsername(username);
        if(user==null)
        {
            System.out.println("가입되지 않은 회원 입니다.");
            return "redirect:/user/signIn";
        }
        if(!user.pwMatch(password)) //password.equals(user.getPw())
        {
            System.out.println("비밀번호가 틀렸습니다.");
            return "redirect:/user/signIn";
        }
        System.out.println("session 할당");
        session.setAttribute(HttpSessionUtils.User_SESSION_KEY,user);
        //세션에 저장할것.
        return "redirect:/";

    }

    @GetMapping("/logout")
    public String logOut(HttpSession session)
    {
        session.removeAttribute(HttpSessionUtils.User_SESSION_KEY);
        return "redirect:/";
    }


    @GetMapping("/{id}/updateForm")
    public String update(@PathVariable Long id,Model model,HttpSession session)
    {
//        Object tempUser= session.getAttribute("sessionedUser");
        if(!HttpSessionUtils.isLoginUser(session))  //tempUser==null
        {
            System.out.println("로그인이 안되어 있습니다.");
            return "redirect:/user/signIn";
        }

//        User sessionndUser=(User) tempUser;
        User sessionndUser=HttpSessionUtils.getUserFromSession(session);
        if(!sessionndUser.idMatch(id)) //!id.equals(sessionndUser.getId())
        {
            // 무결성 방지 .  기록 하기 위한  ..
            System.out.println("무결성 위반 기록");
            System.out.println(((User) sessionndUser));
            System.out.println("무결성 피해자 id "+ id);
            return "redirect:/";
        }
        // 위에와 중복임.
        User user=userRepository.findById(sessionndUser.getId()).get();
        //자기 자신의 정보만 수정할 수 있게끔.
        model.addAttribute("user",userRepository.findById(id).get());
        return "user/updateForm";
    }
//    @PostMapping("/{id}/updated")
//    public String updated(@PathVariable Long id,User updateuser)
//    {
//        User user=userRepository.findById(id).get();
//        user.update(updateuser);
//        userRepository.save(user);
//        return "redirect:/user/list";
//    }

    @PutMapping("/{id}/updated")
    public String updated(@PathVariable Long id,User updateuser,HttpSession session)
    {

        if(!HttpSessionUtils.isLoginUser(session))
        {
            System.out.println("로그인이 안되어 있습니다.");
            return "redirect:/user/signIn";
        }

        User sessionndUser=HttpSessionUtils.getUserFromSession(session);
        if(!sessionndUser.idMatch(id))
        {
            // 무결성 방지 .  기록 하기 위한  ..
            System.out.println("무결성 위반 기록");
            System.out.println(((User) sessionndUser));
            System.out.println("해킹 위험 피해자 id "+ id);
            return "redirect:/user/list";
        }
        // 위에와 중복임.
        User user=userRepository.findById(sessionndUser.getId()).get();
        user.update(updateuser);
        userRepository.save(user);
        return "redirect:/user/list";
    }




}