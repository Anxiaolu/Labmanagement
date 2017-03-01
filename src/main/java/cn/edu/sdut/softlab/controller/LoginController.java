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
package cn.edu.sdut.softlab.controller;

import cn.edu.sdut.softlab.model.Stuff;
import cn.edu.sdut.softlab.qualifiers.LoggedIn;
import cn.edu.sdut.softlab.service.StuffFacade;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named("login")
public class LoginController implements Serializable {

    private static final long serialVersionUID = 7965455427888195913L;

    @Inject
    private Credentials credentials;

    @Inject
    StuffFacade stuffService;

    @Inject
    FacesContext facesContext;

    private Stuff currentUser = null;

    /**
     * 处理登录逻辑.
     *
     * @return
     */
    public String login() throws Exception {
        Stuff stuff = stuffService.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
        Stuff stuff2 = stuffService.findByName(credentials.getUsername());
        if (stuff != null) {
            currentUser = stuff;
            facesContext.addMessage(null, new FacesMessage("Welcome, " + currentUser.getUsername()));
        }
        if (stuff2 == null) {
            facesContext.addMessage(null, new FacesMessage("Wrong Message!"));
            return null;
        }
        return "/home.xhtml?faces-redirect=true";
    }

    public void validateStuffName(FacesContext fc, UIComponent uc, Object value) {
        List<String> stuffnameList = stuffService.findAllStuffName();
        Iterator it = stuffnameList.iterator();
        while (it.hasNext()) {
            if (!it.equals(value)) {
                continue;
            }
            if (it.hasNext()) {
                throw new ValidatorException(new FacesMessage("您确定您已经注册了该用户？"));
            }
        }
    }

    /**
     * 处理退出登录逻辑.
     *
     * @return
     */
    public String logout() {
        facesContext.addMessage(null, new FacesMessage("Goodbye, " + currentUser.getUsername()));
        currentUser = null;
        return "/index.xhtml?faces-redirect=true";
    }

    public void checkLogin(ComponentSystemEvent event) {
        if (!isLoggedIn()) {
            FacesContext context = facesContext.getCurrentInstance();
            ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.
                    getApplication().getNavigationHandler();
            handler.performNavigation("index");
        }
    }

    public String getLoginUserName() {
        if (isLoggedIn()) {
            return this.getCurrentUser().getUsername();
        }
        return null;
    }

    /**
     * 判断用户是否登录.
     *
     * @return true：已经登录；false：没有登录
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    @Produces
    @LoggedIn
    public Stuff getCurrentUser() {
        return currentUser;
    }

}
