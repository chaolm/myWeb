package com.dripop.core.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.StringUtils;

public class JaxbXmlUtil {
    public static final String DEFAULT_ENCODING = "UTF-8";
    private JAXBContext jaxbContext;

    public JaxbXmlUtil(Class... types) {
        try {
            this.jaxbContext = JAXBContext.newInstance(types);
        } catch (JAXBException var3) {
            throw new RuntimeException(var3);
        }
    }

    private JaxbXmlUtil() {
    }

    public static JaxbXmlUtil getJaxbUtil(Class... types) {
        return new JaxbXmlUtil(types);
    }

    public String toXml(Object root, String encoding) {
        try {
            StringWriter e = new StringWriter();
            this.createMarshaller(encoding).marshal(root, e);
            return e.toString();
        } catch (JAXBException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> String toXml(Collection<T> root, String rootName, String encoding) {
        try {
            CollectionWrapper e = new CollectionWrapper();
            e.collection = root;
            JAXBElement wrapperElement = new JAXBElement(new QName(rootName), CollectionWrapper.class, e);
            StringWriter writer = new StringWriter();
            this.createMarshaller(encoding).marshal(wrapperElement, writer);
            return writer.toString();
        } catch (JAXBException var7) {
            throw new RuntimeException(var7);
        }
    }

    public <T> T fromXml(String xml) {
        try {
            StringReader e = new StringReader(xml);
            return (T)this.createUnmarshaller().unmarshal(e);
        } catch (JAXBException var3) {
            throw new RuntimeException(var3);
        }
    }

    public <T> T fromXml(String xml, boolean caseSensitive) {
        try {
            String e = xml;
            if(!caseSensitive) {
                e = xml.toLowerCase();
            }
            StringReader reader = new StringReader(e);
            return (T)this.createUnmarshaller().unmarshal(reader);
        } catch (JAXBException var5) {
            throw new RuntimeException(var5);
        }
    }

    public Marshaller createMarshaller(String encoding) {
        try {
            Marshaller e = this.jaxbContext.createMarshaller();
            e.setProperty("jaxb.formatted.output", Boolean.FALSE);
            if(StringUtils.isNotBlank(encoding)) {
                e.setProperty("jaxb.encoding", encoding);
            }

            return e;
        } catch (JAXBException var3) {
            throw new RuntimeException(var3);
        }
    }

    public Unmarshaller createUnmarshaller() {
        try {
            return this.jaxbContext.createUnmarshaller();
        } catch (JAXBException var2) {
            throw new RuntimeException(var2);
        }
    }

    private static String setElementsJavaType(String source, String[] elementsName, Class... clazzs) {
        String xmlStr = source;

        for(int i = 0; i < clazzs.length; ++i) {
            xmlStr = setElementJavaType(xmlStr, elementsName[i], clazzs[i]);
        }

        return xmlStr;
    }

    private static <T> String setElementJavaType(String xmlStr, String elementName, Class<T> clazz) {
        String xmlStr_ = addAttribute(xmlStr, elementName, "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        String clz = clazz.getSimpleName();
        String className = clz.substring(0, 1).toLowerCase();
        className = className + clz.substring(1);
        xmlStr_ = addAttribute(xmlStr_, elementName, "xsi:type", className);
        return xmlStr_;
    }

    private static String addAttribute(String xmlStr, String elementName, String attrName, String value) {
        return xmlStr.replaceAll("<" + elementName, "<" + elementName + " " + attrName + "=\"" + value + "\"");
    }

    /**
     * 封装Root Element 是 Collection的情况.
     */
    public static class CollectionWrapper {
        @XmlAnyElement
        protected Collection<?> collection;
    }
}
