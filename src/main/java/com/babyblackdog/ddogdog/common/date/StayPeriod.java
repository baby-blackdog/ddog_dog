package com.babyblackdog.ddogdog.common.date;

import java.time.LocalDate;

public class StayPeriod {

    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public StayPeriod(LocalDate checkIn, LocalDate checkOut, TimeProvider timeProvider) {
        this.checkIn = adjustIfInvalid(checkIn, timeProvider);
        LocalDate validCheckOut = adjustIfInvalid(checkOut, timeProvider);
        this.checkOut = ensureCheckOutIsAfterCheckIn(this.checkIn, validCheckOut);
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    private LocalDate adjustIfInvalid(LocalDate date, TimeProvider timeProvider) {
        if (date == null || timeProvider.getCurrentDate().isAfter(date)) {
            return timeProvider.getCurrentDate();
        }
        return date;
    }

    private LocalDate ensureCheckOutIsAfterCheckIn(LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            return checkIn.plusDays(1);
        }
        return checkOut;
    }
}
