/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.deepnet.joshua.simplespringwithmemcached.beans;

import com.google.code.ssm.api.CacheKeyMethod;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * Simple bean
 * @author mcamangi
 */
public class MyBean implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private String description;

    public MyBean(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public MyBean() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyBean other = (MyBean) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MyBean{" + "id=" + id + ", description=" + description + '}';
    }
    
    @CacheKeyMethod
    public String chacheKey() {
        return String.valueOf(hashCode());
    }
    
}
