/*
 * Copyright 2017 huanlu and individual contributors by the 
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

import cn.edu.sdut.softlab.converterandvalidator.IllegalValidator;
import cn.edu.sdut.softlab.model.Stuff;
import cn.edu.sdut.softlab.service.StuffFacade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.NotFoundException;

@Named("userManager")
@RequestScoped
public class StuffManagerImpl extends IllegalValidator implements StuffManager {

    @Inject
    private transient Logger logger;

    @Inject
    StuffFacade userService;

    @Inject
    Credentials credentials;

    @Inject
    private UserTransaction utx;

    @Inject
    EntityManager em;

    private Stuff currenstuff = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //作为前台修改信息的暂存变量
    private String currentstuffname;
    private String email;
    private String password;

    public String getCurrentstuffname() {
        return currentstuffname;
    }

    public void setCurrentstuffname(String currentstuffname) {
        this.currentstuffname = currentstuffname;
    }

    private Stuff newStuff = new Stuff();

    public Stuff getNewStuff() {
        return newStuff;
    }

    public void setNewStuff(Stuff newStuff) {
        this.newStuff = newStuff;
    }

    public List<Stuff> getStuffs() throws Exception {
        try {
            utx.begin();
            return userService.findAll(Stuff.class);
        }
        finally {
            utx.commit();
        }
    }

    public String addStuff() throws Exception {
        try {
            utx.begin();
            userService.create(newStuff);
            logger.log(Level.INFO, "Added {0}", newStuff);
            return "/Index.xhtml?faces-redirect=true";
        }
        finally {
            utx.commit();
        }
    }
    
    public List<String> getAllStuffName() throws Exception {
        List<String> stuffnamelist = new ArrayList<>();
        List<Stuff> stufflist = getStuffs();
        for (Stuff s : stufflist) {
            stuffnamelist.add(s.getUsername());
        }
        return stuffnamelist;
    }

    public String removeStuff() throws Exception {
        Stuff temporstuff = userService.findByName(credentials.getName());
        if (temporstuff != null) {
            currenstuff = temporstuff;
        }
        utx.begin();
        userService.remove(currenstuff);
        utx.commit();
        logger.log(Level.INFO, "Added {0}", newStuff);
        return "/userdelect.xhtml?faces-redirect=true";
    }
    
    public void validatorRemoveName(FacesContext fc,UIComponent uc, Object value){
        List<String> stuffs = userService.findAllStuffName();
        Iterator it = stuffs.iterator();
        while(it.hasNext()){
            if (!it.equals(value)) {
                continue;
            }
            if (!it.hasNext()) {
                throw new ValidatorException(new FacesMessage("没有该用户！"));
            }
        }
    }

    public String editStuff() throws Exception {
        utx.begin();
        Stuff updatestuff = userService.findByName(currentstuffname);
        if (updatestuff == null) {
            throw new NotFoundException();
        }
        if (updatestuff.getUsername() != null) {
            updatestuff.setUsername(currentstuffname);
        }
        if (updatestuff.getPassword() != null) {
            updatestuff.setPassword(password);
        }
        if (updatestuff.getEmail() != null) {
            updatestuff.setEmail(email);
        }
        em.merge(updatestuff);
        utx.commit();
        return "/Stuff_query.xhtml?faces-redirect=true";
    }

    public void StuffAddValidator(FacesContext fc, UIComponent component, Object value) throws Exception {
        AddValidator(value);
        List<String> stuffNamelist = getAllStuffName();
        for (String s : stuffNamelist) {
            if (((String) value).equals(s)) {
                throw new ValidatorException(new FacesMessage("您注册的昵称已有，请另选择其他昵称！"));
            }
        }
    }
}
