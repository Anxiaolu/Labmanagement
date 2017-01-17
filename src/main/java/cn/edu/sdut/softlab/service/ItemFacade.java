/*
 * Copyright 2016 Su Baochen and individual contributors by the 
 * @authors tag. See the copyright.txt in the distribution for
 * a full listing of individual contributors.
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

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;

@Stateless
@Named("item")
public class ItemFacade extends AbstractFacade<Item> {

	public ItemFacade() {
		super(Item.class);
	}

	public Item findByItemnameAndParentId(String name, int category) {
		Map<String, Object> parameters = new HashMap<>(0);
		parameters.put("name", name);
		parameters.put("category", category);
		return findSingleByNamedQuery("item.findBynameAndParentId", parameters, Item.class).get();
	}
}
