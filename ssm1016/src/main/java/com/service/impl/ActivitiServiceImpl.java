package com.service.impl;

import com.service.ActivitiService;
import com.service.LeaveBillService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class ActivitiServiceImpl implements ActivitiService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private LeaveBillService leaveBillService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


    @Override
    public int deletedeploy(String depid) {
        repositoryService.deleteDeployment(depid);
        return 1;
    }

    @Override
    @Transactional  //如何将业务过程和流程定义进行关联
    public int qingjia(int leaveid,String username) {
        //1.修改请假单的状态
        leaveBillService.updateState(leaveid,1);
       //2.启动流程实例时候，需要建立流程和业务之间的关系
        //使用business_key来保存业务的信息
        //business_key的组成方式: 流程定义的key+业务的id
       String  business="LeaveBill."+leaveid;
       Map map=new HashMap();
       map.put("busid",business);
       map.put("username",username);
       ProcessInstance instance=
                runtimeService.startProcessInstanceByKey("LeaveBill",business,map);
        //查询流程实例的id查询任务对象
        Task task=
        taskService.createTaskQuery()
                    .processInstanceId(instance.getId()).singleResult();
        //完成个人任务
        taskService.complete(task.getId());
        return 1;
    }


    @Override
    public int add(InputStream in, String name) {
        ZipInputStream zip=new ZipInputStream(in);
        repositoryService.createDeployment()
                .addZipInputStream(zip)
                .name(name)
                .deploy();

        return 1;
    }

    @Override
    public List<Deployment> getdeplist() {

      List list=  repositoryService.createDeploymentQuery().list();


        return list;
    }

    @Override
    public List<ProcessDefinition> getprolist() {
        List list=repositoryService.createProcessDefinitionQuery().list();
        return list;
    }

    @Override
    public InputStream findimage(String depid, String imagename) {
        return repositoryService.getResourceAsStream(depid,imagename);
    }
}
