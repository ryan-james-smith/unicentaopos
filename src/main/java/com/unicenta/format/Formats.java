//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2018 uniCenta & previous Openbravo POS works
//    https://unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.unicenta.format;

import java.nio.charset.StandardCharsets;
import java.text.*;
import com.unicenta.basic.BasicException;
import java.util.Date;

public abstract class Formats {

    public final static Formats NULL = new FormatsNULL();
    public final static Formats INT = new FormatsINT();
    public final static Formats STRING = new FormatsSTRING();
    public final static Formats DOUBLE = new FormatsDOUBLE();
    public final static Formats CURRENCY = new FormatsCURRENCY();
    public final static Formats PERCENT = new FormatsPERCENT();
    public final static Formats BOOLEAN = new FormatsBOOLEAN();
    public final static Formats TIMESTAMP = new FormatsTIMESTAMP();
    public final static Formats DATE = new FormatsDATE();
    public final static Formats TIME = new FormatsTIME();
    public final static Formats BYTEA = new FormatsBYTEA();
    public final static Formats HOURMIN = new FormatsHOURMIN();
    public final static Formats SIMPLEDATE = new FormatsSIMPLEDATE();

    private static NumberFormat m_integerformat = NumberFormat.getIntegerInstance();
    private static NumberFormat m_doubleformat = NumberFormat.getNumberInstance();
    private static NumberFormat m_currencyformat = NumberFormat.getCurrencyInstance();
    private static NumberFormat m_percentformat = new DecimalFormat("#,##0.##%");
    private static DateFormat m_dateformat = DateFormat.getDateInstance();
    private static DateFormat m_timeformat = DateFormat.getTimeInstance();
    private static DateFormat m_datetimeformat = DateFormat.getDateTimeInstance();

    private static final DateFormat m_hourminformat = new SimpleDateFormat("H:mm:ss");
    private static final DateFormat m_simpledate = new SimpleDateFormat("dd-MM-yyyy");

    protected Formats() {
    }

    public static int getCurrencyDecimals() {
        return m_currencyformat.getMaximumFractionDigits();
    }

    public String formatValue(Object value) {
        return value == null ? "" : formatValueInt(value);
    }

    public Object parseValue(String value, Object defvalue) throws BasicException {
        if (value == null || value.isEmpty()) {
            return defvalue;
        } else {
            try {
                return parseValueInt(value);
            } catch (ParseException e) {
                throw new BasicException(e.getMessage(), e);
            }
        }
    }

    public Object parseValue(String value) throws BasicException {
        return parseValue(value, null);
    }

    public static void setIntegerPattern(String pattern) {
        m_integerformat = pattern == null || pattern.isEmpty() ? NumberFormat.getIntegerInstance() : new DecimalFormat(pattern);
    }

    public static void setDoublePattern(String pattern) {
        m_doubleformat = pattern == null || pattern.isEmpty() ? NumberFormat.getNumberInstance() : new DecimalFormat(pattern);
    }

    public static void setCurrencyPattern(String pattern) {
        m_currencyformat = pattern == null || pattern.isEmpty() ? NumberFormat.getCurrencyInstance() : new DecimalFormat(pattern);
    }

    public static void setPercentPattern(String pattern) {
        m_percentformat = pattern == null || pattern.isEmpty() ? new DecimalFormat("#,##0.##%") : new DecimalFormat(pattern);
    }

    public static void setDatePattern(String pattern) {
        m_dateformat = pattern == null || pattern.isEmpty() ? DateFormat.getDateInstance() : new SimpleDateFormat(pattern);
    }

    public static void setTimePattern(String pattern) {
        m_timeformat = pattern == null || pattern.isEmpty() ? DateFormat.getTimeInstance() : new SimpleDateFormat(pattern);
    }

    public static void setDateTimePattern(String pattern) {
        m_datetimeformat = pattern == null || pattern.isEmpty() ? DateFormat.getDateTimeInstance() : new SimpleDateFormat(pattern);
    }

    protected abstract String formatValueInt(Object value);

    protected abstract Object parseValueInt(String value) throws ParseException;

    public abstract int getAlignment();

    private static final class FormatsNULL extends Formats {
        @Override
        protected String formatValueInt(Object value) {
            return null;
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return null;
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.LEFT;
        }
    }

