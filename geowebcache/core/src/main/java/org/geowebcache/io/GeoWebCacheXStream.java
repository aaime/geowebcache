/**
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU Lesser General Public License along with this
 * program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Kevin Smith, Boundless, Copyright 2015
 */
package org.geowebcache.io;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.ConverterRegistry;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;
import com.thoughtworks.xstream.converters.basic.BigIntegerConverter;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.ByteConverter;
import com.thoughtworks.xstream.converters.basic.CharConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.ShortConverter;
import com.thoughtworks.xstream.converters.basic.StringBufferConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.basic.URIConverter;
import com.thoughtworks.xstream.converters.basic.URLConverter;
import com.thoughtworks.xstream.converters.collections.ArrayConverter;
import com.thoughtworks.xstream.converters.collections.BitSetConverter;
import com.thoughtworks.xstream.converters.collections.CharArrayConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.collections.SingletonCollectionConverter;
import com.thoughtworks.xstream.converters.collections.SingletonMapConverter;
import com.thoughtworks.xstream.converters.extended.ColorConverter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.converters.extended.FileConverter;
import com.thoughtworks.xstream.converters.extended.GregorianCalendarConverter;
import com.thoughtworks.xstream.converters.extended.JavaClassConverter;
import com.thoughtworks.xstream.converters.extended.JavaFieldConverter;
import com.thoughtworks.xstream.converters.extended.JavaMethodConverter;
import com.thoughtworks.xstream.converters.extended.LocaleConverter;
import com.thoughtworks.xstream.converters.extended.LookAndFeelConverter;
import com.thoughtworks.xstream.converters.extended.SqlDateConverter;
import com.thoughtworks.xstream.converters.extended.SqlTimeConverter;
import com.thoughtworks.xstream.converters.extended.SqlTimestampConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.SelfStreamingInstanceChecker;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import java.lang.reflect.Constructor;
import org.geowebcache.GeoWebCacheExtensions;

/**
 * XStream subclass
 *
 * @author Kevin Smith, Boundless
 */
public class GeoWebCacheXStream extends XStream {

    public GeoWebCacheXStream() {
        super();
        secure();
    }

    public GeoWebCacheXStream(HierarchicalStreamDriver hierarchicalStreamDriver) {
        super(hierarchicalStreamDriver);
        secure();
    }

    public GeoWebCacheXStream(
            ReflectionProvider reflectionProvider,
            HierarchicalStreamDriver driver,
            ClassLoaderReference classLoaderReference,
            Mapper mapper,
            ConverterLookup converterLookup,
            ConverterRegistry converterRegistry) {
        super(
                reflectionProvider,
                driver,
                classLoaderReference,
                mapper,
                converterLookup,
                converterRegistry);
        secure();
    }

    public GeoWebCacheXStream(
            ReflectionProvider reflectionProvider,
            HierarchicalStreamDriver driver,
            ClassLoaderReference classLoaderReference,
            Mapper mapper) {
        super(reflectionProvider, driver, classLoaderReference, mapper);
        secure();
    }

    public GeoWebCacheXStream(
            ReflectionProvider reflectionProvider,
            HierarchicalStreamDriver driver,
            ClassLoaderReference classLoaderReference) {
        super(reflectionProvider, driver, classLoaderReference);
        secure();
    }

    public GeoWebCacheXStream(
            ReflectionProvider reflectionProvider,
            HierarchicalStreamDriver hierarchicalStreamDriver) {
        super(reflectionProvider, hierarchicalStreamDriver);
        secure();
    }

    public GeoWebCacheXStream(ReflectionProvider reflectionProvider) {
        super(reflectionProvider);
        secure();
    }

    @Deprecated
    public GeoWebCacheXStream(
            ReflectionProvider reflectionProvider,
            HierarchicalStreamDriver driver,
            ClassLoader classLoader,
            Mapper mapper,
            ConverterLookup converterLookup,
            ConverterRegistry converterRegistry) {
        super(reflectionProvider, driver, classLoader, mapper, converterLookup, converterRegistry);
        secure();
    }

    @Deprecated
    public GeoWebCacheXStream(
            ReflectionProvider reflectionProvider,
            HierarchicalStreamDriver driver,
            ClassLoader classLoader,
            Mapper mapper) {
        super(reflectionProvider, driver, classLoader, mapper);
        secure();
    }

    @Deprecated
    public GeoWebCacheXStream(
            ReflectionProvider reflectionProvider,
            HierarchicalStreamDriver driver,
            ClassLoader classLoader) {
        super(reflectionProvider, driver, classLoader);
        secure();
    }

