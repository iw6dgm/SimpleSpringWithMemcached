/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.deepnet.joshua.simplespringwithmemcached.dao;

import java.util.List;
import java.util.Map;

/**
 * DAO interface: any entityDAO can implement this interface
 *
 * @author mcamangi
 */
public interface DAO<T, PK> {

    List<Map<String, Object>> list();

    T get(PK pk);

    T create(T entity);

    T update(T entity);

    void delete(PK pk);

    void cleanAll();
}
