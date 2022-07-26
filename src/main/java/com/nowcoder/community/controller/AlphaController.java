package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

   @Autowired
   private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println(httpServletRequest.getMethod());
        System.out.println(httpServletRequest.getServletPath());
        Enumeration<String> enumeration=httpServletRequest.getHeaderNames();
        while (enumeration.hasMoreElements()){
           String name=enumeration.nextElement();
           String val=httpServletRequest.getHeader(name);
           System.out.println(name+": "+val);
        }
        System.out.println(httpServletRequest.getParameter("code"));

        httpServletResponse.setContentType("text/html;charset=utf-8");
        try(PrintWriter printWriter=httpServletResponse.getWriter();) {
            printWriter.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //GET请求/students?current=3&limit=50
    @RequestMapping(path="/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name="current",required = false,defaultValue = "1") int current,
            @RequestParam(name="limit",required = false,defaultValue = "10")int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    ///student/101
    @RequestMapping(path="/student/{id}",method=RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id")int id){
        System.out.println(id);
        return "a student";
    }

    //POST请求
    @RequestMapping(path = "/student" ,method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //响应HTML数据
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("name","张老师");
        modelAndView.addObject("age","30");
        modelAndView.setViewName("/demo/view");
        return modelAndView;
    }

    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","西南交通大学");
        model.addAttribute("age","54");
        return "/demo/view";
    }

    //响应JSON数据(异步请求)
    //Java对象--》JSON字符串--》 JS对象
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> map=new HashMap<>();
        map.put("name","张三");
        map.put("age",23);
        map.put("salary",8000.00);
        return map;

    }


    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("name","张三");
        map.put("age",23);
        map.put("salary",8000.00);
        Map<String,Object> map2=new HashMap<>();
        map2.put("name","李四");
        map2.put("age",24);
        map2.put("salary",9000.00);
        Map<String,Object> map3=new HashMap<>();
        map3.put("name","王五");
        map3.put("age",25);
        map3.put("salary",18000.00);
        list.add(map);
        list.add(map2);
        list.add(map3);
        return list;

    }


}
