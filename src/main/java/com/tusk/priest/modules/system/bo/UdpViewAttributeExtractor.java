package com.tusk.priest.modules.system.bo;

import net.sf.ehcache.Element;
import net.sf.ehcache.search.attribute.AttributeExtractor;
import net.sf.ehcache.search.attribute.AttributeExtractorException;

public class UdpViewAttributeExtractor implements AttributeExtractor {
    @Override
    public Object attributeFor(Element element, String s) throws AttributeExtractorException {
        UdpView udpView = (UdpView) element.getObjectValue();
        return udpView.getIp();
    }
}