    @Deprecated
    public GeoWebCacheXStream(
            ReflectionProvider reflectionProvider, Mapper mapper, HierarchicalStreamDriver driver) {
        super(reflectionProvider, mapper, driver);
        secure();
    }

    /**
     * Add security permission for a type hierarchy.
     *
     * @param types the base type(s) to allow
     * @since 1.4.7
     */
    public void allowTypeHierarchies(Class<?>... types) {
        for (Class<?> type : types) {
            this.allowTypeHierarchy(type);
        }
    }

    private void secure() {
        // Require classes to be on whitelist
        addPermission(NoTypePermission.NONE);

        // Allow primitive types
        addPermission(new PrimitiveTypePermission());

        // Common non-primitives
        allowTypes(
                new Class[] {
                    java.lang.String.class,
                    java.util.Date.class,
                    java.sql.Date.class,
                    java.sql.Timestamp.class,
                    java.sql.Time.class,
                });

        // Common collections
        allowTypes(
                new Class[] {
                    java.util.TreeSet.class,
                    java.util.SortedSet.class,
                    java.util.Set.class,
                    java.util.HashSet.class,
                    java.util.List.class,
                    java.util.ArrayList.class,
                    java.util.Map.class,
                    java.util.HashMap.class,
                    java.util.concurrent.CopyOnWriteArrayList.class,
                    java.util.concurrent.ConcurrentHashMap.class,
                });

        String whitelistProp = GeoWebCacheExtensions.getProperty("GEOWEBCACHE_XSTREAM_WHITELIST");
        if (whitelistProp != null) {
            String[] wildcards = whitelistProp.split("\\s+|(\\s*;\\s*)");
            this.allowTypesByWildcard(wildcards);
        }
    }

