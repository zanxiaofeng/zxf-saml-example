package com.zxf.example.utils;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;

public class OpenSAMLUtil {
    public static <T> T buildSAMLObject(final Class<T> clazz) throws Exception {
        XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
        QName defaultElementName = (QName) clazz.getDeclaredField("DEFAULT_ELEMENT_NAME").get(null);
        return (T) builderFactory.getBuilder(defaultElementName).buildObject(defaultElementName);
    }
}
