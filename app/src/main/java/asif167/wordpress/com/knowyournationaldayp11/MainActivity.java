package asif167.wordpress.com.knowyournationaldayp11;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayAdapter<String> aaToDo;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);

        String item1 = "Singapore National Day is on 9 Aug";
        String item2 = "Singapore 52 Years Old";
        String item3 = "Theme is '#OneNationTogether'";

        arrayList.add(item1);
        arrayList.add(item2);
        arrayList.add(item3);

        aaToDo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        lv.setAdapter(aaToDo);

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String id = prefs.getString("id", "Null");


        if (id.equalsIgnoreCase("Null")) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout passPhrase =
                    (LinearLayout) inflater.inflate(R.layout.passphrase, null);
            final EditText etPassphrase = (EditText) passPhrase
                    .findViewById(R.id.editTextPassPhrase);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter")
                    .setView(passPhrase)
                    .setCancelable(false)
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            String pass = etPassphrase.getText().toString();
                            if (pass.equalsIgnoreCase("738964")) {

                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor prefEdit = prefs.edit();
                                prefEdit.putString("id", "Okayy");
                                prefEdit.commit();

                            } else {
                                Toast.makeText(MainActivity.this, "You have entered the wrong password", Toast.LENGTH_SHORT).show();
                                onStart();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {

        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "You clicked no",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (id == R.id.send) {
            final String[] list = new String[]{"Email", "SMS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {

                                String message = "";
                                for (int a = 0; a < arrayList.size(); a++) {
                                    message += arrayList.get(a).toString() + "\n";
                                }

                                Intent email = new Intent(Intent.ACTION_SEND);
                                // Put essentials like email address, subject & body text
                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"jason_lim@rp.edu.sg"});

                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "Know Your National Day");

                                email.putExtra(Intent.EXTRA_TEXT,
                                        message);
                                // This MIME type indicates email
                                email.setType("message/rfc822");
                                // createChooser shows user a list of app that can handle
                                // this MIME type, which is, email
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));


                            } else {

                                String message = "";
                                for (int a = 0; a < arrayList.size(); a++) {
                                    message += arrayList.get(a).toString() + "\n";
                                }

                                Intent sms = new Intent(Intent.ACTION_VIEW);
                                sms.setType("vnd.android-dir/mms-sms");
                                sms.putExtra("address","81896421");
                                sms.putExtra("sms_body",message);
                                startActivity(sms);
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View viewDialog = inflater.inflate(R.layout.quiz, null);

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
            myBuilder.setView(viewDialog);
            myBuilder.setTitle("Test Yourself");

            myBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                // The parameter "which" is the item index
                // clicked, starting from 0
                public void onClick(DialogInterface dialog, int which) {

                    String ans1,ans2 = "",ans3 = "";
                    int count = 0;

                    RadioGroup rg = (RadioGroup) viewDialog.findViewById(R.id.radiogroup1);

                    // Get the Id of the selected radio button in the RadioGroup
                    int selectedButtonId = rg.getCheckedRadioButtonId();

                    // Get the radio button object from the Id we had gotten above
                    RadioButton rb = (RadioButton) viewDialog.findViewById(selectedButtonId);

                    ans1 = rb.getText().toString();

                    // Get the RadioGroup object
                    RadioGroup rg2 = (RadioGroup) viewDialog.findViewById(R.id.radiogroup2);

                    // Get the Id of the selected radio button in the RadioGroup
                    int selectedButtonId2 = rg2.getCheckedRadioButtonId();

                    // Get the radio button object from the Id we had gotten above
                    RadioButton rb2 = (RadioButton) viewDialog.findViewById(selectedButtonId2);

                    ans2 = rb2.getText().toString();

                    // Get the RadioGroup object
                    RadioGroup rg3 = (RadioGroup) viewDialog.findViewById(R.id.radiogroup3);

                    // Get the Id of the selected radio button in the RadioGroup
                    int selectedButtonId3 = rg3.getCheckedRadioButtonId();

                    // Get the radio button object from the Id we had gotten above
                    RadioButton rb3 = (RadioButton) viewDialog.findViewById(selectedButtonId3);

                    ans3 = rb3.getText().toString();

                    if(ans1.equalsIgnoreCase("NO")){
                        count +=1;
                    }else{}

                    if(ans2.equalsIgnoreCase("YES")){
                        count +=1;
                    }else{}

                    if(ans3.equalsIgnoreCase("YES")){
                        count +=1;
                    }else{}

                    Toast.makeText(MainActivity.this, "Total Score is "+count, Toast.LENGTH_SHORT).show();


                }
            });

            myBuilder.setNegativeButton("DON'T KNOW LAH",null);
            AlertDialog myDialog = myBuilder.create();
            myDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

}
