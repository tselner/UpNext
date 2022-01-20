package io.those.upnext.model;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.ArrayList;
import java.util.List;

public class UpNextAccount implements Comparable<UpNextAccount> {
    private long id;
    private String name;
    private List<UpNextCalendar> calendars;

    public static UpNextAccount of(long id, String name, List<UpNextCalendar> calendars) {
        UpNextAccount account = new UpNextAccount();
        account.setId(id);
        account.setName(name);
        account.setCalendars(calendars);
        return account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UpNextCalendar> getCalendars() {
        if (calendars == null) {
            calendars = new ArrayList<>();
        }
        return calendars;
    }

    public void setCalendars(List<UpNextCalendar> calendars) {
        this.calendars = calendars;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(UpNextAccount o) {
        return new CompareToBuilder()
                .append(this.getId(), o.getId())
                .toComparison();
    }
}
