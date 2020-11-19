package com.kbytech.init.web;

import com.kbytech.init.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    // session 중복 제거를 위한 클래스 생성.
    public static final String User_SESSION_KEY="sessionedUser";

    //로그인 세션 확인.
    public static boolean isLoginUser(HttpSession session)
    {
        Object sessionedUser=session.getAttribute(User_SESSION_KEY);
        if(sessionedUser==null)
        {
            return false;
        }
        return true;
    }
    // userform update 에 사용될 세션 확인.
    public static User getUserFromSession(HttpSession session)
    {
        if(!isLoginUser(session))
        {
            return null;
        }
        User sessionedUser=(User)session.getAttribute(User_SESSION_KEY);
        return sessionedUser;
    }
}
