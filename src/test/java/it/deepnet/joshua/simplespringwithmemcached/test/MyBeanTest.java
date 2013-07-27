/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.deepnet.joshua.simplespringwithmemcached.test;

import it.deepnet.joshua.simplespringwithmemcached.beans.MyBean;
import it.deepnet.joshua.simplespringwithmemcached.dao.DAO;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
        
/**
 * Simple test for MyBest bean and its related DAO
 * @author mcamangi
 */
@ContextConfiguration(locations = "classpath:spring.xml", loader=ContextLoader.class)
public class MyBeanTest extends AbstractJUnit4SpringContextTests  {
    
    //ApplicationContext context;
    @Autowired
    DataSource dataSource;
    @Autowired
    MyBean bean;
    @Autowired
    MyBean bean2;
    @Autowired
    DAO myBeanDAO;
    
    
    public MyBeanTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            jdbcTemplate.execute("CREATE TABLE myBean (id BIGINT NOT NULL, description CHAR(100), PRIMARY KEY (id))");            
        } catch(DataIntegrityViolationException e) {
            
        }
    }
    
    @After
    public void tearDown() throws Exception {

    }
    
    @Test
    public void test() {
        assertNotNull(bean);
    }
    @Test
    public void testDAO() {
        assertNotNull(myBeanDAO);
    }
    
    @Test
    public void testCreate() {
        assertNotNull(bean);
        assertNotNull(myBeanDAO);
        
        bean.setId(Long.MIN_VALUE);
        bean.setDescription("DeepThought");
        
        MyBean newBean = (MyBean)myBeanDAO.create(bean);
        assertEquals(bean.getId(), newBean.getId());
        assertEquals(bean.getDescription(), newBean.getDescription());
        
        MyBean retreivedBean = (MyBean)myBeanDAO.get(bean.getId());
        assertNotNull(retreivedBean);
        assertEquals(bean.getId(), retreivedBean.getId());
    }
    
    @Test
    public void testDelete() {
        bean.setId(Long.MAX_VALUE);
        bean.setDescription("DeepThought");
        MyBean newBean = (MyBean)myBeanDAO.create(bean);
        myBeanDAO.delete(newBean.getId());
        try {
            MyBean retreivedBean = (MyBean)myBeanDAO.get(bean.getId());
        } catch(EmptyResultDataAccessException e) {
            assert true;
        }
        
    }
    
    @Test
    public void testList() {
        bean.setId(42L);
        bean.setDescription("DeepThought");
        myBeanDAO.create(bean);
        
        bean2.setId(1997L);
        bean2.setDescription("DeepBlue");
        myBeanDAO.create(bean2);
        
        List<Map<String, Object>> list = myBeanDAO.list();
        assertNotNull(list);
        assert list.size()>0;
        
        myBeanDAO.cleanAll();
        list = myBeanDAO.list();
        assertEquals(list.size(), 0);
    }
}
