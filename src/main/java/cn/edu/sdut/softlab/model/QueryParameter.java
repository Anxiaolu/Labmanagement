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

/**
 * 作为数据查询的条件对象
 * （包含三个参数，分别为参数名、参数值、查询条件表达式）
 * @author huanlu
 */
public class QueryParameter {
    public enum QueryOperateType {
        Equal, CharIn
    }

    public String ParameterName;
    public Object ParameterValue;
    public QueryOperateType ParameterType;

    public QueryParameter() {

    }

    public QueryParameter(String parameterName, Object parameterValue,
            QueryOperateType parameterType) {
        this.ParameterName = parameterName;
        this.ParameterValue = parameterValue;
        this.ParameterType = parameterType;
    }

    public String getParameterName() {
        return ParameterName;
    }

    public QueryOperateType getParameterType() {
        return ParameterType;
    }

    public Object getParameterValue() {
        return ParameterValue;
    }

    public void setParameterName(String parameterName) {
        ParameterName = parameterName;
    }

    public void setParameterType(QueryOperateType parameterType) {
        this.ParameterType = parameterType;
    }

    public void setParameterValue(Object parameterValue) {
        ParameterValue = parameterValue;
    }
}
