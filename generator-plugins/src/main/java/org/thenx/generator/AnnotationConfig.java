/*-
 * <<
 * thenx-generator
 * >
 * Copyright (C) 2019 thenx
 * >
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
 * >>
 */

package org.thenx.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author thenx
 * <p>
 * 自定义一个插件，用于第三方扩展
 */
public class AnnotationConfig extends PluginAdapter {

    /**
     * 开启验证
     *
     * @param list
     * @return null
     */
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 添加 Lombok 扩展
     * 这里的扩展不包含父类实体，但是需要单独处理 Lombok带来的BUG
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return null
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        boolean present = isPresent(introspectedTable);
        String entityFullName = Underline2Camel.underline2Camel(introspectedTable.getFullyQualifiedTable().toString(), true);
        if (present) {
            boolean assignableFrom = entityFullName.getClass().isAssignableFrom((entityFullName + "Key").getClass());
            if (assignableFrom) {
                // 这个注解主要是处理 Lombok 的继承父类实体BUG
                topLevelClass.addImportedType("lombok.EqualsAndHashCode");
                topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = true)");
            }
        }
        return true;
    }

    /**
     * 判断索引字段
     *
     * @param introspectedTable
     * @return null
     */
    private static boolean isPresent(IntrospectedTable introspectedTable) {
        return introspectedTable.getPrimaryKeyColumns().size() > 1;
    }

    /**
     * 判断索引父类是否存在
     *
     * @param name
     * @return null
     */
    private static boolean isPresents(String name) {
        try {
            Thread.currentThread().getContextClassLoader().loadClass(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 给 DAO 一个明明白白的注释
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return null
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine("* 表" + introspectedTable.getFullyQualifiedTable() +
                " -> " + introspectedTable.getRemarks() + " 的基本功能实现");
        interfaze.addJavaDocLine("*");
        try {
            interfaze.addJavaDocLine("* @author " + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        interfaze.addJavaDocLine("* @date " + date2Str(Calendar.getInstance().getTime()));
        interfaze.addJavaDocLine("*/");
        return true;
    }

    /**
     * 取消 GETTER
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return null
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 取消 SETTER
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return null
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 格式化时间日期
     *
     * @param date
     * @return null
     */
    private String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

}
