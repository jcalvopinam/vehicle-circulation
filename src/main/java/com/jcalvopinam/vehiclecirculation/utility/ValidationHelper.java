package com.jcalvopinam.vehiclecirculation.utility;

import com.jcalvopinam.vehiclecirculation.domain.Policy;
import com.jcalvopinam.vehiclecirculation.exception.DateException;
import com.jcalvopinam.vehiclecirculation.exception.PlateNumberException;
import com.jcalvopinam.vehiclecirculation.exception.TimeException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
@Slf4j
public class ValidationHelper {

    /**
     * The plateNumber must have this structure: (3 Characters)(Dash Separator)(4 Numbers).
     * Example: AAA-1234
     * Any other format is invalid
     *
     * @param plateNumber receives the plateNumber
     * @return the last plate number.
     */
    public static char getLastNumber(final String plateNumber) {
        log.info("plateNumber: {}", plateNumber);
        if (Objects.isNull(plateNumber)) {
            throw new PlateNumberException("The plate number cannot be null");
        }
        final String plateNumberFormat = "(^[A-Z]{3})([-])\\d{4}$";
        final Pattern pattern = Pattern.compile(plateNumberFormat);
        final Matcher matcher = pattern.matcher(plateNumber);

        if (matcher.find()) {
            return plateNumber.charAt(plateNumber.length() - 1);
        } else {
            throw new PlateNumberException("Invalid plate number");
        }
    }

    /**
     * The date must have this structure: uuuu/MM/dd.
     * Example: 2020/10/05
     *
     * @param date receives the date
     * @return the date in LocalDate object.
     */
    public static LocalDateTime dateTime(final String date, final String time) {
        log.info("dateTime: {} {}", date, time);
        if (Objects.isNull(date)) {
            throw new DateException("The date cannot be null");
        }
        if (Objects.isNull(time)) {
            throw new TimeException("The time cannot be null");
        }
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm")
                                                                 .withLocale(Locale.US)
                                                                 .withResolverStyle(ResolverStyle.STRICT);
            final String dateTime = String.format("%s %s", date, time);
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException dtpe) {
            throw new DateTimeParseException("Incorrect date format", dtpe.getParsedString(), dtpe.getErrorIndex());
        }
    }

    /**
     * Validates whether the day is a week day.
     *
     * @param dayOfWeek receive a day.
     * @return a boolean.
     */
    public static boolean isWeekDay(final DayOfWeek dayOfWeek) {
        return !DayOfWeek.SATURDAY.equals(dayOfWeek) && !DayOfWeek.SUNDAY.equals(dayOfWeek);
    }

    /**
     * Validates whether the vehicle is restricted to road or not depending on the time, day of week and plateNumber.
     *
     * @param vehicleDateTime receives the LocalDateTime object.
     * @param lastPlateNumber receives the last PlateNumber
     * @param policy          receives the Policy object.
     * @return a boolean.
     */
    public boolean isCirculationRestricted(final LocalDateTime vehicleDateTime, final char lastPlateNumber,
                                           final Policy policy) {
        if (isPlateRestricted(vehicleDateTime.getDayOfWeek(), lastPlateNumber)) {
            return isDateTimeRestricted(vehicleDateTime, policy);
        } else {
            return false;
        }
    }

    /**
     * Validates whether the plate is restricted depending on the dayOfWeek and the lastPlateNumber
     *
     * @param dayOfWeek       receives the DayOfWeek object.
     * @param lastPlateNumber receives the last PlateNumber
     * @return a boolean.
     */
    private static boolean isPlateRestricted(final DayOfWeek dayOfWeek, char lastPlateNumber) {
        return (DayOfWeek.MONDAY.equals(dayOfWeek) && (lastPlateNumber == '1' || lastPlateNumber == '2'))
               || (DayOfWeek.TUESDAY.equals(dayOfWeek) && (lastPlateNumber == '3' || lastPlateNumber == '4'))
               || (DayOfWeek.WEDNESDAY.equals(dayOfWeek) && (lastPlateNumber == '5' || lastPlateNumber == '6'))
               || (DayOfWeek.THURSDAY.equals(dayOfWeek) && (lastPlateNumber == '7' || lastPlateNumber == '8'))
               || (DayOfWeek.FRIDAY.equals(dayOfWeek) && (lastPlateNumber == '9' || lastPlateNumber == '0'));
    }

    /**
     * Validates if the vehicle is restricted depending on the time
     *
     * @param vehicleDateTime receives the LocalDateTime object.
     * @param policy          receives the Policy object.
     * @return a boolean.
     */
    private boolean isDateTimeRestricted(final LocalDateTime vehicleDateTime, final Policy policy) {
        final LocalTime startTimeMorning = setTimeRestriction(policy.getMorningStartTime()
                                                                    .getHour(),
                                                              policy.getMorningStartTime()
                                                                    .getMinute());
        final LocalTime endTimeMorning = setTimeRestriction(policy.getMorningEndTime()
                                                                  .getHour(),
                                                            policy.getMorningEndTime()
                                                                  .getMinute());
        final LocalTime startTimeAfternoon = setTimeRestriction(policy.getAfternoonStartTime()
                                                                      .getHour(),
                                                                policy.getAfternoonStartTime()
                                                                      .getMinute());
        final LocalTime endTimeAfternoon = setTimeRestriction(policy.getAfternoonEndTime()
                                                                    .getHour(),
                                                              policy.getAfternoonEndTime()
                                                                    .getMinute());

        final LocalTime halfDay = setTimeRestriction(12, 0);

        final LocalTime vehicleTime = vehicleDateTime.toLocalTime()
                                                     .plusNanos(1);

        if (vehicleTime.isBefore(halfDay)) {
            return vehicleTime.isAfter(startTimeMorning) && vehicleTime.isBefore(endTimeMorning);
        } else {
            return vehicleTime.isAfter(startTimeAfternoon) && vehicleTime.isBefore(endTimeAfternoon);
        }
    }

    /**
     * Creates a LocalTime based on the input of the parameters
     *
     * @param hour   receives the hour.
     * @param minute receives the minute.
     * @return a LocalTime object.
     */
    private static LocalTime setTimeRestriction(final int hour, final int minute) {
        return LocalDate.now()
                        .atTime(hour, minute)
                        .toLocalTime();
    }

}
