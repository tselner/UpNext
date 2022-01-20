package io.those.upnext.repository;

import static java.util.stream.Collectors.groupingBy;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.those.upnext.model.UpNextAccount;
import io.those.upnext.model.UpNextCalendar;

public class CalendarRepository extends Repository<UpNextCalendar> {

    public CalendarRepository(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected String[] getProjection() {
        return new String[]{
                Calendars._ID,                   // 0
                Calendars.NAME,                  // 1
                Calendars.ACCOUNT_NAME,          // 2
                Calendars.CALENDAR_DISPLAY_NAME, // 3
                Calendars.CALENDAR_COLOR,        // 4
                Calendars.VISIBLE                // 5
        };
    }

    @Override
    protected String getSelection() {
        return null;
    }

    @Override
    protected String getSortOrder() {
        return null;
    }

    @Override
    protected Uri getProviderUri() {
        return Calendars.CONTENT_URI;
    }

    @Override
    protected UpNextCalendar toItem(Cursor cur) {
        return UpNextCalendar.of(
                cur.getLong(0),
                cur.getString(1),
                cur.getString(2),
                cur.getString(3),
                cur.getInt(4),
                cur.getInt(5) == 1 ? Boolean.TRUE : Boolean.FALSE
        );
    }

    public List<UpNextCalendar> getCalendars() {
        return getItems(null);
    }

    private Map<String, List<UpNextCalendar>> getCalendarsByAccountName() {
        return getCalendars().stream().collect(groupingBy(UpNextCalendar::getAccountName));
    }

    public List<UpNextAccount> getAccountsWithCalendars() {
        Map<String, List<UpNextCalendar>> calendarsByAccountName = getCalendarsByAccountName();
        List<UpNextAccount> accounts = new ArrayList<>();

        List<String> accountNames = new ArrayList<>(calendarsByAccountName.keySet());

        for (int i = 0; i < calendarsByAccountName.size(); i++) {
            String accountName = accountNames.get(i); // Need a (fictional) id for AccountsListViewAdapter
            UpNextAccount account = UpNextAccount.of(i, accountName, calendarsByAccountName.get(accountName));
            accounts.add(account);
        }

        return accounts;
    }
}