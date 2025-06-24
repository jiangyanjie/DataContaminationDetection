package tools.timezone;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class DayLight {

  private TimeZone timeZone;
  private Date startDay = new Date();
  private Date endDay;

  public static DayLight create() {
    return create(TimeZone.getDefault());
  }

  public static DayLight create(TimeZone timeZone) {
    DayLight dayLight = new DayLight();
    dayLight.setTimeZone(timeZone);
    return dayLight;
  }

  public void process(String dayStr) {
    process(dayStr, dayStr);
  }

  public void process(String startDayStr, String endDayStr) {
    parseDateInterval(startDayStr, endDayStr);
  }

  private void parseDateInterval(String startDayStr, String endDayStr) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setTimeZone(timeZone);

    try {
      this.startDay = dateFormat.parse(startDayStr);
      this.endDay = dateFormat.parse(endDayStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    if (this.startDay.equals(this.endDay)) {
      Calendar cal = Calendar.getInstance(timeZone);
      cal.setTime(this.startDay);
      cal.add(Calendar.DATE, 1);
      this.endDay = cal.getTime();
    }
  }

  public List<Date> getHours() {
    List<Date> result = new ArrayList<Date>();
    final Calendar cal = Calendar.getInstance(timeZone);
    cal.setTime(startDay);
    do {
      result.add(cal.getTime());
      cal.add(Calendar.HOUR, 1);
    } while (cal.getTime().getTime() < endDay.getTime());
    return result;
  }

  public List<Date> formatHoursTo24() {
    final int maxTimesSize = 25 - 1;
    final Calendar cal = Calendar.getInstance(timeZone);
    List<Date> result = new ArrayList<Date>(getHours());

    // Remove the local duplicated hour
    if (result.size() > maxTimesSize) {
      int preHourIntVal = -1;
      for (Iterator<Date> i = result.iterator(); i.hasNext(); ) {
        cal.setTime(i.next());
        int hourIntValInLocal = cal.get(Calendar.HOUR_OF_DAY);
        if (preHourIntVal == hourIntValInLocal) {
          i.remove();
        }
        preHourIntVal = hourIntValInLocal;
      }
    }

    // Complete the missed local hour
    if (result.size() < maxTimesSize) {
      int preHourIntVal = -1;
      for (int i = 0; i < result.size(); i++) {
        cal.setTime(result.get(i));
        int hourIntValInLocal = cal.get(Calendar.HOUR_OF_DAY);
        if (hourIntValInLocal - preHourIntVal > 1) {
          result.add(i, new Date(cal.getTimeInMillis()));
          break;
        }
        preHourIntVal = hourIntValInLocal;
      }
    }

    return result;
  }

  public TimeZone getTimeZone() {
    return this.timeZone;
  }

  private void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }

  private DayLight() {
  }
}