    private static final class FormatsINT extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof Number) {
            return m_integerformat.format(((Number) value).longValue());
        } else {
            throw new IllegalArgumentException("Expected a Number object");
        }
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        if (value != null) {
            return m_integerformat.parse(value).intValue();
        } else {
            throw new IllegalArgumentException("Value must not be null");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.RIGHT;
    }
}

    private static final class FormatsSTRING extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else {
            throw new IllegalArgumentException("Expected a String object");
        }
    }

    @Override
    protected Object parseValueInt(String value) {
        if (value != null) {
            return value;
        } else {
            throw new IllegalArgumentException("Value must not be null");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.LEFT;
    }
}

    private static final class FormatsDOUBLE extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof Number) {
            return m_doubleformat.format(DoubleUtils.fixDecimals((Number) value)); // quickfix for 3838
        } else {
            throw new IllegalArgumentException("Expected a Number object");
        }
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        if (value != null) {
            return m_doubleformat.parse(value).doubleValue();
        } else {
            throw new IllegalArgumentException("Value must not be null");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.RIGHT;
    }
}

    private static final class FormatsPERCENT extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof Number) {
            return m_percentformat.format(DoubleUtils.fixDecimals((Number) value)); // quickfix for 3838
        } else {
            throw new IllegalArgumentException("Expected a Number object");
        }
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        if (value != null) {
            try {
                return m_percentformat.parse(value).doubleValue();
            } catch (ParseException e) {
                // Second chance as a regular number
                return m_doubleformat.parse(value).doubleValue() / 100;
            }
        } else {
            throw new IllegalArgumentException("Value must not be null");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.RIGHT;
    }
}

    private static final class FormatsCURRENCY extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof Number) {
            return m_currencyformat.format(DoubleUtils.fixDecimals((Number) value)); // quickfix for 3838
        } else {
            throw new IllegalArgumentException("Expected a Number object");
        }
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        if (value != null) {
            try {
                return m_currencyformat.parse(value).doubleValue();
            } catch (ParseException e) {
                // Second chance as a regular number
                return m_doubleformat.parse(value).doubleValue();
            }
        } else {
            throw new IllegalArgumentException("Value must not be null");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.RIGHT;
    }
}

    private static final class FormatsBOOLEAN extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            if (value instanceof Boolean) {
                return value.toString();
            } else {
                throw new IllegalArgumentException("Expected a Boolean object");
            }
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            if (value != null) {
                return Boolean.valueOf(value);
            } else {
                throw new IllegalArgumentException("Value must not be null");
            }
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }

    private static final class FormatsTIMESTAMP extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof Date) {
            return m_datetimeformat.format((Date) value);
        } else {
            throw new IllegalArgumentException("Expected a Date object");
        }
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        if (value != null) {
            try {
                return m_datetimeformat.parse(value);
            } catch (ParseException e) {
                // Second chance as a normal date
                return m_dateformat.parse(value);
            }
        } else {
            throw new IllegalArgumentException("Value must not be null");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.CENTER;
    }
}

    private static final class FormatsDATE extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            if (value instanceof Date) {
                return m_dateformat.format((Date) value);
            } else {
                throw new IllegalArgumentException("Expected a Date object");
            }
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            if (value != null) {
                return m_dateformat.parse(value);
            } else {
                throw new IllegalArgumentException("Value must not be null");
            }
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }

    private static final class FormatsTIME extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof Date) {
            return m_timeformat.format((Date) value);
        } else {
            throw new IllegalArgumentException("Expected a Date object");
        }
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        if (value != null) {
            return m_timeformat.parse(value);
        } else {
            throw new IllegalArgumentException("Value must not be null");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.CENTER;
    }
}

    private static final class FormatsBYTEA extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof byte[]) {
            return new String((byte[]) value, StandardCharsets.UTF_8);
        } else {
            throw new IllegalArgumentException("Expected a byte array");
        }
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        if (value != null) {
            return value.getBytes(StandardCharsets.UTF_8);
        } else {
            throw new IllegalArgumentException("Value must not be null");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.LEADING;
    }
}

private static final class FormatsHOURMIN extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof Date) {
            return m_hourminformat.format((Date) value);
        } else {
            throw new IllegalArgumentException("Expected a Date object");
        }
    }

    @Override
    protected Date parseValueInt(String value) throws ParseException {
        if (value != null && !value.isEmpty()) {
            return m_hourminformat.parse(value);
        } else {
            throw new IllegalArgumentException("Value must not be null or empty");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.CENTER;
    }
}

private static final class FormatsSIMPLEDATE extends Formats {

    @Override
    protected String formatValueInt(Object value) {
        if (value instanceof Date) {
            return m_simpledate.format((Date) value);
        } else {
            throw new IllegalArgumentException("Expected a Date object");
        }
    }

    @Override
    protected Date parseValueInt(String value) throws ParseException {
        if (value != null && !value.isEmpty()) {
            return m_simpledate.parse(value);
        } else {
            throw new IllegalArgumentException("Value must not be null or empty");
        }
    }

    @Override
    public int getAlignment() {
        return javax.swing.SwingConstants.CENTER;
    }
}
}
