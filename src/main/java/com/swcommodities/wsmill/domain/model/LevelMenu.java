package com.swcommodities.wsmill.domain.model;

import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.Menu;

public class LevelMenu {
	
	private final int currentPage;
	private final List<PageMenu> menusLevel1;
	private final List<PageMenu> menusLevel2;
	private final List<PageMenu> menusLevel3;
	
	public int getCurrentPage() {
		return currentPage;
	}

    public List<PageMenu> getMenusLevel1() {
        return menusLevel1;
    }

    public List<PageMenu> getMenusLevel2() {
        return menusLevel2;
    }

    public List<PageMenu> getMenusLevel3() {
        return menusLevel3;
    }

    public LevelMenu(int currentPage, List<PageMenu> menusLevel1, List<PageMenu> menusLevel2, List<PageMenu> menusLevel3) {
        this.currentPage = currentPage;
        this.menusLevel1 = menusLevel1;
        this.menusLevel2 = menusLevel2;
        this.menusLevel3 = menusLevel3;
    }
	
    
	
	

	
}
