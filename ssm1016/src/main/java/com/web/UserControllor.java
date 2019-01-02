package com.web;

import com.bean.UserTb;
import com.service.UserTbService;
import com.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Controller
@SessionAttributes({"u1","logintime"})
public class UserControllor {

    @Autowired
    private UserTbService userTbService;

    //修改
    @RequestMapping("/user/updateuser")
    public void update(UserTb userTb, HttpServletResponse resp, ModelMap map){
       int k= userTbService.updateByPrimaryKeySelective(userTb);
        resp.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=resp.getWriter();
            if (k>0){
                UserTb userTb1=userTbService.selectByPrimaryKey(userTb.getUserId());
                map.put("u1",userTb1);
                out.write("<script>alert('修改成功');top.location.href='/index.jsp'</script>");
            }else{
                out.write("<script>alert('修改失败');top.location.href='MyUser.jsp'</script>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //登录
    @RequestMapping("/login")
    public void login(HttpServletResponse resp, UserTb userTb,
                      String DropExpiration, ModelMap map,
                      String yanzheng){
        System.out.println(userTb.getUserName()+"..."+userTb.getUserPs());
        String yz= ImageUtil.getCode();
        resp.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=resp.getWriter();
        if(yz.equals(yanzheng)){
            UserTb u=userTbService.login(userTb);


                if(u==null){
                    out.print("<script>alert('用户名或密码不正确');location.href='login.jsp'</script>");
                }else{
                    map.put("u1",u);
                    Cookie c=new Cookie("uname",userTb.getUserName());
                    if (DropExpiration.equals("Day")){
                        c.setMaxAge(24*60*60);
                    }else if(DropExpiration.equals("Month")){
                        c.setMaxAge(24*60*60*31);
                    }else if(DropExpiration.equals("Year")){
                        c.setMaxAge(24*60*60*365);
                    }
                    resp.addCookie(c);
                    //登录时间
                    Date date=new Date();
                    map.put("logintime",date);
                    out.print("<script>alert('登录成功');location.href='index.jsp'</script>");
                }

        }else{
            out.print("<script>alert('验证码错误');location.href='login.jsp'</script>");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      //验证密码
    @RequestMapping("/user/checkpass")
    public void  checkpass(String yuanpass,ModelMap map,HttpServletResponse response){
         UserTb userTb=(UserTb) map.get("u1");
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=response.getWriter();
            if(userTb.getUserPs().equals(yuanpass)){
                out.print(true);
            }else{
                out.print(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //修改密码
    @RequestMapping("/user/updatepassword")
    public void updatepassword(UserTb userTb, HttpServletResponse response, SessionStatus status){
       int k= userTbService.updateByPrimaryKeySelective(userTb);
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=response.getWriter();
            if(k>0){
                status.setComplete();
                out.print("<script >alert('修改成功，请重新登录');top.location.href='/login.jsp'</script>");
            }else{
                out.print("<script >alert('修改失败');location.href='password.jsp'</script>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
