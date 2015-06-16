package com.investigateconnectionpool;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.servlet.ServletContext;

@Controller
public class InvestigateController implements ServletContextAware {
    private ServletContext context;
    private static final String SELECT_QUERY = "SELECT NAME FROM EMP WHERE ID IN (:ids)";
    private static final String ERROR_PAGE = "error";
    private static final String SUCCESS_PAGE = "success"; 
    private static final String SUCCESS_MESSAGE = "There is no error";
    
    @RequestMapping("/tomcatJdbcTest")
    public ModelAndView tomcatDataSourceTest() {
        System.out.println("tomcatDataSourceTest");
        return getViewBySelectQueryWithConnectionPool("tomcatJdbcDataSource");
    }
    
    @RequestMapping("/c3poTest")
    public ModelAndView c3poDataSourceTest() {
        System.out.println("c3poDataSourceTest");
        return getViewBySelectQueryWithConnectionPool("c3poDataSource");
    }
    
    public void setServletContext(ServletContext servletContext) {
         this.context = servletContext;
    }
    
    private List<Integer> getIds() {
        List<Integer> ids = new ArrayList<Integer>(1000); //32768
        for (int index = 0; index < 32768; index++) {
            ids.add(index);
        }
        return ids;
    }
    
    private void checkOrCreateTable(DataSource ds) {
        try {
            Connection conn = ds.getConnection();
            DatabaseMetaData metadata = conn.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "emp", null);
            if(resultSet.next()) {
                // table exist
                return;
            } else {
                String createQuery = "CREATE TABLE EMP( ID INT PRIMARY KEY NOT NULL," 
                           + "NAME TEXT NOT NULL,"
                           + "AGE INT NOT NULL,"
                           + "ADDRESS CHAR(50),"
                           + "SALARY REAL );";
                Statement st = conn.createStatement();
                st.execute(createQuery);
                st.close();
                //insert record into table
                insertRecord(ds);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void insertRecord(DataSource ds) {
        String [] queries = {
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (1, 'A', 1, 'ADDESS_1', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (2, 'B', 2, 'ADDESS_2', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (3, 'C', 3, 'ADDESS_3', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (4, 'D', 4, 'ADDESS_4', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (5, 'E', 5, 'ADDESS_5', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (6, 'F', 6, 'ADDESS_6', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (7, 'G', 7, 'ADDESS_7', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (8, 'H', 8, 'ADDESS_8', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (9, 'I', 9, 'ADDESS_9', 1000)",
                "INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (10, 'J', 10, 'ADDESS_10', 1000)",
            };
        Statement st = null;
        try {
            Connection conn = ds.getConnection();
            st= conn.createStatement();
             
            for (String query : queries) {
                st.addBatch(query);
            }
            st.executeBatch();
            st.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        } 
    }
    
    private String exceptionStacktraceToString(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        return baos.toString();
    }
    
    private ModelAndView getViewBySelectQueryWithConnectionPool(String beanName) {
        String message = null;
        try {
            DataSource ds = (DataSource) WebApplicationContextUtils.getWebApplicationContext(context).getBean(beanName);
            checkOrCreateTable(ds);
            NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(ds);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ids", getIds());
            
            List<Map<String, Object>> list = namedTemplate.queryForList(SELECT_QUERY, params);
        } catch (Exception e) {
            message = exceptionStacktraceToString(e);
            return new ModelAndView(ERROR_PAGE, "message", message);
        }
        return new ModelAndView(SUCCESS_PAGE, "message", SUCCESS_MESSAGE);
    }
}

