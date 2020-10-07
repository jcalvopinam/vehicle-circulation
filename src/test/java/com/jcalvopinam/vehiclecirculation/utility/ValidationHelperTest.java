package com.jcalvopinam.vehiclecirculation.utility;

import com.jcalvopinam.vehiclecirculation.domain.Policy;
import com.jcalvopinam.vehiclecirculation.domain.Time;
import com.jcalvopinam.vehiclecirculation.exception.DateException;
import com.jcalvopinam.vehiclecirculation.exception.PlateNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ValidationHelperTest {

    public static final String DATE = "2020/10/05";
    public static final String PLATE_NUMBER = "AAA-0001";

    @Test
    public void validPlateNumberTest() {
        final char validPlate = ValidationHelper.getLastNumber("ASD-1234");
        Assertions.assertEquals('4', validPlate);
    }

    @Test
    public void plateNumberExceptionTest() {
        Assertions.assertThrows(PlateNumberException.class, () -> {
            ValidationHelper.getLastNumber(null);
            ValidationHelper.getLastNumber("AAAAA-1234");
            ValidationHelper.getLastNumber("AA-1234");
            ValidationHelper.getLastNumber("AAA-12345");
            ValidationHelper.getLastNumber("AAA-123");
            ValidationHelper.getLastNumber("AAAAA");
            ValidationHelper.getLastNumber("ASD1234");
            ValidationHelper.getLastNumber("ASD+1234");
            ValidationHelper.getLastNumber("1234");
            ValidationHelper.getLastNumber("");
        });
    }

    @Test
    public void validDateTimeTest() {
        final LocalDateTime validDateTime = ValidationHelper.dateTime(DATE, "07:07");
        Assertions.assertEquals(2020, validDateTime.getYear());
        Assertions.assertEquals(10, validDateTime.getMonthValue());
        Assertions.assertEquals(5, validDateTime.getDayOfMonth());
        Assertions.assertEquals(7, validDateTime.getHour());
        Assertions.assertEquals(7, validDateTime.getMinute());
    }

    @Test
    public void dateTimeExceptionTest() {
        Assertions.assertThrows(DateTimeParseException.class, () -> ValidationHelper.dateTime("AAA", "AA"));
        Assertions.assertThrows(DateTimeParseException.class, () -> ValidationHelper.dateTime("31/02/2020", "99:99"));
        Assertions.assertThrows(DateTimeParseException.class, () -> ValidationHelper.dateTime("41-01-2020", "-1:-1"));
        Assertions.assertThrows(DateTimeParseException.class, () -> ValidationHelper.dateTime("", ""));
        Assertions.assertThrows(DateException.class, () -> ValidationHelper.dateTime(null, null));
    }

    @Test
    public void validWeekDayTest() {
        boolean validDay = ValidationHelper.isWeekDay(DayOfWeek.MONDAY);
        Assertions.assertTrue(validDay);
    }

    @Test
    public void invalidWeekDayTest() {
        boolean invalidDay1 = ValidationHelper.isWeekDay(DayOfWeek.SATURDAY);
        Assertions.assertFalse(invalidDay1);
        boolean invalidDay7 = ValidationHelper.isWeekDay(DayOfWeek.SUNDAY);
        Assertions.assertFalse(invalidDay7);
    }

    @Test
    public void isCirculationRestrictedOutOfHourTest() {
        final boolean beforeStartingTimeMorning = validateCirculation("06:59");
        Assertions.assertFalse(beforeStartingTimeMorning);

        final boolean afterStartingTimeMorning = validateCirculation("07:01");
        Assertions.assertTrue(afterStartingTimeMorning);

        final boolean beforeEndingTimeMorning = validateCirculation("09:29");
        Assertions.assertTrue(beforeEndingTimeMorning);

        final boolean afterEndingTimeMorning = validateCirculation("09:30");
        Assertions.assertFalse(afterEndingTimeMorning);

        final boolean beforeStartingTimeAfternoon = validateCirculation("15:59");
        Assertions.assertFalse(beforeStartingTimeAfternoon);

        final boolean afterEndingTimeAfternoon = validateCirculation("19:30");
        Assertions.assertFalse(afterEndingTimeAfternoon);
    }

    private boolean validateCirculation(final String timeValue) {
        final LocalDateTime validDateTime = ValidationHelper.dateTime(ValidationHelperTest.DATE, timeValue);
        final char validPlate = ValidationHelper.getLastNumber(ValidationHelperTest.PLATE_NUMBER);
        return ValidationHelper.isCirculationRestricted(validDateTime, validPlate, createPolicy());
    }

    private Policy createPolicy() {
        final LocalDateTime morningStartLocalTime = ValidationHelper.dateTime(ValidationHelperTest.DATE, "07:00");
        final LocalDateTime morningEndLocalTime = ValidationHelper.dateTime(ValidationHelperTest.DATE, "09:30");
        final LocalDateTime afterStartLocalTime = ValidationHelper.dateTime(ValidationHelperTest.DATE, "16:00");
        final LocalDateTime afterEndLocalTime = ValidationHelper.dateTime(ValidationHelperTest.DATE, "19:30");

        final Time morningStartTime = new Time(morningStartLocalTime.getHour(), morningStartLocalTime.getMinute());
        final Time morningEndTime = new Time(morningEndLocalTime.getHour(), morningEndLocalTime.getMinute());
        final Time afternoonStartTime = new Time(afterStartLocalTime.getHour(), afterStartLocalTime.getMinute());
        final Time afternoonEndTime = new Time(afterEndLocalTime.getHour(), afterEndLocalTime.getMinute());
        return Policy.builder()
                     .morningStartTime(morningStartTime)
                     .morningEndTime(morningEndTime)
                     .afternoonStartTime(afternoonStartTime)
                     .afternoonEndTime(afternoonEndTime)
                     .build();
    }

}