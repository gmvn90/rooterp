/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import com.swcommodities.wsmill.domain.model.PageMenu;
import com.swcommodities.wsmill.domain.model.SimpleMenu;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 *
 * @author macOS
 */

@Component
public class MenuRepositoryImpl {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    //@Query(value = "select m.id, m.parent_id from menu m inner join Page p on m.url_id = p.id inner join authorization a on p.id = a.page_id where a.user_id = :userId and a.permission = 1", nativeQuery = true)
    
    private Integer getIntegerFromByte(Object value) {
        if(value == null) {
            return null;
        }
        return Integer.valueOf(String.valueOf(value));
    }
    
    public List<SimpleMenu> findAllMenus(int userId) {
        Session session = entityManager.unwrap(Session.class);
        Query q = session.createSQLQuery("select m.id, m.parent_id, m.`is_default` from menu m inner join Page p on m.url_id = p.id inner join authorization a on p.id = a.page_id where a.user_id = :userId and a.permission = 1");
        q.setParameter("userId", userId);
        List<Object> result = q.list();
        List<SimpleMenu> resultObject = new ArrayList<>();
        for(Object itemObject: result) {
            Object[] item = (Object[]) itemObject;
            resultObject.add(new SimpleMenu((int) getIntegerFromByte((Byte)item[0]), getIntegerFromByte((Byte)item[1]), (Boolean) item[2]));
        }
        return resultObject;
    }
    
    public List<PageMenu> findByIds(List<Integer> ids) {
        Session session = entityManager.unwrap(Session.class);
        Query q = session.createQuery("select new com.swcommodities.wsmill.domain.model.PageMenu(m.id, m.menu.id, m.order, p.url, m.default_, m.name, m.showInMainMenu) from Menu m left join m.page p where m.id in (:ids)");
        q.setParameterList("ids", ids);
        List<PageMenu> result = q.list();
        return result;
    }
    
}
