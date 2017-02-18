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
package cn.edu.sdut.softlab.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询对象实体
 * (包含查询实体名称以及查询参数对象)
 * @author huanlu
 */
public class QueryCondition {

    public String ModelName;
    public List<QueryParameter> Parameters = new ArrayList<QueryParameter>();

    public QueryCondition() {

    }

    public QueryCondition(String modelName) {
        this.ModelName = modelName;
    }

    public QueryCondition(String modelName, List<QueryParameter> parameters) {
        this.ModelName = modelName;
        this.Parameters = parameters;
    }

    public void add(QueryParameter queryParameter) {
        this.Parameters.add(queryParameter);
    }

    public String getModelName() {
        return ModelName;
    }

    public List<QueryParameter> getParameters() {
        return Parameters;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public void setParameters(List<QueryParameter> parameters) {
        Parameters = parameters;
    }
}
