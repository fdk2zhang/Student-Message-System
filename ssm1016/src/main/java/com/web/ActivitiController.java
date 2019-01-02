package com.web;

import com.bean.UserTb;
import com.service.ActivitiService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ActivitiController {
    @Autowired
    private ActivitiService activitiService;
    //1.查询流程定义和部署信息
    @RequestMapping("/bushu/getdeploys")
    public String chaxun(ModelMap map){
        //查询部署信息
        List<Deployment> dlist=activitiService.getdeplist();
        //查询流程定义信息
        List<ProcessDefinition> prolist=activitiService.getprolist();
        map.put("dlist",dlist);
        map.put("prolist",prolist);
        return "/bushu/list";
    }
    //2.上传流程文件
    @RequestMapping("/bushu/adddeploy")
    public String up(MultipartFile depfile,String name){
        try {
            activitiService.add(depfile.getInputStream(),name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/bushu/getdeploys";
    }
    //3.查看流程图
    @RequestMapping("/bushu/lookimage")
    public void liuchengtu(String depid, String imagename, HttpServletResponse response){

        try {
            //根据部署id和图片名称，查看文件流
            InputStream inputStream=activitiService.findimage(depid,imagename);
            //将文件保存到本地
            FileUtils.copyInputStreamToFile(inputStream,new File("e://"+imagename));
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("<script>alert('文件保存在e盘,文件名:"+imagename+"');location.href='/bushu/getdeploys'</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //4.删除流程部署
    @RequestMapping("/bushu/deletedeploy")
    public void  delete(String depid,HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        try {
            activitiService.deletedeploy(depid);
            response.getWriter().print("<script>location.href='/bushu/getdeploys'</script>");
        } catch (Exception e) {
            try {
                response.getWriter().print("<script>alert('流程正在使用，不能删除');location.href='/bushu/getdeploys'</script>");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    //5.提交请假申请
    @RequestMapping("/qingjia/tijiao")
    public String qingjia(int leaveid, HttpSession session){//请假条的id值
        UserTb userTb=(UserTb) session.getAttribute("u1");
        activitiService.qingjia(leaveid,userTb.getUserName());
        return "redirect:/qingjia/getleavebills";
    }






}
