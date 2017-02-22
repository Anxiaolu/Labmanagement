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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;

/**
 *
 * @author huanlu
 */
@Named
@RequestScoped
public class ItemLend {

    @Inject
    private transient Logger logger;

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

    @Inject
    FacesContext facesContext;

    @Inject
    private UserTransaction utx;

    private Integer LendNum;
    private ItemAccount itemAccount = new ItemAccount();

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
    public List<Item> getAvailableItems() {
        return itemService.getAvailableItems();
    }

    public String LendItem(Item item) throws Exception {
        itemAccount.setStuff(loginService.getCurrentUser());
        itemAccount.setItem(item);
        itemAccount.setFlag("OUT");
        Integer itemnum = itemService.findByName(item.getName()).getNumTotal();
        if (LendNum > itemnum || LendNum <= 0) {
            facesContext.addMessage(null, new FacesMessage("您输入的物品数量不合法！"));
        } else if (itemnum == LendNum) {
            itemService.findByName(item.getName()).setNumTotal(0);
            itemService.findByName(item.getName()).setStatus("NOT_AVALIABLE");
        } else if (itemnum > LendNum) {
            itemService.findByName(item.getName()).setNumTotal(itemnum - LendNum);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        itemAccount.setTimeCheck(df.parse(df.format(new Date())));
        try {
            utx.begin();
            itemAccountService.create(itemAccount);
            logger.log(Level.INFO, "Added {0}", itemAccount);
            return null;
        }
        finally {
            utx.commit();
        }
    }

    public String ReturnItem(Item item) throws Exception {
        itemAccount.setStuff(loginService.getCurrentUser());
        itemAccount.setItem(item);
        itemAccount.setFlag("In");
        Integer itemmnum = itemService.findByName(item.getName()).getNumTotal(); //查询前台选中的物品所剩余的库存
        ItemAccount returnAccount = itemAccountService
                .findItemAccountByStuffAndItemAndFlag(loginService.getCurrentUser(), item , "OUT");  //根据用户和物品和标记查询借用记录
        Item returnItem = returnAccount.getItem();
        returnItem.setNumTotal(returnItem.getNumTotal() + LendNum);
        if (returnItem.getStatus().equals("NOT_AVALIABLE") && LendNum > 0) {
            returnItem.setStatus("AVALIABLE");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        itemAccount.setTimeCheck(df.parse(df.format(new Date())));
        try {
            utx.begin();
            itemAccountService.create(itemAccount);
            logger.log(Level.INFO, "Added {0}", itemAccount);
            return null;
        }
        finally {
            utx.commit();
        }
    }

    @Named
    @RequestScoped
    public List<ItemAccount> findLendRecordByItem(Item item) {
        return itemAccountService.findLendRecordByItem(item);
    }

    @Named
    @RequestScoped
    public List<ItemAccount> findLendRecorByStuff() {
        return null;
    }
}
