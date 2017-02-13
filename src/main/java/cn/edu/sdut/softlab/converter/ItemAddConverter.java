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
package cn.edu.sdut.softlab.converter;

import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.service.ItemFacade;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * 没有用联合主键的方式来确定某一物品,
 * 通过物品名称来确定某一物品
 * @author huanlu
 */
@ManagedBean(name = "itemAddConverterBean")
@FacesConverter(value = "itemAddConverterBean")
public class ItemAddConverter implements Converter, Serializable {

    @Inject
    ItemFacade itemService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.equals("") && value != null) {
            return value;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        if (o instanceof Item) {
            return String.valueOf(((Item) o));
        } else {
            return null;
        }
    }

}
