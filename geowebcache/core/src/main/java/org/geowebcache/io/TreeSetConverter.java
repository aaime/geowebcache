/*
 * Copyright (C) 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2010, 2011, 2013, 2014, 2016 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 * Created on 08. May 2004 by Joe Walnes
 */
package org.geowebcache.io;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.core.util.PresortedSet;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Converts a java.util.TreeSet to XML, and serializes the associated java.util.Comparator. The
 * converter assumes that the elements in the XML are already sorted according the comparator.
 *
 * <p>Cloned from XStream in order to avoid illegal reflective lookup warnings
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class TreeSetConverter extends CollectionConverter {
    public TreeSetConverter(Mapper mapper) {
        super(mapper, TreeSet.class);
    }

    public void marshal(
            Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        SortedSet sortedSet = (SortedSet) source;
        TreeMapConverter.marshalComparator(mapper(), sortedSet.comparator(), writer, context);
        super.marshal(source, writer, context);
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        TreeSet result = null;
        Comparator unmarshalledComparator =
                TreeMapConverter.unmarshalComparator(mapper(), reader, context, null);
        boolean inFirstElement = unmarshalledComparator instanceof Mapper.Null;
        Comparator comparator = inFirstElement ? null : unmarshalledComparator;
        final PresortedSet set = new PresortedSet(comparator);
        result = comparator == null ? new TreeSet() : new TreeSet(comparator);
        if (inFirstElement) {
            // we are already within the first element
            addCurrentElementToCollection(reader, context, result, set);
            reader.moveUp();
        }
        populateCollection(reader, context, result, set);
        if (set.size() > 0) {
            result.addAll(set); // comparator will not be called if internally optimized
        }
        return result;
    }
}
