package br.com.antoniogabriel.lirelab.util;

import java.util.concurrent.TimeUnit;

class TimeOut {
    public final long value;
    public final TimeUnit unit;

    private TimeOut(long value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static TimeOut of(long value, TimeUnit seconds) {
        return new TimeOut(value, seconds);
    }
}
