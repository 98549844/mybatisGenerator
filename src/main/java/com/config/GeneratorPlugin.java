package com.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GeneratorPlugin extends PluginAdapter {
    private static final Logger log = LogManager.getLogger(GeneratorPlugin.class.getName());


    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * model' annotation
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        log.info("Generate MPFA model: {}", topLevelClass.getType());
        String author = properties.getProperty("author");
        String dateFormat = properties.getProperty("dateFormat");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);

        log.info("import customized class, javaDoc and annotation");


        /*
        log.info("generating EmbeddedId and getter/setter");
        Field keyField = new Field("key", new FullyQualifiedJavaType("com.models.RFncFunctionKey"));
        keyField.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(keyField);
        Method getMethod = new Method("getKey");
        Method setMethod = new Method("setKey");
        getMethod.setVisibility(JavaVisibility.PRIVATE);
        getMethod.setReturnType(new FullyQualifiedJavaType("com.models.RFncFunctionKey"));
        getMethod.addBodyLine("return key ;");

        setMethod.setVisibility(JavaVisibility.PRIVATE);
        setMethod.setReturnType(new FullyQualifiedJavaType("void"));
        setMethod.addBodyLine("this.key = key;");

        Parameter p = new Parameter(new FullyQualifiedJavaType("com.models.RFncFunctionKey"), "key", false);
        setMethod.addParameter(p);

        topLevelClass.addMethod(getMethod);
        topLevelClass.addMethod(setMethod);
        log.info("Complete EmbeddedId and getter/setter generation");
        */


        topLevelClass.addImportedType("javax.persistence.*");
        topLevelClass.addImportedType("org.springframework.data.jpa.domain.support.AuditingEntityListener");

        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * @author: " + author);
        topLevelClass.addJavaDocLine(" * @time: " + dateFormatter.format(new Date()));
        topLevelClass.addJavaDocLine(" * @description: ");
        topLevelClass.addJavaDocLine(" */");

        topLevelClass.addAnnotation("@EntityListeners(AuditingEntityListener.class)");

        return true;

    }


    /**
     * customized PrimaryKeyClass
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("javax.persistence.*");
        return true;
    }

    /**
     * customized class's field
     *
     * @param field
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //   log.info("modelClassType: {}", modelClassType.name());

        field.addJavaDocLine("@Column");
        return true;
    }


    /**
     * customized mapper interface
     *
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        interfaze.addAnnotation("@Repository");
        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // generate getter
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // generate setter
        return true;
    }

}