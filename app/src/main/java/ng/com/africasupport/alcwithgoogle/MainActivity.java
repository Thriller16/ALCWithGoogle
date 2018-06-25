package ng.com.africasupport.alcwithgoogle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    String dateInMills, mYear, mMonth, mDay;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        calendarView = findViewById(R.id.calender_view);
        textView = findViewById(R.id.date);


        long currentDate = System.currentTimeMillis();

        String displayDate = new SimpleDateFormat().format(currentDate);
        StringTokenizer stringTokenizer = new StringTokenizer(displayDate);

        textView.setText("Monday, " + stringTokenizer.nextToken());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                dateInMills = "" + calendarView.getDate();
                mYear = "" + year;
                mMonth = "" + month;
                mDay = "" + day;

                startActivity(new Intent(MainActivity.this, ViewEvent.class)
                        .putExtra("dateInMills", dateInMills)
                        .putExtra("year", mYear)
                        .putExtra("month", mMonth)
                        .putExtra("day", mDay));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }
}
