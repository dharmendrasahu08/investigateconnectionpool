package com.investigateconnectionpool;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpService {

    private EmpRepo empRepo;
    
    @Transactional(readOnly=true, value="tomcatJdbcTransactionManager")
    public List<Emp> getEmployeesByTomcatJdbc(List<Integer> ids) {
         return empRepo.getEmployeesByTomcatJdbc(ids);
    }
    
    @Transactional(readOnly=true, value="c3poTransactionManager")
    public List<Emp> getEmployeesByC3po(List<Integer> ids) {
         return empRepo.getEmployeesByC3po(ids);
    }
    
    public void setEmpRepo(EmpRepo empRepo) {
        this.empRepo = empRepo;
    }
}
