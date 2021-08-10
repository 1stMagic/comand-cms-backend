package com.biock.cms.component.simple;

import com.biock.cms.i18n.Translation;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SimpleComponentProperty implements ValueObject<SimpleComponentProperty> {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleComponentProperty.class);

    private final String name;
    private String stringValue;
    private long longValue;
    private double doubleValue;
    private boolean booleanValue;
    private List<String> stringValues;
    private List<Long> longValues;
    private List<Double> doubleValues;
    private List<Boolean> booleanValues;
    private Translation translationValue;
    private final SimpleComponentPropertyType type;
    private boolean multiple;
    private boolean translation;

    public SimpleComponentProperty(final String name, final String value) {

        this.name = name;
        this.stringValue = value;
        this.type = SimpleComponentPropertyType.STRING;
    }

    public SimpleComponentProperty(final String name, final long value) {

        this.name = name;
        this.longValue = value;
        this.type = SimpleComponentPropertyType.LONG;
    }

    public SimpleComponentProperty(final String name, final double value) {

        this.name = name;
        this.doubleValue = value;
        this.type = SimpleComponentPropertyType.DOUBLE;
    }

    public SimpleComponentProperty(final String name, final boolean value) {

        this.name = name;
        this.booleanValue = value;
        this.type = SimpleComponentPropertyType.BOOLEAN;
    }

    public SimpleComponentProperty(final String name, final String[] values) {

        this.name = name;
        this.stringValues = List.of(values);
        this.type = SimpleComponentPropertyType.STRING;
        this.multiple = true;
    }

    public SimpleComponentProperty(final String name, final Long[] values) {

        this.name = name;
        this.longValues = List.of(values);
        this.type = SimpleComponentPropertyType.STRING;
        this.multiple = true;
    }

    public SimpleComponentProperty(final String name, final Double[] values) {

        this.name = name;
        this.doubleValues = List.of(values);
        this.type = SimpleComponentPropertyType.STRING;
        this.multiple = true;
    }

    public SimpleComponentProperty(final String name, final Boolean[] values) {

        this.name = name;
        this.booleanValues = List.of(values);
        this.type = SimpleComponentPropertyType.STRING;
        this.multiple = true;
    }

    public SimpleComponentProperty(final String name, final Translation value) {

        this.name = name;
        this.translationValue = value;
        this.type = SimpleComponentPropertyType.STRING;
        this.translation = true;
    }

    public static SimpleComponentProperty of(final Property property) {

        try {
            switch (property.getType()) {
                case PropertyType.STRING:
                    if (property.isMultiple()) {
                        return new SimpleComponentProperty(property.getName(), stringValues(property.getValues()));
                    }
                    return new SimpleComponentProperty(property.getName(), property.getString());
                case PropertyType.LONG:
                    if (property.isMultiple()) {
                        return new SimpleComponentProperty(property.getName(), longValues(property.getValues()));
                    }
                    return new SimpleComponentProperty(property.getName(), property.getLong());
                case PropertyType.DOUBLE:
                    if (property.isMultiple()) {
                        return new SimpleComponentProperty(property.getName(), doubleValues(property.getValues()));
                    }
                    return new SimpleComponentProperty(property.getName(), property.getDouble());
                case PropertyType.DECIMAL:
                    if (property.isMultiple()) {
                        return new SimpleComponentProperty(property.getName(), doubleValues(property.getValues()));
                    }
                    return new SimpleComponentProperty(property.getName(), property.getDecimal().doubleValue());
                case PropertyType.BOOLEAN:
                    if (property.isMultiple()) {
                        return new SimpleComponentProperty(property.getName(), booleanValues(property.getValues()));
                    }
                    return new SimpleComponentProperty(property.getName(), property.getBoolean());
                default:
                    LOG.error("Unsupported property type {} for property '{}'", property.getType(), property.getName());
                    return null;
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public String getName() {

        return this.name;
    }

    public String getStringValue() {

        return this.stringValue;
    }

    public long getLongValue() {

        return this.longValue;
    }

    public double getDoubleValue() {

        return this.doubleValue;
    }

    public boolean getBooleanValue() {

        return this.booleanValue;
    }

    public List<String> getStringValues() {

        return this.stringValues;
    }

    public List<Long> getLongValues() {

        return this.longValues;
    }

    public List<Double> getDoubleValues() {

        return this.doubleValues;
    }

    public List<Boolean> getBooleanValues() {

        return this.booleanValues;
    }

    public Translation getTranslationValue() {

        return this.translationValue;
    }

    public SimpleComponentPropertyType getType() {

        return this.type;
    }

    public boolean isMultiple() {

        return this.multiple;
    }

    public boolean isTranslation() {

        return this.translation;
    }

    public Object getValue(final String language, final String fallbackLanguage) {

        switch (this.type) {
            case STRING:
                if (this.translation) {
                    return this.translationValue.getTranslation(language, fallbackLanguage);
                }
                return this.multiple ? this.stringValues : this.stringValue;
            case LONG:
                return this.multiple ? this.longValues : this.longValue;
            case DOUBLE:
                return this.multiple ? this.doubleValues : this.doubleValue;
            case BOOLEAN:
                return this.multiple ? this.booleanValues : this.booleanValue;
        }
        return null;
    }

    @Override
    public int compareTo(final SimpleComponentProperty other) {

        return this.name.compareTo(other.name);
    }

    private static String[] stringValues(final Value[] values) {

        final Function<Value, String> toString = value -> {
            try {
                return value.getString();
            } catch (final RepositoryException e) {
                throw new RuntimeRepositoryException(e);
            }
        };
        return Stream.of(values).map(toString).toArray(String[]::new);
    }

    private static Long[] longValues(final Value[] values) {

        final Function<Value, Long> toLong = value -> {
            try {
                return value.getLong();
            } catch (final RepositoryException e) {
                throw new RuntimeRepositoryException(e);
            }
        };
        return Stream.of(values).map(toLong).toArray(Long[]::new);
    }

    private static Double[] doubleValues(final Value[] values) {

        final Function<Value, Double> toDouble = value -> {
            try {
                if (value.getType() == PropertyType.DECIMAL) {
                    return value.getDecimal().doubleValue();
                }
                return value.getDouble();
            } catch (final RepositoryException e) {
                throw new RuntimeRepositoryException(e);
            }
        };
        return Stream.of(values).map(toDouble).toArray(Double[]::new);
    }

    private static Boolean[] booleanValues(final Value[] values) {

        final Function<Value, Boolean> toBoolean = value -> {
            try {
                return value.getBoolean();
            } catch (final RepositoryException e) {
                throw new RuntimeRepositoryException(e);
            }
        };
        return Stream.of(values).map(toBoolean).toArray(Boolean[]::new);
    }
}
