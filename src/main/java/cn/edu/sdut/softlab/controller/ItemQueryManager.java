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

//    public QueryCondition pushQueryItemParameters() throws Exception {
//        //形成参数列表
//        List<QueryParameter> parameters = new ArrayList<>();
//        if (!this.getCategoryid().equals("")) {
//            QueryParameter qp_category = new QueryParameter("category", categoryService.
//                    findCategoryById(this.getCategoryid()), Equal);
//            parameters.add(qp_category);
//        }
//        if (!this.getCode().equals("")) {
//            QueryParameter qp_code = new QueryParameter("code", this.getCode(), Equal);
//            parameters.add(qp_code);
//        }
//        if (!this.getDateBought().equals("")) {
//            QueryParameter qp_datebought = new QueryParameter("dateBought", this.getDateBought(), Equal);
//            parameters.add(qp_datebought);
//        }
//        if (!this.getStatus().equals("")) {
//            QueryParameter qp4_status = new QueryParameter("status", this.getStatus(), Equal);
//            parameters.add(qp4_status);
//        }
//
//        for (QueryParameter q : parameters) {
//            System.out.println("所选中的属性参数列表" + q.ParameterName + "," + q.ParameterType + "," + q.ParameterValue);
//        }
//
//        //生成查询对象实体
//        QueryCondition qcd = new QueryCondition("Item", parameters);
//        return qcd;
//    }
    public List<Item> init() {
        return itemService.findAll(Item.class);
    }

    @Named
    @RequestScoped
    public List<Item> getQueryItem() throws Exception {

        // CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaQuery cq = cb.createQuery();
        // cq.select(cq.from(Item.class));
        //Predicate pre = cb.greaterThanOrEqualTo(cq.from(Item.class).get(), cq);
        //return em.createQuery(cq).getResultList();
//        if (this.categoryid.equals("") && this.code.equals("") && this.status.equals("") && this.dateBought.equals("")) {
//            init();
//        }
//        List<Item> queryByPropertys = queryService.queryByPropertys(this.validateParameters(event));
//
//        return queryByPropertys;
        return init();
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
            facesContext.addMessage(null, new FacesMessage("返回的item对象:" +i.getName()));
        }

    }
}
