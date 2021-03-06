package com.itunion.controller;

import com.itunion.model.Result;
import com.itunion.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private HttpSession session;

    // 账号密码登录
    @GetMapping(value = "login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        System.out.println("login username = [" + username + "], password = [" + password + "]");
        User user = new User(username, "app", session.getId());
        session.setAttribute("user", user);
        // 这里记得把会话ID返回到前端，前端之后请求都需要携带该ID, 可以封装到对象中
        return new Result<>(user);
    }

    // 微信登录
    @GetMapping(value = "loginByWx")
    public Result loginByWx(@RequestParam String code) {
        System.out.println("loginByWx.code = [" + code + "]");
        // 调用微信API获取OpenId等信息
        User user = new User("Jim", "weixin", session.getId());
        session.setAttribute("user", user);
        return new Result<>(user);
    }

    // 退出
    @GetMapping(value = "logout")
    public Result logout() {
        System.out.println("logout");
        // session 设置为无效的
        session.invalidate();
        return new Result();
    }

    // 使用会话中的信息
    @GetMapping(value = "hello")
    public Result<User> hello() {
        User user = (User) session.getAttribute("user");
        System.out.println("hello " + user.toString());
        return new Result<>(user);
    }
}
