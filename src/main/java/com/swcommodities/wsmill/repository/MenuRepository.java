package com.swcommodities.wsmill.repository;

import com.swcommodities.wsmill.domain.model.PageMenu;
import com.swcommodities.wsmill.domain.model.SimpleMenu;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swcommodities.wsmill.hibernate.dto.Menu;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    
	@Query("select p.menus from Page p inner join p.authorizations auths where auths.user.id = :userId and auths.permission = 1")
	List<Menu> getAllAllowedMenus(@Param("userId") int userId);
    
    @Query("select distinct m.menu from Menu m where m.menu.menu is null and m.id in :ids")
    List<Menu> getAllParentMenus(@Param("ids") List<Byte> ids);
    
    List<SimpleMenu> findAllMenus(int userId);
    List<PageMenu> findByIds(List<Integer> ids);
    List<Menu> findByMenuIsNull();
    
}
