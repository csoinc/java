package com.oyou.jsf2spring3.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.oyou.jsf2spring3.entity.Employee;
import com.oyou.jsf2spring3.mybatis.data.EmployeeDao;

public class SessionFactory {

    private static SqlSessionFactory sqlSessionFactory;
    private static SessionFactory sessionFactory;

    private SessionFactory() {
    }

    public static SessionFactory getInstance() {
        if (sessionFactory == null)
            sessionFactory = new SessionFactory();
        return sessionFactory;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try {
                String resource = "com/oyou/jsf2spring3/mybatis/data/mybatis-config.xml";
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                sqlSessionFactory = builder.build(inputStream);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        return sqlSessionFactory;
    }

    public SqlSessionFactory buildSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try {
                Properties prop = new Properties();
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                InputStream stream = loader.getResourceAsStream("resources/jdbc-config.properties");
                prop.load(stream);
                BasicDataSource ds = new BasicDataSource();
                ds.setDriverClassName(prop.getProperty("dev.driver"));
                ds.setUsername(prop.getProperty("dev.user"));
                ds.setPassword(prop.getProperty("dev.password"));
                ds.setUrl(prop.getProperty("dev.url"));
                TransactionFactory transactionFactory = new JdbcTransactionFactory();
                Environment environment = new Environment("development", transactionFactory, ds);
                Configuration configuration = new Configuration(environment);
                configuration.setLazyLoadingEnabled(true);
                configuration.getTypeAliasRegistry().registerAlias(Employee.class);
                configuration.addMapper(EmployeeDao.class);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        return sqlSessionFactory;
    }

}
