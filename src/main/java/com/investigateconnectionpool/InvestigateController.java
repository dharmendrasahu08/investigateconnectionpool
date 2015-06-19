package com.investigateconnectionpool;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InvestigateController {
    
    public EmpService empService;
    
    private static final String SUCCESS_PAGE = "success"; 
    private static final String SUCCESS_MESSAGE = "There is no error";
    
    @RequestMapping("/tomcatJdbcTest")
    public ModelAndView tomcatDataSourceTest() {
        return executeQuery("tomcatJdbc", false);
    }
    
    @RequestMapping("/tomcatJdbcTest/valid")
    public ModelAndView tomcatDataSourceValidTest() {
        return executeQuery("tomcatJdbc", true);
    }
    
    @RequestMapping("/c3poTest")
    public ModelAndView c3poDataSourceTest() {
        return executeQuery("c3po", false);
    }
    
    @RequestMapping("/c3poTest/valid")
    public ModelAndView c3poDataSourceValidTest() {
        return executeQuery("c3po", true);
    }

    private ModelAndView executeQuery(String connectionPool, boolean valid) {
        List<Integer> list = new ArrayList<Integer>();
        int count = valid ? 10 : 40000;
        for (Integer index = 0 ; index<count ; index++ ) {
            list.add(index);
        }
        if(connectionPool.equals("tomcatJdbc")) {
            empService.getEmployeesByTomcatJdbc(list); 
        } else {
            empService.getEmployeesByC3po(list); 
        }
        return new ModelAndView(SUCCESS_PAGE, "message", SUCCESS_MESSAGE);
    }
    
    @Autowired(required = false)
    public void setEmpService(EmpService empService) {
        this.empService = empService;
    }
}

