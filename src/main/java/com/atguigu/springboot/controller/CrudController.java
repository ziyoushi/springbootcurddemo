package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.TAdmin;
import com.atguigu.springboot.mapper.TAdminMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CrudController {
    @Autowired
    TAdminMapper tAdminMapper;
    //跳转至显示页面
//    @GetMapping("/crudtest")
//    public String crudPage(Model model){
//        //查询数据库中的用户
//        List<TAdmin> tAdminslist = tAdminMapper.selectTAdmin();
//        model.addAttribute("admins",tAdminslist);
//        System.out.println(tAdminslist);
//        return "admin/listPage";
//    }
    //分页
    @GetMapping("/crudtest")
    public String crudPageList(Model model, HttpSession session, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value="pageSize",defaultValue = "3") Integer pageSize,
                               @RequestParam(value="condition",defaultValue = "") String condition){
        session.setAttribute("pageNum",pageNum);
        //查询数据库中的用户
        PageHelper.startPage(pageNum,pageSize);
        List<TAdmin> tAdminslist = tAdminMapper.selectTAdmin();
        PageInfo pageInfo = new PageInfo(tAdminslist);
        model.addAttribute("admins",pageInfo);

        return "admin/listPage";
    }

    //删除
    @GetMapping("/admin/del")
    public String delAdmin(Integer id,HttpSession session){
        tAdminMapper.deleteById(id);
        Integer pageNum = (Integer)session.getAttribute("pageNum");
        return "redirect:/crudtest?pageNum="+pageNum;
    }

    //添加
    @GetMapping("/admin/add")
    public String addAdmin(TAdmin admin){
        admin.setUserpswd("123456");
        Date time = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(time);
        admin.setCreatetime(format);
        tAdminMapper.addAdmin(admin);
        return "redirect:/crudtest?pageNum="+Integer.MAX_VALUE;
    }

    //查询需要修改的用户信息
    @ResponseBody
    @GetMapping("/admin/selectone")
    public TAdmin getOneInfo(Integer id){
        return tAdminMapper.selectTAdminById(id);
    }

    //修改用户信息

    @GetMapping("/admin/update")
    public String updateAdmin(TAdmin admin,HttpSession session){
        tAdminMapper.updateTadmin(admin);
        Integer pageNum = (Integer)session.getAttribute("pageNum");
        return "redirect:/crudtest?pageNum="+pageNum;
    }
}
