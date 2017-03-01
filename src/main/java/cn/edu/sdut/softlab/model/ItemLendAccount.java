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
package cn.edu.sdut.softlab.model;

import javax.enterprise.context.RequestScoped;

/**
 * 将借用记录和借用数量重新封装为一个, 便于使用
 *
 * @author huanlu
 */
@RequestScoped
public class ItemLendAccount {

    private Item item;
    private Integer lendNum;

    public ItemLendAccount() {
    }

    public ItemLendAccount(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getLendNum() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~LendNum：" + lendNum);
        return lendNum;
    }

    public void setLendNum(Integer lendNum) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + lendNum);
        this.lendNum = lendNum;
    }

}
