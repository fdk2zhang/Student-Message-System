package com.web;

import com.bean.Classes;
import com.github.pagehelper.PageInfo;
import com.service.ClassService;
import com.service.DepartmentService;
import com.service.MajorService;
import com.service.UserTbService;
import com.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ClassController {
    @Autowired
    private ClassService classService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private UserTbService userTbService;


    //导出班级信息
    @RequestMapping("/Educational/class/daochu")
    public void daochu(int[] single, HttpServletResponse response){
         PageInfo pg= classService.getall(null,null,0,0,single,null);
         List<Classes> list=pg.getList();
         ExcelUtil.headers=new String[]{"院系","班级编号","班级名称","班主任老师","人数","班级状态"};
        ExcelUtil.createhead(6);
        ExcelUtil.createother(list);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
        String date= simpleDateFormat.format(new Date());
        FileOutputStream out= null;
        try {
            out = new FileOutputStream("f:\\class"+date+".xls");
            ExcelUtil.export(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out2=response.getWriter();
            out2.print("<script>alert('导出成功');location.href='/Educational/class/getclasslist'</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //审核--查询班级
    @RequestMapping("/Educational/getclasses")
    public  String getclass(String classname, String classnum,
                            @RequestParam(value="index",defaultValue = "1") int pageindex,
                            ModelMap map,
                            @RequestParam(value="size",defaultValue = "5") int size){

      PageInfo pi= classService.getall(classname,classnum,pageindex,size,null,"审核中");
       pi.setPageSize(size);
        map.put("pi",pi);
        map.put("cname",classname);
        map.put("cnum",classnum);
        return "/Educational/Auditing";
    }

    //查询所有班级
    @RequestMapping("/Educational/class/getclasslist")
    public String getall(String classname, String classnum,
                         @RequestParam(value="index",defaultValue = "1") int pageindex,
                         ModelMap map,
                         @RequestParam(value="size",defaultValue = "5") int size){
        PageInfo pageInfo=classService.getall(classname,classnum,pageindex,size,null,null);
        pageInfo.setPageSize(size);
        map.put("pi",pageInfo);
        map.put("cname",classname);
        map.put("cnum",classnum);
     //   map.put("size",size);
        return "/Educational/class/list";
    }
    //查询所有学院
    @RequestMapping("/Educational/class/getDepts")
    public String getdepts(ModelMap map){
        List list= departmentService.findall();
        map.put("dlist",list);
        return "/Educational/class/add";
    }
    //根据学院编号查询学院对应的专业
    @RequestMapping("/Educational/class/getmajorbydid")
    @ResponseBody
    public List getmajor(int did){

        List list=majorService.findbydeptid(did);
        return list;
    }

    //查询专业学院的老师
    @RequestMapping("/Educational/class/getteacher")
    @ResponseBody
    public List getteacher(int did,int mid){
        List list=userTbService.findall(did,mid,"班主任");
        return list;
    }

    //新增班级
    @RequestMapping("/Educational/class/addclass")
    public String add(Classes classes){
        classes.setClassstate("未审核");
        classService.insert(classes);
        return "redirect:/Educational/class/getclasslist";
    }

    //提交审核
    @RequestMapping("/Educational/class/updateclassstate")
    public String update(Classes classes){
        classes.setClassstate("审核中");
        classes.setAuditcount(classes.getAuditcount()+1);
        classService.updateByPrimaryKeySelective(classes);
        return "redirect:/Educational/class/getclasslist";
    }

    @RequestMapping("/Educational/udpatestate")
    public String update2(Classes classes){
        classService.updateByPrimaryKeySelective(classes);
        return "redirect:/Educational/getclasses";//审核界面
    }












}
