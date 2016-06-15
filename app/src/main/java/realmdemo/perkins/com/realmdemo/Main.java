package realmdemo.perkins.com.realmdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import realmdemo.perkins.com.realmdemo.enums.ORMType;
import realmdemo.perkins.com.realmdemo.enums.TestExecuteStyle;
import realmdemo.perkins.com.realmdemo.events.DataManagerExecuteComplete;
import realmdemo.perkins.com.realmdemo.manager.DataManager;

public class Main extends AppCompatActivity {

    ProgressDialog progress;
    DataManager dataManager;

    private RadioButton rbSugar;
    private RadioButton rbRealm;
    private RadioButton rbTimed;
    private RadioButton rbFixed;

    TextView tvTestPrompt;
    EditText text;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getControls();
        setupEvents();

        EventBus.getDefault().register(this);
    }

    private void setupEvents() {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeTest();
            }
        });

        rbTimed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                text.setText("0");
                if(isChecked) {
                    tvTestPrompt.setText(getString(R.string.timedprompt));
                } else {
                    tvTestPrompt.setText(getString(R.string.fixedprompt));
                }
            }
        });

    }

    private void executeTest() {

        progress.setMessage("Running test...");
        progress.show();

        boolean inputValid = false;

        int quantifier = Integer.parseInt(text.getText().toString());

        ORMType ormType;
        TestExecuteStyle style;

        if(rbTimed.isChecked()) {
            style = TestExecuteStyle.TIMED;
            if(quantifier > 60 || quantifier < 0) {
                progress.hide();
                Toast.makeText(this, "Please enter a value more than 0 and less than 60", Toast.LENGTH_LONG).show();
            } else {
                inputValid = true;
            }
        } else {
            style = TestExecuteStyle.FIXED;
            if(quantifier > 999999 || quantifier < 0) {
                progress.hide();
                Toast.makeText(this, "Please enter a value more than 0 and less than 1 million", Toast.LENGTH_LONG).show();
            } else {
                inputValid = true;
            }
        }

        if(rbSugar.isChecked()) {
            ormType = ORMType.SugarORM;
        } else {
            ormType = ORMType.RealmORM;
        }

        if(inputValid) {
            dataManager = new DataManager(ormType, style, quantifier);
            dataManager.execute("");
        }
    }

    public void onEventMainThread(DataManagerExecuteComplete event) {
        progress.hide();
        if(!rbTimed.isChecked()) {
            String time = String.format("%02d min, %02d sec",
                    TimeUnit.MILLISECONDS.toMinutes(dataManager.getResult()),
                    TimeUnit.MILLISECONDS.toSeconds(dataManager.getResult()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dataManager.getResult())));

            Toast.makeText(this, "Result: " + time, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Result: " + String.valueOf(dataManager.getResult()) + " records created", Toast.LENGTH_LONG).show();
        }

    }

    private void getControls() {
        progress = new ProgressDialog(this);
        rbSugar = (RadioButton)findViewById(R.id.rbSugar);
        rbRealm = (RadioButton)findViewById(R.id.rbOrm);
        rbFixed = (RadioButton)findViewById(R.id.rbFixed);
        rbTimed = (RadioButton)findViewById(R.id.rbTimed);
        btnStart = (Button)findViewById(R.id.btnStart);
        tvTestPrompt = (TextView)findViewById(R.id.promptTextView);
        text = (EditText)findViewById(R.id.etQuantifier);
    }
}
