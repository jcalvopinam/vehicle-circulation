package com.jcalvopinam.vehiclecirculation.utility;

import com.jcalvopinam.vehiclecirculation.exception.DateException;
import com.jcalvopinam.vehiclecirculation.exception.PlateNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ValidationHelperTest {

    @Test
    public void validPlateNumberTest() {
        final int validPlate = ValidationHelper.getLastNumber("ASD-1234");
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
    public void validDateTImeTest() {
        final LocalDateTime validDateTime = ValidationHelper.dateTime("2020/10/05", "07:07");
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
    public void validVayOfMonthTest() {
        boolean validDay = ValidationHelper.isWeekDay(2);
        Assertions.assertTrue(validDay);
    }

    @Test
    public void invalidVayOfMonthTest() {
        boolean invalidDay1 = ValidationHelper.isWeekDay(1);
        Assertions.assertFalse(invalidDay1);
        boolean invalidDay7 = ValidationHelper.isWeekDay(7);
        Assertions.assertFalse(invalidDay7);
    }

}