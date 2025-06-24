package javatry.java.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javatry.unit.UnitTryTestCase;

import org.seasar.dbflute.util.DfTypeUtil;

/**
 * @author jflute
 */
public class DateTest extends UnitTryTestCase {

    public void test_Date_minValue() {
        // ## Arrange ##
        Date date = new Date(Long.MIN_VALUE);
        SimpleDateFormat fullFormat = new SimpleDateFormat("Gyyyy-MM-dd HH:mm:ss.SSS");

        // ## Act ##
        String actual = fullFormat.format(date);

        // ## Assert ##
        log(actual);
    }

    public void test_Date_maxValue() {
        // ## Arrange ##
        Date date = new Date(Long.MAX_VALUE);
        SimpleDateFormat fullFormat = new SimpleDateFormat("Gyyyy-MM-dd HH:mm:ss.SSS");

        // ## Act ##
        String actual = fullFormat.format(date);

        // ## Assert ##
        log(actual);
    }

    public void test_Date_BC_border() {
        // ## Arrange ##
        Date dateAD = DfTypeUtil.toDate("0001/01/01 00:00:00.000");
        Date dateBC = DfTypeUtil.toDate("-0001/12/31 23:59:59.999");

        // ## Act ##
        int eraAD = DfTypeUtil.toCalendar(dateAD).get(Calendar.ERA);
        int eraBC = DfTypeUtil.toCalendar(dateBC).get(Calendar.ERA);

        // ## Assert ##
        log("eraAD=" + eraAD);
        log("eraBC=" + eraBC);
        assertEquals(GregorianCalendar.AD, eraAD);
        assertEquals(GregorianCalendar.BC, eraBC);
        log("AD millis = " + dateAD.getTime());
        log("BC millis = " + dateBC.getTime());
    }

    public void test_Date_equals() {
        // ## Arrange ##
        Date date = DfTypeUtil.toDate("2008/12/12");
        Timestamp timestamp = DfTypeUtil.toTimestamp("2008/12/12 00:00:00");

        // ## Act ##
        boolean actualDateBase = date.equals(timestamp);
        boolean actualTimestampBase = timestamp.equals(date);

        // ## Assert ##
        assertTrue(actualDateBase);
        assertFalse(actualTimestampBase); // I'm shocked!
    }

    public void test_Date_toString() {
        // ## Arrange ##
        Date date = DfTypeUtil.toDate("2008/09/08");

        // ## Act ##
        String actual = date.toString();

        // ## Assert ##
        assertEquals("Mon Sep 08 00:00:00 JST 2008", actual);
    }
}
