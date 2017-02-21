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
package cn.edu.sdut.softlab.converterandvalidator;

import cn.edu.sdut.softlab.model.Category;
import cn.edu.sdut.softlab.service.CategoryFacade;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author huanlu
 */
@ManagedBean(name = "categoryConverterBean")
@FacesConverter(forClass = Category.class, value = "categoryConverter")
public class CategoryConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	// private static final Logger logger =
	// Logger.getLogger(CategoryConverter.class.getName());

	@Inject
	CategoryFacade categoryservice;

	
	//@PersistenceContext() private transient EntityManager em;
	 

	@Inject
	FacesContext facescontext;

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) throws ConverterException {

		if (!value.equals("") && value != null) {
			//int Catid = Integer.parseInt(value);
                        String Catname = value;
			System.out.println("前台返回的实体名字" + Catname);
			Category category = categoryservice.findCategoryByName(Catname);
			System.out.println("查询到的实体：" + category.getId() + "，名字：" + category.getName());
                        int categoryid = category.getId();
			return categoryid;
		}
		return null;
	}

      
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		if (o instanceof Category)
			return String.valueOf(((Category) o));
		else {
			return null;
		}
	}

}
