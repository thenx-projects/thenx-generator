/*
 * Copyright [2021-2021] [Thenx Projects]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thenx.config;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.thenx.config.entity.GeneratorEntity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @author wales
 * <p>
 * 加强 MyBatis的逆向功能
 */
public class GeneratorConfig implements CommentGenerator {

    private final GeneratorEntity generatorEntity = new GeneratorEntity();

    /**
     * 构造赋值
     */
    public GeneratorConfig() {
        super();
        generatorEntity.setSystemPro(System.getProperties());
        generatorEntity.setSuppressDate(false);
        generatorEntity.setSuppressAllComments(false);
        generatorEntity.setCurrentDateStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .format(Calendar.getInstance().getTime()));
    }

    /**
     * 格式化日期字符串以包含在Javadoc标记和XML注释
     *
     * @return {@link String}
     */
    protected String getDateString() {
        String result = null;
        if (!generatorEntity.isSuppressDate()) {
            result = generatorEntity.getCurrentDateStr();
        }
        return result;
    }

    /**
     * 从该配置中的任何属性添加此实例的属性CommentGenerator配置
     *
     * @param properties {@link Properties}
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        generatorEntity.setPro(properties);
        generatorEntity.setSuppressDate(isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE)));
        generatorEntity.setSuppressAllComments(isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS)));
    }

    /**
     * 为字段添加注释
     *
     * @param field              {@link Field}
     * @param introspectedTable  {@link IntrospectedTable}
     * @param introspectedColumn {@link IntrospectedColumn}
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (generatorEntity.isSuppressAllComments()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        if (introspectedColumn.getRemarks() == null || introspectedColumn.getRemarks().isEmpty()) {
            try {
                sb.append(" 作者: ").append(InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else {
            sb.append(introspectedColumn.getRemarks());
        }
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    /**
     * Java属性注释
     *
     * @param field             {@link Field}
     * @param introspectedTable {@link IntrospectedTable}
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (generatorEntity.isSuppressAllComments()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    /**
     * entity 类上添加注释
     * 这里是同时包含了父子类的实体，但是这里需要单独处理父类的注解
     *
     * @param topLevelClass     {@link TopLevelClass}
     * @param introspectedTable {@link IntrospectedTable}
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (generatorEntity.isSuppressAllComments()) {
            return;
        }
        // 父类必要的包
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        topLevelClass.addImportedType("lombok.AllArgsConstructor");

        // 父类实体需要的注解
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@AllArgsConstructor");

        // 序列化接口
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));

        topLevelClass.addJavaDocLine("/**");
        try {
            topLevelClass.addJavaDocLine("* @author " + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        topLevelClass.addJavaDocLine("* <p>");
        topLevelClass.addJavaDocLine("* " + introspectedTable.getRemarks() + "实体");
        topLevelClass.addJavaDocLine("* @date " + Calendar.getInstance().getTime());
        topLevelClass.addJavaDocLine("*/");
    }

    /**
     * Java类的类注释
     *
     * @param innerClass        {@link InnerClass}
     * @param introspectedTable {@link IntrospectedTable}
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (generatorEntity.isSuppressAllComments()) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine("* ");
        try {
            innerClass.addJavaDocLine("* @author " + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        innerClass.addJavaDocLine("* @date " + getDateString());
        innerClass.addJavaDocLine("*/");
    }

    /**
     * 类级别注释
     *
     * @param innerClass        {@link InnerClass}
     * @param introspectedTable {@link IntrospectedTable}
     * @param b                 flag
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        if (generatorEntity.isSuppressAllComments()) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine("*");
        try {
            innerClass.addJavaDocLine("* @author " + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        innerClass.addJavaDocLine("* @date " + getDateString());
        innerClass.addJavaDocLine("*/");
    }

    /**
     * 为枚举添加注释
     *
     * @param innerEnum         {@link InnerEnum}
     * @param introspectedTable {@link IntrospectedTable}
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        if (generatorEntity.isSuppressAllComments()) {
            return;
        }
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
        innerEnum.addJavaDocLine(" */");
    }

    /**
     * GETTER 我觉得没必要了，因为我们用了Lombok
     *
     * @param method             {@link Method}
     * @param introspectedTable  {@link IntrospectedTable}
     * @param introspectedColumn {@link IntrospectedColumn}
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /**
     * 同样的， SETTER同样的没什么用了
     *
     * @param method             {@link Method}
     * @param introspectedTable  {@link IntrospectedTable}
     * @param introspectedColumn {@link IntrospectedColumn}
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /**
     * 普通方法的注释，这里主要是 DAO 里面的接口方法的注释
     *
     * @param method            {@link Method}
     * @param introspectedTable {@link IntrospectedTable}
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (generatorEntity.isSuppressAllComments()) {
            return;
        }
        method.addJavaDocLine("/**");

        // 这里和阿里巴巴的Java规范插件有点冲突，所以就去掉 @Description描述，否则会有警告
        method.addJavaDocLine(" * " + introspectedTable.getRemarks() + "  " + method.getName() + " 实现");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param " + method.getParameters().get(0).getName());
        method.addJavaDocLine(" * @return null " + method.getReturnType());
        try {
            method.addJavaDocLine(" * @author " + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        method.addJavaDocLine(" * @date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance().getTime()));
        method.addJavaDocLine(" */");
    }

    /**
     * 给Java文件加注释，这个注释是在文件的顶部，也就是package上面
     *
     * @param compilationUnit {@link CompilationUnit}
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    /**
     * Mybatis的Mapper.xml文件里面的注释
     *
     * @param xmlElement {@link XmlElement}
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        if (!generatorEntity.isSuppressAllComments()) {
            xmlElement.addElement(new TextElement("<!--"));
            StringBuilder sb = new StringBuilder();
            sb.append(xmlElement.getName()).append("语句操作").append("\n");
            try {
                sb.append(" 作者： ").append(InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String s = String.valueOf(Calendar.getInstance().getTime());
            if (s != null) {
                sb.append(s);
                xmlElement.addElement(new TextElement(sb.toString()));
            }
            xmlElement.addElement(new TextElement("-->"));
        }
    }

    /**
     * 为调用此方法作为根元素的第一个子节点添加注释
     *
     * @param xmlElement {@link XmlElement}
     */
    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }
}
