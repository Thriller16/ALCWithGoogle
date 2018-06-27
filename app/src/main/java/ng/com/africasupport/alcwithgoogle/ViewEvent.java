package ng.com.africasupport.alcwithgoogle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewEvent extends AppCompatActivity {

    RelativeLayout relativeLayout;
    String dateClicked, currentYear, currentMonth, currentDay;
    FloatingActionButton editFab;
    EditText editText;
    DatabaseAccess databaseAccess;
    List<Event> eventList = new ArrayList<>();
    String fulldate;
    ProgressDialog mProgressDialog;



    DatabaseReference mEventDatabase;
    FirebaseAuth mFireAuth;
    FirebaseUser mCurrentUser;

    String actionDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

//        This is where the views will be setup
        relativeLayout = findViewById(R.id.home_layout);
        editFab = findViewById(R.id.edit_fab);
        editText = findViewById(R.id.edit_text);
        mProgressDialog = new ProgressDialog(this);


//        Firebase database
        mFireAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFireAuth.getCurrentUser();

        mEventDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(mCurrentUser.getUid());
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        editText.setEnabled(false);
        //      Gathering of all the string extras
        dateClicked = getIntent().getStringExtra("dateInMills");
        currentYear = getIntent().getStringExtra("year");
        currentMonth = getIntent().getStringExtra("month");
        currentDay = getIntent().getStringExtra("day");
        fulldate = currentDay + "/" + (Integer.parseInt(currentMonth)+1) +"/"+ currentYear;

        actionDate = currentDay+addSuffix(currentDay)+" " + convertToMonth((Integer.parseInt(currentMonth)+1)) + " " + currentYear;

        eventList = databaseAccess.checkWithDate(fulldate);
        getSupportActionBar().setTitle(actionDate);

        if (!eventList.isEmpty()) {
            editText.setText(eventList.get(0).getEvent());
        }

//        This is wher ei set onclick listeners for the fab
        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.isEnabled()) {
                    changeFocusToLayout();
                    if (eventList.isEmpty()) {


                        if (editText.getText().toString().equals("")) {
                            Toast.makeText(ViewEvent.this, "Write an event to save", Toast.LENGTH_SHORT).show();

                        }
                        else if (!editText.getText().toString().equals("")) {


                            mProgressDialog.show();
                            mProgressDialog.setTitle("Please Wait");
                            mProgressDialog.setMessage("Adding your event");

//                            Update on firebase
                            HashMap<String, Object> eventHashmap = new HashMap<>();
                            eventHashmap.put("day", fulldate);
                            eventHashmap.put("event", editText.getText().toString());


                            mEventDatabase.child(actionDate).setValue(eventHashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProgressDialog.dismiss();
                                    databaseAccess.add(fulldate, editText.getText().toString());
                                    Toast.makeText(ViewEvent.this, "Event has been added to today", Toast.LENGTH_SHORT).show();
                                    eventList = databaseAccess.checkWithDate(fulldate);
                                }
                            });
                        }


                    } else {


                        if (editText.getText().toString().equals("")) {
                            Toast.makeText(ViewEvent.this, "Write an event to save", Toast.LENGTH_SHORT).show();
                            editText.setText(eventList.get(0).getEvent());

                        }

                        else if (!editText.getText().toString().equals("")) {
                            mProgressDialog.show();
                            mProgressDialog.setTitle("Please Wait");
                            mProgressDialog.setMessage("Adding your event");
//                            Update on firebase

                            HashMap<String, Object> eventHashmap = new HashMap<>();
                            eventHashmap.put("day", fulldate);
                            eventHashmap.put("event", editText.getText().toString());

                            mEventDatabase.child(actionDate).setValue(eventHashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProgressDialog.dismiss();
                                    databaseAccess.updateEvents(editText.getText().toString(), fulldate);
                                    Toast.makeText(ViewEvent.this, "Done editing this event", Toast.LENGTH_SHORT).show();
                                    eventList = databaseAccess.checkWithDate(fulldate);

                                }
                            });


                        }

                    }

                } else {
                    changefocusToEdt();
                }
            }
        });
    }


//    This where the menu will be inflated
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_event_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void changefocusToEdt() {
        relativeLayout.setFocusable(false);
        relativeLayout.setFocusableInTouchMode(false);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editFab.setImageResource(R.drawable.ic_done_black_24dp);
        Toast.makeText(this, "Edit Mode On", Toast.LENGTH_SHORT).show();

        if (eventList.isEmpty()) {
            editText.setText("");
        }
        editText.setEnabled(true);
    }


//    This changs the focus whenever the fab is pressed
    public void changeFocusToLayout() {

        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        relativeLayout.setFocusable(true);
        editFab.setImageResource(R.drawable.ic_edit_black_24dp);
        relativeLayout.setFocusableInTouchMode(true);
        editText.setEnabled(false);
    }


//    This handles what happens when the back button is pressed
    @Override
    public void onBackPressed() {
        if (editText.isEnabled()) {
            editText.setEnabled(false);
            editFab.setImageResource(R.drawable.ic_edit_black_24dp);
        } else {
            finish();
        }
    }



//THis will create teh menu to be inflated by the app
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {

            if(eventList.isEmpty()){
                Toast.makeText(this, "No event has been added to this day", Toast.LENGTH_SHORT).show();
            }
            else {
                mEventDatabase.child(actionDate).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        databaseAccess.delete(fulldate);
                        Toast.makeText(ViewEvent.this, "Event has been deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        }

        return super.onOptionsItemSelected(item);
    }


    public String convertToMonth(int number){
        String monthName = "";
        switch (number){
            case 1:
                monthName = "January";
                break;

            case 2:
                monthName = "February";
                break;

            case 3:
                monthName = "March";
                break;

            case 4:
                monthName = "April";
                break;

            case 5:
                monthName = "May";
                break;

            case 6:
                monthName = "June";
                break;

            case 7:
                monthName = "July";
                break;

            case 8:
                monthName = "August";
                break;

            case 9:
                monthName = "September";
                break;

            case 10:
                monthName = "October";
                break;

            case 11:
                monthName = "November";
                break;

            case 12:
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
}
