package com.oyou.jsf2spring3.mybatis;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.oyou.jsf2spring3.entity.Employee;
import com.oyou.jsf2spring3.mybatis.data.EmployeeDao;
import com.oyou.jsf2spring3.mybatis.data.EmployeeMapper;

public class MybatisTest extends TestCase {
    private static final Log log = LogFactory.getLog(MybatisTest.class);

    @Override
    public void setUp() {

    }

    public void ntestSessionFactory() {
        try {
            String resource = "com/oyou/jsf2spring3/mybatis/data/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = builder.build(inputStream);
            SqlSession session = factory.openSession();
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee emp = mapper.selectEmployee(1);
            log.debug("Firstname: " + emp.getFirstname());
            log.debug(" Lastname: " + emp.getLastname());
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void ntestXMLConfig() {
        SqlSession session = SessionFactory.getInstance().getSqlSessionFactory().openSession();
        com.oyou.jsf2spring3.mybatis.data.EmployeeMapper mapper = session.getMapper(com.oyou.jsf2spring3.mybatis.data.EmployeeMapper.class);
        Employee emp = mapper.selectEmployee(1);
        log.debug("Firstname: " + emp.getFirstname());
        log.debug(" Lastname: " + emp.getLastname());
    }

    public void testClassConfig() {
        SqlSession session = SessionFactory.getInstance().buildSqlSessionFactory().openSession();
        EmployeeDao mapper = session.getMapper(EmployeeDao.class);
        Employee emp = mapper.selectEmployee(1);
        log.debug("Firstname: " + emp.getFirstname());
        log.debug(" Lastname: " + emp.getLastname());
    }
    
}
