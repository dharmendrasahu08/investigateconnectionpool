package com.investigateconnectionpool;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmpRepo {
    
    public HibernateTemplate tomcatJdbcHibernateTemplate;
    public HibernateTemplate c3poHibernateTemplate;

    public List<Emp> getEmployeesByTomcatJdbc(List<Integer> ids) {
        Session session = tomcatJdbcHibernateTemplate.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Emp.class)
                .add(Restrictions.in("id", ids))
                ;
        return criteria.list();
    }
    
    public List<Emp> getEmployeesByC3po(List<Integer> ids) {
        Session session = c3poHibernateTemplate.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Emp.class)
                .add(Restrictions.in("id", ids))
                ;
        return criteria.list();
    }
    
    public void setTomcatJdbcHibernateTemplate(HibernateTemplate tomcatJdbcHibernateTemplate) {
        this.tomcatJdbcHibernateTemplate = tomcatJdbcHibernateTemplate;
    }
    
    public void setC3poHibernateTemplate(HibernateTemplate c3poHibernateTemplate) {
        this.c3poHibernateTemplate = c3poHibernateTemplate;
    }
}
