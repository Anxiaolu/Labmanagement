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

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author huanlu
 */
public class IllegalValidator {
    public void AddValidator(Object value){
        if (((String) value).contains(":") | ((String) value).contains("~")
                | ((String) value).contains("！") | ((String) value).contains("@")
                | ((String) value).contains("#") | ((String) value).contains("$")
                | ((String) value).contains("%") | ((String) value).contains("^")
                | ((String) value).contains("&") | ((String) value).contains("*")
                | ((String) value).contains("(") | ((String) value).contains(")")
                | ((String) value).contains("-") | ((String) value).contains("{")
                | ((String) value).contains("}") | ((String) value).contains("【")
                | ((String) value).contains("]") ) {
            throw new ValidatorException(new FacesMessage("您输入的名称含有不合法字符！"));
        }
    }
}
