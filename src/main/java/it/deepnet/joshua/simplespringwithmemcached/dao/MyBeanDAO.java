package it.deepnet.joshua.simplespringwithmemcached.dao;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;
import it.deepnet.joshua.simplespringwithmemcached.beans.MyBean;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

/**
 *
 * @author mcamangi
 */
@Service
public class MyBeanDAO extends JdbcDaoSupport implements DAO<MyBean, Long>{
    
    private static final String NS_NAME = "MyBean";
    
    private static final String QUERY_FOR_INSERT = "INSERT INTO myBean(id, description) VALUES(?,?)";
    private static final String QUERY_FOR_UPDATE = "UPDATE myBean SET description=? WHERE id=?";
    private static final String QUERY_FOR_DELETE = "DELETE FROM myBean WHERE id=?";
    private static final String QUERY_FOR_SELECT = "SELECT id, description FROM myBean WHERE id=?";
    private static final String QUERY_FOR_ALL    = "SELECT * FROM MyBean";
    private static final String QUERY_FOR_CLEAN  = "TRUNCATE TABLE MyBean";

    public MyBeanDAO() {
    }
    
    //Does it need to use the cache?
    @Override
    public List<Map<String, Object>> list() {
        return getJdbcTemplate().queryForList(QUERY_FOR_ALL);
    }

    @Override
    @ReadThroughSingleCache(namespace = NS_NAME, expiration = 3600)
    public MyBean get(@ParameterValueKeyProvider final Long pk) {
        return getJdbcTemplate().queryForObject(QUERY_FOR_SELECT, new ParameterizedRowMapper<MyBean>(){

            public MyBean mapRow(ResultSet rs, int i) throws SQLException {
                MyBean temp = new MyBean();
                temp.setId(pk);
                return temp;
            }
        
        }, pk);
    }

    @Override
    @UpdateSingleCache(namespace = NS_NAME, expiration = 3600)
    public MyBean create(@ParameterDataUpdateContent @ParameterValueKeyProvider MyBean entity) {
        getJdbcTemplate().update(QUERY_FOR_INSERT, entity.getId(), entity.getDescription());
        return entity;
    }

    @Override
    @UpdateSingleCache(namespace = NS_NAME, expiration = 3600)
    public MyBean update(@ParameterDataUpdateContent @ParameterValueKeyProvider MyBean entity) {
        getJdbcTemplate().update(QUERY_FOR_UPDATE, new Object[]{entity.getDescription(), entity.getId()});
        return entity;
    }

    @Override
    @InvalidateSingleCache(namespace = NS_NAME)
    public void delete(@ParameterValueKeyProvider Long pk) {
        getJdbcTemplate().update(QUERY_FOR_DELETE, pk);
    }

    //TODO Invalidate all
    @Override
    public void cleanAll() {
        getJdbcTemplate().execute(QUERY_FOR_CLEAN);
    }
    
}
