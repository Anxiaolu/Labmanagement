/*
 * Copyright 2017 huanlu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.sdut.softlab.controller;

import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.model.ItemAccount;
import cn.edu.sdut.softlab.service.ItemAccountFaced;
import cn.edu.sdut.softlab.service.ItemFacade;
import cn.edu.sdut.softlab.service.StuffFacade;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author huanlu
 */
@Named
@RequestScoped
public class ItemLend {

    @Inject
    ItemFacade itemService;

    @Inject
    ItemManager itemManager;
    
    @Inject
    StuffFacade stuffService;
    
    @Inject
    LoginController loginService;
    
    @Inject
    ItemAccountFaced itemAccountService;

    private Integer LendNum;

    public Integer getLendNum() {
        return LendNum;
    }

    public void setLendNum(Integer LendNum) {
        this.LendNum = LendNum;
    }
    
    @Named
    @Produces
    @RequestScoped
    public List<Item> getAllItems() throws Exception {
        return itemManager.getAllItem();
    }

    @Named
    @RequestScoped
    public List<Item> getAvaliableItems() {
        return itemService.getAvailableItems();
    }
    
    @Named
    @RequestScoped
    public String LendItem(Item item){
        loginService.getCurrentUser();
        return null;
    }
    
    @Named
    @RequestScoped
    public List<ItemAccount> findLendRecordByItem(Item item){
        return itemAccountService.findLendRecordByItem(item);
    }
    
    @Named
    @RequestScoped
    public List<ItemAccount> findLendRecorByStuff(){
        return null;
    }
}
