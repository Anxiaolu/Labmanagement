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
package cn.edu.sdut.softlab.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 *
 * @author huanlu
 */
@Named
@SessionScoped
public class AutocompleteListener {

    private static String COMPLETION_ITEMS_ATTR = "corejsf.completionItems";

    public void valueChanged(ValueChangeEvent e) {
        UIInput input = (UIInput) e.getSource();
        UISelectOne listbox = (UISelectOne) input.findComponent("listbox");

        if (listbox != null) {
            UISelectItems items = (UISelectItems) listbox.getChildren().get(0);
            Map<String, Object> attrs = listbox.getAttributes();
            List<String> newItems = getNewItems((String) input.getValue(),
                    getCompletionItems(items, attrs));
            items.setValue(newItems.toArray());
            setListboxStyle(newItems.size(), attrs);
        }
    }

    private List<String> getNewItems(String inputValueString, String[] completionItems) {
        List<String> newItems = new ArrayList<String>();
        for (String item : completionItems) {
            if (item.toLowerCase().startsWith(inputValueString.toLowerCase())) {
                newItems.add(item);
            }
        }
        return newItems;
    }
    
    /**
     * 将list列表中选中的样式进行修改
     * @param rows
     * @param attrs 
     */
    private void setListboxStyle(int rows, Map<String, Object> attrs) {
        if (rows > 0) {
            Map<String, String> reqParams = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap();
            attrs.put("style", "display:inline; position:absolte;left:"
                    + reqParams.get("x") + "px;" + "top:" + reqParams.get("y") + "px");
        } else {
            attrs.put("style", "display:none;");
        }
    }

    private String[] getCompletionItems(
            UISelectItems items, Map<String, Object> attrs) {
        String[] completionItems = (String[]) attrs.get(COMPLETION_ITEMS_ATTR);
        if (completionItems == null) {
            completionItems = (String[]) items.getValue();
            attrs.put(COMPLETION_ITEMS_ATTR, completionItems);
        }
        return completionItems;
    }

    public void completionItemSelected(ValueChangeEvent e) {
        UISelectOne listbox = (UISelectOne) e.getSource();
        UIInput input = (UIInput) listbox.findComponent("input");
        if (input != null) {
            input.setValue(listbox.getValue());
        }
        Map<String, Object> attrs = listbox.getAttributes();
        attrs.put("style", "display:none");
    }
}
