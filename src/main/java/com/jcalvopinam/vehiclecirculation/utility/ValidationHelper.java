package com.jcalvopinam.vehiclecirculation.utility;

import com.jcalvopinam.vehiclecirculation.exception.DateException;
import com.jcalvopinam.vehiclecirculation.exception.PlateNumberException;
import com.jcalvopinam.vehiclecirculation.exception.TimeException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
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
     * @return the date in LocaDate object.
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
     * Validates that the day is a week day.
     *
     * @param day receive a day.
     * @return a boolean.
     */
    public static boolean isWeekDay(final int day) {
        return day != 6 && day != 7;
    }

}
