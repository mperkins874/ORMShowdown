package realmdemo.perkins.com.realmdemo.manager;

import android.os.AsyncTask;
import android.util.Log;


import java.util.Date;
import java.util.Calendar;

import de.greenrobot.event.EventBus;
import realmdemo.perkins.com.realmdemo.core.RandomDataObject;
import realmdemo.perkins.com.realmdemo.enums.ORMType;
import realmdemo.perkins.com.realmdemo.enums.TestExecuteStyle;
import realmdemo.perkins.com.realmdemo.events.DataManagerExecuteComplete;
import realmdemo.perkins.com.realmdemo.realmobjects.PersonManager;
import realmdemo.perkins.com.realmdemo.sugarobjects.Person;

/**
 * Created by matt on 6/14/16.
 */
public class DataManager extends AsyncTask<String, String, Boolean>{

    private ORMType ormType;
    private TestExecuteStyle style;
    private int quantifier;
    private long result;

    public DataManager(ORMType ormType, TestExecuteStyle style, int quantifier) {
        this.ormType = ormType;
        this.style = style;
        this.quantifier = quantifier;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        result = 0;
        try {
            if (style == TestExecuteStyle.TIMED) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.MINUTE, quantifier);
                Date finish = calendar.getTime();

                while(new Date().getTime() <= finish.getTime()) {
                    RandomDataObject obj = generateObj();
                    obj.generateRandomData();
                    obj.saveToORM();

                    result += 1;
                }
            } else {
                Date start = new Date();

                for(int i = 0; i < quantifier; i++) {
                    RandomDataObject obj = generateObj();
                    obj.generateRandomData();
                    obj.saveToORM();
                }

                Date end = new Date();

                result = end.getTime() - start.getTime();

            }
        } catch(Exception ex) {
            result = -1;
            Log.e("ORM", ex.toString());
            return false;
        }
        return true;
    }

    private RandomDataObject generateObj() {
        if(ormType == ORMType.RealmORM) {
            return new PersonManager();
        } else {
            return new Person();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        EventBus.getDefault().post(new DataManagerExecuteComplete());
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    public long getResult() {
        return result;
    }

    public ORMType getOrmType() {
        return ormType;
    }

    public void setOrmType(ORMType ormType) {
        this.ormType = ormType;
    }
}
