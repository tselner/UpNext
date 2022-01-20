package io.those.upnext.activity;

import static android.Manifest.permission.READ_CALENDAR;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import io.those.upnext.R;
import io.those.upnext.adapter.CalendarListViewAdapter;
import io.those.upnext.model.UpNextAccount;
import io.those.upnext.repository.CalendarRepository;
import io.those.upnext.util.PermissionUtil;
import io.those.upnext.util.WidgetUtil;

public class UpNextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        refreshUI();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtil.isReadCalendarGranted(requestCode, grantResults)) {
            refreshUI();
            WidgetUtil.updateAllWidgets(this);
            Toast.makeText(this, "Thank you!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "This app requires permission to read your calendar.", Toast.LENGTH_LONG).show();
        }
    }

    private void refreshUI() {
        setContentView(R.layout.activity_upnext);

        if (!PermissionUtil.checkReadCalendarPermission(this)) {
            Log.i(this.getClass().getSimpleName(), "Permission not granted, UI not refreshed!");
            return;
        }

        List<UpNextAccount> accounts = new CalendarRepository(getContentResolver()).getAccountsWithCalendars();
        if (accounts == null) {
            Log.i(this.getClass().getSimpleName(), "No accounts available, UI not refreshed!");
            return;
        }

        LinearLayout activityUpnext = findViewById(R.id.activity_upnext);
        LayoutInflater layInf = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        accounts.forEach(account -> {
            LinearLayout layoutAccount = (LinearLayout) layInf.inflate(R.layout.layout_account, null);

            // Account name
            ((TextView) layoutAccount.findViewById(R.id.account_name)).setText(account.getName());

            // List of calendars
            ListView listViewAvailableCalendars = layoutAccount.findViewById(R.id.calendar_list);
            listViewAvailableCalendars.setAdapter(new CalendarListViewAdapter(this, account.getCalendars()));

            activityUpnext.addView(layoutAccount);
        });
    }

    private void checkPermission() {
        if (!PermissionUtil.checkReadCalendarPermission(this)) {
            requestPermissions(new String[] {READ_CALENDAR}, PermissionUtil.READ_CALENDAR_CODE);
        }
    }

    public void onCalendarCheckboxClicked(View view) {
        Log.i(this.getClass().getSimpleName(), String.format("checkbox %d clicked.", view.getId()));
    }
}