    @Override
    protected void setupConverters() {
        Mapper mapper = getMapper();
        ReflectionProvider reflectionProvider = getReflectionProvider();
        ClassLoaderReference classLoaderReference = getClassLoaderReference();
        ConverterLookup converterLookup = getConverterLookup();

        registerConverter(new ReflectionConverter(mapper, reflectionProvider), PRIORITY_VERY_LOW);
        registerConverter(new InternalBlackList(), PRIORITY_LOW);

        registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
        registerConverter(new IntConverter(), PRIORITY_NORMAL);
        registerConverter(new FloatConverter(), PRIORITY_NORMAL);
        registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
        registerConverter(new LongConverter(), PRIORITY_NORMAL);
        registerConverter(new ShortConverter(), PRIORITY_NORMAL);
        registerConverter((Converter) new CharConverter(), PRIORITY_NORMAL);
        registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
        registerConverter(new ByteConverter(), PRIORITY_NORMAL);

        registerConverter(new StringConverter(), PRIORITY_NORMAL);
        registerConverter(new StringBufferConverter(), PRIORITY_NORMAL);
        registerConverter(new DateConverter(), PRIORITY_NORMAL);
        registerConverter(new BitSetConverter(), PRIORITY_NORMAL);
        registerConverter(new URIConverter(), PRIORITY_NORMAL);
        registerConverter(new URLConverter(), PRIORITY_NORMAL);
        registerConverter(new BigIntegerConverter(), PRIORITY_NORMAL);
        registerConverter(new BigDecimalConverter(), PRIORITY_NORMAL);

        registerConverter(new ArrayConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new CharArrayConverter(), PRIORITY_NORMAL);
        registerConverter(new CollectionConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new MapConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new TreeMapConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new TreeSetConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new SingletonCollectionConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new SingletonMapConverter(mapper), PRIORITY_NORMAL);
        registerConverter((Converter) new EncodedByteArrayConverter(), PRIORITY_NORMAL);

        registerConverter(new FileConverter(), PRIORITY_NORMAL);
        if (JVM.isSQLAvailable()) {
            registerConverter(new SqlTimestampConverter(), PRIORITY_NORMAL);
            registerConverter(new SqlTimeConverter(), PRIORITY_NORMAL);
            registerConverter(new SqlDateConverter(), PRIORITY_NORMAL);
        }
        registerConverter(new JavaClassConverter(classLoaderReference), PRIORITY_NORMAL);
        registerConverter(new JavaMethodConverter(classLoaderReference), PRIORITY_NORMAL);
        registerConverter(new JavaFieldConverter(classLoaderReference), PRIORITY_NORMAL);

        if (JVM.isAWTAvailable()) {
            registerConverter(new ColorConverter(), PRIORITY_NORMAL);
        }
        if (JVM.isSwingAvailable()) {
            registerConverter(
                    new LookAndFeelConverter(mapper, reflectionProvider), PRIORITY_NORMAL);
        }
        registerConverter(new LocaleConverter(), PRIORITY_NORMAL);
        registerConverter(new GregorianCalendarConverter(), PRIORITY_NORMAL);

        // late bound converters - allows XStream to be compiled on earlier JDKs
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.extended.SubjectConverter",
                PRIORITY_NORMAL,
                new Class[] {Mapper.class},
                new Object[] {mapper});
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.extended.ThrowableConverter",
                PRIORITY_NORMAL,
                new Class[] {ConverterLookup.class},
                new Object[] {converterLookup});
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.extended.StackTraceElementConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.extended.CurrencyConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.extended.RegexPatternConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.extended.CharsetConverter",
                PRIORITY_NORMAL,
                null,
                null);

        // late bound converters - allows XStream to be compiled on earlier JDKs
        if (JVM.loadClassForName("javax.xml.datatype.Duration") != null) {
            registerConverterDynamically(
                    "com.thoughtworks.xstream.converters.extended.DurationConverter",
                    PRIORITY_NORMAL,
                    null,
                    null);
        }
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.enums.EnumConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.basic.StringBuilderConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.basic.UUIDConverter",
                PRIORITY_NORMAL,
                null,
                null);
        if (JVM.loadClassForName("javax.activation.ActivationDataFlavor") != null) {
            registerConverterDynamically(
                    "com.thoughtworks.xstream.converters.extended.ActivationDataFlavorConverter",
                    PRIORITY_NORMAL,
                    null,
                    null);
        }
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.extended.PathConverter",
                PRIORITY_NORMAL,
                null,
                null);

        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.ChronologyConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.DurationConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.HijrahDateConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.JapaneseDateConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.JapaneseEraConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.InstantConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.LocalDateConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.LocalDateTimeConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.LocalTimeConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.MinguoDateConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.MonthDayConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.OffsetDateTimeConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.OffsetTimeConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.PeriodConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.SystemClockConverter",
                PRIORITY_NORMAL,
                new Class[] {Mapper.class},
                new Object[] {mapper});
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.ThaiBuddhistDateConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.ValueRangeConverter",
                PRIORITY_NORMAL,
                new Class[] {Mapper.class},
                new Object[] {mapper});
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.WeekFieldsConverter",
                PRIORITY_NORMAL,
                new Class[] {Mapper.class},
                new Object[] {mapper});
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.YearConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.YearMonthConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.ZonedDateTimeConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.time.ZoneIdConverter",
                PRIORITY_NORMAL,
                null,
                null);
        registerConverterDynamically(
                "com.thoughtworks.xstream.converters.reflection.LambdaConverter",
                PRIORITY_NORMAL,
                new Class[] {Mapper.class, ReflectionProvider.class, ClassLoaderReference.class},
                new Object[] {mapper, reflectionProvider, classLoaderReference});

        registerConverter(new SelfStreamingInstanceChecker(converterLookup, this), PRIORITY_NORMAL);
    }

    private void registerConverterDynamically(
            String className,
            int priority,
            Class[] constructorParamTypes,
            Object[] constructorParamValues) {
        try {
            Class type = Class.forName(className, false, getClassLoaderReference().getReference());
            Constructor constructor = type.getConstructor(constructorParamTypes);
            Object instance = constructor.newInstance(constructorParamValues);
            if (instance instanceof Converter) {
                registerConverter((Converter) instance, priority);
            } else if (instance instanceof SingleValueConverter) {
                registerConverter((SingleValueConverter) instance, priority);
            }
        } catch (Exception e) {
            throw new com.thoughtworks.xstream.InitializationException(
                    "Could not instantiate converter : " + className, e);
        } catch (LinkageError e) {
            throw new com.thoughtworks.xstream.InitializationException(
                    "Could not instantiate converter : " + className, e);
        }
    }

    private class InternalBlackList implements Converter {

        public boolean canConvert(final Class type) {
            return (type == void.class || type == Void.class)
                    || (type != null
                            && (type.getName().equals("java.beans.EventHandler")
                                    || type.getName().endsWith("$LazyIterator")
                                    || type.getName().startsWith("javax.crypto.")));
        }

        public void marshal(
                final Object source,
                final HierarchicalStreamWriter writer,
                final MarshallingContext context) {
            throw new ConversionException("Security alert. Marshalling rejected.");
        }

        public Object unmarshal(
                final HierarchicalStreamReader reader, final UnmarshallingContext context) {
            throw new ConversionException("Security alert. Unmarshalling rejected.");
        }
    }
}
