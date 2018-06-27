package ng.com.africasupport.alcwithgoogle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mFireAuth;
    FirebaseUser mCurrentUser;

    CalendarView calendarView;
    String dateInMills, mYear, mMonth, mDay;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFireAuth = FirebaseAuth.getInstance();


        calendarView = findViewById(R.id.calender_view);
        textView = findViewById(R.id.date);


        long currentDate = System.currentTimeMillis();

        String displayDate = new SimpleDateFormat().format(currentDate);
        StringTokenizer stringTokenizer = new StringTokenizer(displayDate);


        StringTokenizer newToken = new StringTokenizer(stringTokenizer.nextToken(), "/");

        String month = newToken.nextToken();
        String day = newToken.nextToken();
        String year = " 20" +newToken.nextToken();

        textView.setText("Monday, "  + day+addSuffix(day)+" " + convertToMonth(month) + " " + year);

//        Toast.makeText(this, "" + calendarView.getFirstDayOfWeek(), Toast.LENGTH_SHORT).show();

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
            case R.id.logout:
                mFireAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public String convertToMonth(String number){
        String monthName = "";
        switch (number){
            case "1":
                monthName = "January";
                break;

            case "2":
                monthName = "February";
                break;

            case "3":
                monthName = "March";
                break;

            case "4":
                monthName = "April";
                break;

            case "5":
                monthName = "May";
                break;

            case "6":
                monthName = "June";
                break;

            case "7":
                monthName = "July";
                break;

            case "8":
                monthName = "August";
                break;

            case "9":
                monthName = "September";
                break;

            case "10":
                monthName = "October";
                break;

            case "11":
                monthName = "November";
                break;

            case "12":
                monthName = "December";
                break;
        }

        return monthName;
    }

    public String addSuffix(String day){
        String suffix = "";

        if(day.equals("1") || day.equals("21") || day.equals("31")){
            suffix = "st";
        }

        else if(day.equals("2") || day.equals("22")){
            suffix = "nd";
        }
        else if(day.equals("3") || day.equals("23")){
            suffix = "rd";
        }

        else{
            suffix = "th";
        }
        return suffix;
    }

    @Override
    protected void onStart() {
        mCurrentUser = mFireAuth.getCurrentUser();
        if(mCurrentUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
