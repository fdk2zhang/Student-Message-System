package com.util;

import com.bean.UserTb;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class MyTag extends SimpleTagSupport {
    private UserTb yonghu;

    public UserTb getYonghu() {
        return yonghu;
    }

    public void setYonghu(UserTb yonghu) {
        this.yonghu = yonghu;
    }

    public boolean getTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    private boolean test;


    @Override  //定义希望标签做的事情
    public void doTag() throws JspException, IOException {

       /* 直接给页面输出内容
       JspWriter out= this.getJspContext().getOut();
        out.print("这是我的自定义标签");*/
       //获得标签对中间的内容并输出
        /*StringWriter sw=new StringWriter();
        this.getJspBody().invoke(sw);
        this.getJspContext().getOut().print(sw);*/
        this.getJspContext().getOut().print(yonghu.getUserName()+".....");

    }
}
