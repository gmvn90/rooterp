package com.swcommodities.wsmill.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.domain.model.LevelMenu;
import com.swcommodities.wsmill.domain.model.PageMenu;
import com.swcommodities.wsmill.domain.model.SimpleMenu;
import com.swcommodities.wsmill.hibernate.dto.Authorization;
import com.swcommodities.wsmill.hibernate.dto.AuthorizationId;
import com.swcommodities.wsmill.hibernate.dto.Menu;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.AuthorizationRepository;
import com.swcommodities.wsmill.repository.MenuRepository;
import com.swcommodities.wsmill.repository.PageRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

@Service("application.service.MenuService")
@Transactional(readOnly = true)
public class MenuService {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    PageRepository pageRepository;
    
    @Autowired AuthorizationRepository authorizationRepository;

    private Map<Integer, Integer> getRelationMap(int userId) {
        Map<Integer, Integer> map = new HashMap<>();
        for (SimpleMenu idAndParent : menuRepository.findAllMenus(userId)) {
            map.put(idAndParent.getMenu(), idAndParent.getParent());
        }
        Map<Integer, Integer> temp = new HashMap<>();
        for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext();) {
            Integer item = iterator.next();
            if (!map.containsKey(map.get(item))) {
                temp.put(map.get(item), 0);
            }
        }
        map.putAll(temp);
        return map;
    }

    private int getPageIdOfUrl(String url, Set<Integer> l2, Set<Integer> l3) {
        int _pageId = 0;
        List<Page> pages = pageRepository.findByUrl(url);
        for (Page p : pages) {
            int currentMenu = p.getMenus().iterator().next().getId();
            if (l3.contains(currentMenu)) {
                _pageId = currentMenu;
            } else if (l2.contains(currentMenu)) {
                _pageId = currentMenu;
            }
        }
        int pageId = _pageId;
        return pageId;
    }

    private Map<Integer, PageMenu> getMenuMap(Set<Integer> l1, Set<Integer> l2, Set<Integer> l3) {
        Set<Integer> needToGetFromDb = new HashSet<>();
        needToGetFromDb.addAll(new HashSet<>(l1));
        needToGetFromDb.addAll(new HashSet<>(l2));
        needToGetFromDb.addAll(new HashSet<>(l3));
        List<PageMenu> pageMenus = menuRepository.findByIds(new ArrayList<>(needToGetFromDb));
        Map<Integer, PageMenu> menuMap = pageMenus.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
        return menuMap;
    }

    private Map<String, Set<Integer>> getShowMenu(int pageId, Set<Integer> l2, Set<Integer> l3, Map<Integer, Integer> relationMap) {
        Set<Integer> showL2 = new HashSet<>();
        Set<Integer> showL3 = new HashSet<>();
        if (l3.contains(pageId)) {
            showL3.add(pageId);
            showL3.addAll(l3.stream().filter(i -> relationMap.get(i).equals(relationMap.get(pageId))).collect(Collectors.toSet()));
            showL2.add(relationMap.get(pageId));
            showL2.addAll(l2.stream().filter(i -> relationMap.get(i).equals(relationMap.get(relationMap.get(pageId)))).collect(Collectors.toSet()));
        } else {
            showL2.add(pageId);
            showL2.addAll(l2.stream().filter(i -> relationMap.get(i).equals(relationMap.get(pageId))).collect(Collectors.toSet()));
            showL3.addAll(l3.stream().filter(i -> showL2.contains(relationMap.get(i))).collect(Collectors.toSet()));
        }

        Map<String, Set<Integer>> res = new HashMap<>();
        res.put("showL2", showL2);
        res.put("showL3", showL3);
        return res;
    }
    
    // because we dont have default, we force to use the first child url for parent
    private void setUrlAndSelectedOfParentBasedOnFirstChild(int parentId, Set<Integer> listMenu, Map<Integer, PageMenu> menuMap, Map<Integer, Integer> parentChildRelation) {
        menuMap.get(parentId).setUrl(listMenu.stream()
            .filter(x -> parentChildRelation.get(x) == parentId)
            .map(x -> menuMap.get(x))
            .sorted(Comparator.comparing(PageMenu::getOrder)).findFirst().map(x -> x.getUrl()).orElse(""));
        
    }

    private void setUrlAndSelectedOfParentBasedOnDefaultChild(int pageId, Set<Integer> listMenu, Map<Integer, PageMenu> menuMap, Map<Integer, Integer> parentChildRelation) {
        listMenu.stream().forEach(id -> {
            PageMenu thisMenu = menuMap.get(id);
            PageMenu parentMenu = menuMap.get(parentChildRelation.get(id));
            if (id == pageId) {
                thisMenu.setSelected(true);
            }
            if (thisMenu.getIsDefault()) {
                // if parent link is not null then set, else leave it
                if (StringUtils.isEmpty(parentMenu.getUrl())) {
                    parentMenu.setUrl(thisMenu.getUrl());
                }
            }
            if (thisMenu.getSelected()) {
                parentMenu.setSelected(true);
            }
            if(StringUtils.isEmpty(parentMenu.getUrl())) {
                setUrlAndSelectedOfParentBasedOnFirstChild(parentMenu.getId(), listMenu, menuMap, parentChildRelation);
            }
        });
    }
    
    public LevelMenu getAllMenus(int userId, String url) {
        Map<Integer, Integer> parentChildRelation = getRelationMap(userId);
        Set<Integer> l1 = parentChildRelation.entrySet().stream().filter(entry -> entry.getValue() == 0).map(entry -> entry.getKey()).collect(Collectors.toSet());
        Set<Integer> l2 = parentChildRelation.entrySet().stream().filter(entry -> l1.contains(entry.getValue())).map(entry -> entry.getKey()).collect(Collectors.toSet());
        Set<Integer> l3 = parentChildRelation.entrySet().stream().filter(entry -> l2.contains(entry.getValue())).map(entry -> entry.getKey()).collect(Collectors.toSet());
        int pageId = getPageIdOfUrl(url, l2, l3);
        if (pageId == 0) {
            return new LevelMenu(0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
        Map<Integer, PageMenu> menuMap = getMenuMap(l1, l2, l3);
        setUrlAndSelectedOfParentBasedOnDefaultChild(pageId, l3, menuMap, parentChildRelation);
        setUrlAndSelectedOfParentBasedOnDefaultChild(pageId, l2, menuMap, parentChildRelation);
        
        List<PageMenu> l1Menus = l1.stream().map(menu -> menuMap.get(menu)).sorted(Comparator.comparing(PageMenu::getOrder)).collect(Collectors.toList());
        Map<String, Set<Integer>> res = getShowMenu(pageId, l2, l3, parentChildRelation);
       
        List<PageMenu> l2Menus = res.get("showL2").stream().filter(menu -> menuMap.get(menu).getShowInMainMenu()).map(x -> menuMap.get(x))
            .sorted(Comparator.comparing(PageMenu::getOrder)).collect(Collectors.toList());
        List<PageMenu> l3Menus = res.get("showL3").stream().filter(menu -> menuMap.get(menu).getShowInMainMenu()).map(x -> menuMap.get(x))
            .sorted(Comparator.comparing(PageMenu::getOrder)).collect(Collectors.toList());
        
        // for client to show, because client will loop this
        for (PageMenu menu : l3Menus) {
            menuMap.get(menu.getParent()).addChild(menu);
        }
        return new LevelMenu(pageId, l1Menus, l2Menus, l3Menus);
    }

    public List<Menu> getAllMenus() {
        List<Menu> menus = menuRepository.findByMenuIsNull();
        menus.sort(Comparator.comparing(Menu::getId));
        return menus;
    }

    // with pages, we have two types: page and button, real pages are the pages with url;
    public List<Page> getRealPages() {
        List<Page> pages = new ArrayList<>(pageRepository.findByUrlIsNotNull().stream().filter(p -> !p.getUrl().equals("")).collect(Collectors.toList()));
        Comparator<Page> c = (a, b) -> a.getName().compareToIgnoreCase(b.getName());
        pages.sort(c);
        return pages;
    }

    public List<Page> getAllPages() {
        List<Page> pages = new ArrayList<>(pageRepository.findByPageIsNull());
        Comparator<Page> c = (a, b) -> a.getName().compareToIgnoreCase(b.getName());
        pages.sort(c);
        return pages;
    }
    
    public void updatePageAndMenuForUsers() {
        List<String> userNames = Arrays.asList("minhdn", "huong", "whs_cuong", "JWB");
        List<Integer> userIds = Arrays.asList(10, 15, 12, 4);
        List<List<String>> menus = new ArrayList<>();
        menus.add(Arrays.asList("Shipping advice", "shipping-instruction/{id}/shipping_advice"));
        menus.add(Arrays.asList("Loading report", "shipping-instruction/{id}/loading_report"));
        menus.add(Arrays.asList("Claim detail", "shipping-instruction/{siRef}/claim-detail/{refNumber}"));
        menus.add(Arrays.asList("Sample sent", "shipping-instruction/{siRef}/sample-sent/{ssRef}"));
        int shipping_list_id = 4;
        int shipping_menu_id = 11;
        Menu shipping = new Menu(shipping_menu_id);
        Page shippingPage = pageRepository.findOne(shipping_list_id);
        
        menus.forEach(item -> {
            Page page = new Page(item.get(0), item.get(1));
            page.setPage(shippingPage);
            page.setId(pageRepository.findTopByOrderByIdDesc().getId() + 1);
            
            pageRepository.save(page);
            
            Menu menu = new Menu(shipping, page, item.get(0), false, Byte.valueOf("1"), new HashSet<Menu>());
            menuRepository.save(menu);
            userIds.forEach(id -> {
                Authorization auth = new Authorization(new AuthorizationId(id, page.getId()), new User(id), page, Byte.valueOf("1"));
                authorizationRepository.save(auth);
            });
            
        });
        
    }

}
