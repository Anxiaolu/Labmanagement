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

import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.model.QueryCondition;
import cn.edu.sdut.softlab.model.QueryParameter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author huanlu
 */
public class ItemQueryFacade extends QueryDataAction {

    /**
     * 根据传入的查询条件对象用拼接sql的方式返回查询道德对象集合
     * @param queryCondition
     * @return 符合条件的list集合 
     */
    public List<Item> queryByPropertys(QueryCondition queryCondition) {
        String querySymbols = ":";
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select model \n");
        sqlBuffer.append("from " + queryCondition.ModelName + " as model \n");
        boolean first = true;
        for (int pi = 0; pi < queryCondition.Parameters.size(); pi++) {
            if (queryCondition.Parameters.get(pi).getParameterName() != null) {
                if (first) {
                    sqlBuffer.append("where ");
                    first = false;
                } else {
                    sqlBuffer.append("and ");
                }
                if (queryCondition.Parameters.get(pi).getParameterType() == QueryParameter.QueryOperateType.Equal) {
                    sqlBuffer.append("model."
                            + queryCondition.Parameters.get(pi)
                                    .getParameterName()
                            + " = "
                            + querySymbols
                            + queryCondition.Parameters.get(pi)
                                    .getParameterName() + " \n");
                } else if (queryCondition.Parameters.get(pi).getParameterType() == QueryParameter.QueryOperateType.CharIn) {
                    sqlBuffer.append("InStr(model."
                            + queryCondition.Parameters.get(pi)
                                    .getParameterName()
                            + " , "
                            + querySymbols
                            + queryCondition.Parameters.get(pi)
                                    .getParameterName() + " ) > 0 \n");
                }
            }
        }
        List<Item> list = new ArrayList<>();
        try {
            //EntityManagerHelper.log(sqlBuffer.toString(), Level.INFO, null);
            //EntityManager emEntityManager = EntityManagerHelper.getEntityManager();
            Query queryObject = em.createQuery(sqlBuffer
                    .toString());
            for (int li = 0; li < queryCondition.Parameters.size(); li++) {
                queryObject.setParameter(queryCondition.Parameters.get(li)
                        .getParameterName(), queryCondition.Parameters.get(li)
                                .getParameterValue());
            }
            list = queryObject.getResultList();
            em.close();
        }
        catch (RuntimeException re) {
            //errorCode += "CM000006";
            //EntityManagerHelper.log("queryByPropertys error", Level.SEVERE, re);
            throw re;
        }
        return list;
    }
}
