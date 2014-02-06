package com.oyou.jsf2spring3.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.oyou.jsf2spring3.entity.Employee;
import com.oyou.jsf2spring3.mybatis.data.EmployeeDao;

@ContextConfiguration("file:**/applicationContext.xml")
public class SpringJunitTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final Log log = LogFactory.getLog(SpringJunitTest.class);

    @Autowired
    protected EmployeeDao employeeDao;

    @Test
    public void employeeTest() {
        Employee emp = employeeDao.selectEmployee(1);
        log.debug("Firstname: " + emp.getFirstname());
        log.debug(" Lastname: " + emp.getLastname());
    }
}