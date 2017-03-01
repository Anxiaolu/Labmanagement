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
import cn.edu.sdut.softlab.model.ItemLendAccount;
import cn.edu.sdut.softlab.service.ItemAccountFaced;
import cn.edu.sdut.softlab.service.ItemFacade;
import cn.edu.sdut.softlab.service.StuffFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
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

    private ItemAccount itemAccount = new ItemAccount();
    
    private ItemLendAccount itemLendAccount = new ItemLendAccount();

    public ItemAccount getItemAccount() {
        return itemAccount;
    }

    public void setItemAccount(ItemAccount itemAccount) {
        this.itemAccount = itemAccount;
    }

    public ItemLendAccount getItemLendAccount() {
        return itemLendAccount;
    }

    public void setItemLendAccount(ItemLendAccount itemLendAccount) {
        this.itemLendAccount = itemLendAccount;
    }

    public List<Item> getAllItems() throws Exception {
        return itemManager.getAllItem();
    }

    /**
     * 简单的将ItemAccount和LendNum封装成一个ItemLendAccount对象并返回相应的list集合
     * @return 
     */
    public List<ItemLendAccount> availableItemLendAccount() {
        List<Item> availiableitemList = itemService.getAvailableItems();
        List<ItemLendAccount> availableItemLendAccounts = new ArrayList<>();
        for(Item i :availiableitemList){
            availableItemLendAccounts.add(new ItemLendAccount(i));
        }
        return availableItemLendAccounts;
    }

    public String lendItem(ItemLendAccount lendAccount) throws Exception {
        itemAccount.setStuff(loginService.getCurrentUser());
        itemAccount.setItem(lendAccount.getItem());
        itemAccount.setFlag("OUT");
        Integer LendNum = lendAccount.getLendNum();
        Integer itemnum = itemService.findByName(lendAccount.getItem().getName()).getNumTotal();  //调用之前先查询选中物品对应的库存
        System.out.println("前台选中物品剩余库存：" + itemnum + "要借用的件数：" + LendNum);
        if (LendNum > itemnum || LendNum <= 0) {
            facesContext.addMessage(null, new FacesMessage("您输入的物品数量不合法！"));
        } else if (itemnum == LendNum) {
            itemService.findByName(lendAccount.getItem().getName()).setNumTotal(0);
            itemService.findByName(lendAccount.getItem().getName()).setStatus("NOT_AVALIABLE");
        } else if (itemnum > LendNum) {
            itemService.findByName(lendAccount.getItem().getName()).setNumTotal(itemnum - LendNum);
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
    
//    public String lendItem(Item item) throws Exception {
//        itemAccount.setStuff(loginService.getCurrentUser());
//        itemAccount.setItem(item);
//        itemAccount.setFlag("OUT");
//        Integer itemnum = itemService.findByName(item.getName()).getNumTotal();
//        System.out.println("前台选中物品剩余库存：" + itemnum + "要借用的件数：" + LendNum);
//        if (this.getLendNum() > itemnum || this.getLendNum() <= 0) {
//            facesContext.addMessage(null, new FacesMessage("您输入的物品数量不合法！"));
//        } else if (itemnum == this.getLendNum()) {
//            itemService.findByName(item.getName()).setNumTotal(0);
//            itemService.findByName(item.getName()).setStatus("NOT_AVALIABLE");
//        } else if (itemnum > this.getLendNum()) {
//            itemService.findByName(item.getName()).setNumTotal(itemnum - this.getLendNum());
//        }
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        itemAccount.setTimeCheck(df.parse(df.format(new Date())));
//        try {
//            utx.begin();
//            itemAccountService.create(itemAccount);
//            logger.log(Level.INFO, "Added {0}", itemAccount);
//            return null;
//        }
//        finally {
//            utx.commit();
//        }
//    }

//    public String returnItem(Item item) throws Exception {
//        itemAccount.setStuff(loginService.getCurrentUser());
//        itemAccount.setItem(item);
//        itemAccount.setFlag("In");
//        Integer itemnum = itemService.findByName(item.getName()).getNumTotal(); //查询前台选中的物品所剩余的库存
//        ItemAccount returnAccount = itemAccountService
//                .findItemAccountByStuffAndItemAndFlag(loginService.getCurrentUser(), item, "OUT");  //根据用户和物品和标记查询借用记录
//        Item returnItem = returnAccount.getItem();
//        returnItem.setNumTotal(itemnum + LendNum);
//        if (returnItem.getStatus().equals("NOT_AVALIABLE") && LendNum > 0) {
//            returnItem.setStatus("AVALIABLE");
//        }
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        itemAccount.setTimeCheck(df.parse(df.format(new Date())));
//        try {
//            utx.begin();
//            itemAccountService.create(itemAccount);
//            logger.log(Level.INFO, "Added {0}", itemAccount);
//            return null;
//        }
//        finally {
//            utx.commit();
//        }
//    }

    public List<ItemAccount> findLendRecordByItem(Item item) {
        return itemAccountService.findLendRecordByItem(item);
    }

    public List<ItemAccount> findLendRecorByStuff() {
        return null;
    }
}
