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

import cn.edu.sdut.softlab.model.Category;
import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.model.QueryCondition;
import cn.edu.sdut.softlab.model.QueryParameter;
import static cn.edu.sdut.softlab.model.QueryParameter.QueryOperateType.Equal;
import cn.edu.sdut.softlab.service.CategoryFacade;
import cn.edu.sdut.softlab.service.ItemFacade;
import cn.edu.sdut.softlab.service.ItemQueryFacade;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;

/**
 *
 * @author huanlu
 */
@Named("itemQueryManager")
@RequestScoped
public class ItemQueryManager {

    @Inject
    Logger logger;

    @Inject
    UserTransaction utx;

    @Inject
    ItemQueryFacade queryService;

    @Inject
    ItemFacade itemService;

    @Inject
    CategoryFacade categoryService;

    @Inject
    FacesContext facesContext;
    
    private List<Item> filterItems;

    public List<Item> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Item> filterItems) {
        this.filterItems = filterItems;
    }

    private Integer categoryid;
    private String code;
    private String status;
    private String dateBought;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateBought() {
        return dateBought;
    }

    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }

    public List<Item> findAllItem(){
        return itemService.findAll(Item.class);
    }
    
    public List<Category> findAllCategorys(){
        List<Category> categorys =  new ArrayList<>();
        for(Item i: this.findAllItem()){
            if (categorys.contains(i.getCategory())) {
                continue;
            }
            else{
                categorys.add(i.getCategory());
            }
        }
        return categorys;
    }
    
    public List<String> findAllCategoryName(){
        List<String> categorys =  new ArrayList<>();
        for(Category c: categoryService.findAll(Category.class)){
            categorys.add(c.getName());
        }
        return categorys;
    }
    
    /**
     * 用于检测在筛选物品的时候输入的日期不合法
     *
     * @param fc
     * @param component
     * @param value //前台传入的日期参数
     */
    public void validateQueryDate(FacesContext fc, UIComponent component, Object value) {
        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
        if (!((String) value).contains("-")) {
            throw new ValidatorException(new FacesMessage("您输入的日期不合法，请按照（年-月-日 如：2017-02-12）!"));
        }
        else {
            try {
                formatdate.parse(((String) value));
            } catch (Exception e) {
                throw new ValidatorException(new FacesMessage("日期验证错误，您输入的日期可能不合法！"));
            }
            int[] date = {0};
            String[] dateString = ((String)value).split("-");
            for(int i=0;i<3;i++){
                date[i] = Integer.parseInt(dateString[i]);
            }
            int y = date[0];int m = date[1]; int d = date[2];
            if (isValidate(y, m, d)) {
                throw new ValidatorException(new FacesMessage("您输入的日期不合法"));
            }
        }
    }

    public static boolean isValidate(int y, int m, int d) {
        if (d < 1 || m < 1 || m > 12) {
            return false;
        }
        switch (m) {
            case 2:
                if (isLeapYear(y)) {
                    return d <= 29;
                } else {
                    return d <= 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return d <= 30;
            default:
                return d <= 31;
        }
    }

    public static boolean isLeapYear(int y) {
        return y % 4 == 0 && (y % 400 == 0 || y % 100 != 0);
    }

    /**
     * 获取前台选中的筛选条件, 根据所选中的条件进行查询对象的生成
     *
     * @return
     * @throws Exception
     */
    public QueryCondition pushQueryItemParameters() throws Exception {
        //形成参数列表
        List<QueryParameter> parameters = new ArrayList<>();
        if (!this.getCategoryid().equals("")) {
            QueryParameter qp_category = new QueryParameter("category", categoryService.
                    findCategoryById(this.getCategoryid()), Equal);
            parameters.add(qp_category);
        }
        if (!this.getCode().equals("")) {
            QueryParameter qp_code = new QueryParameter("code", this.getCode(), Equal);
            parameters.add(qp_code);
        }
        if (!this.getDateBought().equals("")) {
            QueryParameter qp_datebought = new QueryParameter("dateBought", this.getDateBought(), Equal);
            parameters.add(qp_datebought);
        }
        if (!this.getStatus().equals("")) {
            QueryParameter qp4_status = new QueryParameter("status", this.getStatus(), Equal);
            parameters.add(qp4_status);
        }

        for (QueryParameter q : parameters) {
            System.out.println("所选中的属性参数列表" + q.ParameterName + "," + q.ParameterType + "," + q.ParameterValue);
        }

        //生成查询对象实体
        QueryCondition qcd = new QueryCondition("Item", parameters);
        return qcd;
    }

    public List<Item> init() {
        return itemService.findAll(Item.class);
    }

    /**
     * 处理页面下方的Item对象列表的显示
     *
     * @return 符合条件的筛选完成地Item对象
     * @throws Exception
     */
    public List<Item> getQueryItem() throws Exception {

        List<Item> queryitemList = new ArrayList<>();
        boolean first = true;

        if (first) {
            queryitemList = init();
            first = false;

        } else if (this.categoryid.equals("") && this.code.equals("") && this.status.equals("") && this.dateBought.equals("")) {
            queryitemList = init();
        } else if (this.categoryid.equals("") | this.code.equals("") | this.status.equals("") | this.dateBought.equals("")) {
            queryitemList = queryService.queryByPropertys(this.pushQueryItemParameters());
        }
        return queryitemList;
    }

    public void getValidateParameters(ComponentSystemEvent event) throws ParseException {
        UIComponent source = event.getComponent();
        UIInput categoryidComponent = (UIInput) source.findComponent("categoryid");
        UIInput statusComponent = (UIInput) source.findComponent("status");
        UIInput codeComponent = (UIInput) source.findComponent("code");
        UIInput dateboguhtComponent = (UIInput) source.findComponent("datebought");

        Integer categoryid_input = ((Number) categoryidComponent.getLocalValue()).intValue();
        String status_input = ((String) statusComponent.getLocalValue());
        String code_input = ((String) codeComponent.getLocalValue());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dateboght_input = sdf.parse(((String) dateboguhtComponent.getLocalValue()));

        List<QueryParameter> parameters = new ArrayList<>();
        if (!categoryid_input.equals("")) {
            QueryParameter qp_category = new QueryParameter("category", categoryService.
                    findCategoryById(categoryid_input), Equal);
            parameters.add(qp_category);
        }
        if (!code_input.equals("")) {
            QueryParameter qp_code = new QueryParameter("code", code_input, Equal);
            parameters.add(qp_code);
        }
        if (!dateboght_input.equals("")) {
            QueryParameter qp_datebought = new QueryParameter("dateBought", dateboght_input, Equal);
            parameters.add(qp_datebought);
        }
        if (!status_input.equals("")) {
            QueryParameter qp4_status = new QueryParameter("status", status_input, Equal);
            parameters.add(qp4_status);
        }

        for (QueryParameter q : parameters) {
            facesContext.addMessage(null, new FacesMessage("所选中的属性参数列表" + q.ParameterName + "," + q.ParameterType + "," + q.ParameterValue));
            //System.out.println();
        }

        //生成查询对象实体
        QueryCondition qcd = new QueryCondition("Item", parameters);

        if (this.categoryid.equals("") && this.code.equals("") && this.status.equals("") && this.dateBought.equals("")) {
            init();
        }
        List<Item> queryByPropertys = queryService.queryByPropertys(qcd);

        for (Item i : queryByPropertys) {
            facesContext.addMessage(null, new FacesMessage("返回的item对象:" + i.getName()));
        }

    }
}
