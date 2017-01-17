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

import cn.edu.sdut.softlab.model.Category;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author huanlu
 */
@ManagedBean(name = "categoryConverterBean")
@FacesConverter(value = "categoryConverter")
public class CategoryConverter implements Converter {

	@PersistenceContext()
	private transient EntityManager em;

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		// This will return the actual object representation
		// of your Category using the value (in your case 52)
		// returned from the client side
		return em.find(Category.class, new Integer(value));
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		// This will return view-friendly output for the dropdown menu
		// return ((Category) o).getId().toString();

		if (!(o instanceof Category))
			return null;
		return String.valueOf(((Category) o).getId());
	}

}
