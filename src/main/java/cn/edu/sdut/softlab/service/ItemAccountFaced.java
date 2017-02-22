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
package cn.edu.sdut.softlab.service;

import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.model.ItemAccount;
import cn.edu.sdut.softlab.model.Stuff;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author huanlu
 */
@Named("itemaccount")
@Stateless
public class ItemAccountFaced extends AbstractFacade<ItemAccount>{
    
    @Inject
    ItemFacade itemService;
    
    public ItemAccountFaced() {
        super(ItemAccount.class);
    }
    
    public int ItemAccountCount(){
        return count();
    }
    
    public List<ItemAccount> findLendRecordByItem(Item item){
        Integer itemid = itemService.findItemid(item);
        List<ItemAccount> itemAccounts = this.findAll(ItemAccount.class);
        List<ItemAccount> correctlist = new ArrayList<>();
        for(ItemAccount it:itemAccounts){
            if (it.getItem().equals(itemid)) {
                correctlist.add(it);
            }
        }
        return correctlist;
    }
    
    public ItemAccount findItemAccountByStuffAndItemAndFlag(Stuff stuff,Item item,String flag){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("stuff", stuff);
        parameters.put("item", item);
        parameters.put("flag", flag);
        return findSingleByNamedQuery("ItemAccount.findByStuffAndItemAndFlag", parameters, ItemAccount.class).get();
    }
    
}
