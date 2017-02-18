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
package cn.edu.sdut.softlab.service;

import cn.edu.sdut.softlab.model.QueryCondition;
import java.util.List;
import javax.inject.Inject;

import javax.persistence.EntityManager;


/**
 *
 * @author huanlu
 */
public abstract class QueryDataAction {

    @Inject
    EntityManager em;
    
    public String errorCode;

    public QueryDataAction() {
    }

    public QueryDataAction(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 
     * @param <T>
     * @param queryCondition
     * @return 返回查询到的实体列表
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryByPropertys(QueryCondition queryCondition) {
        return null;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
