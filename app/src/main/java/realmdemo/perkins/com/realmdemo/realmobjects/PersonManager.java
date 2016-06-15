package realmdemo.perkins.com.realmdemo.realmobjects;

import android.util.Log;

import io.realm.Realm;
import realmdemo.perkins.com.realmdemo.config.Constants;
import realmdemo.perkins.com.realmdemo.core.RandomDataObject;

/**
 * Created by matt on 6/14/16.
 */
public class PersonManager implements RandomDataObject{

    private Realm realm;
    private Person person;

    // And for the fun parts...

    public PersonManager() {
        realm = Realm.getDefaultInstance();
        person = new Person();
    }

    @Override
    public void generateRandomData() {
        person.setFirstName(Constants.generateRandomString(255));
        person.setLastName(Constants.generateRandomString(255));
        person.setEmailAddress(Constants.generateRandomString(255));
        person.setPhoneNumber(Constants.generateRandomString(255));
        person.setSocialSecurityNo(Constants.generateRandomString(255));
    }

    @Override
    public void saveToORM() {
        saveRealmPerson(person);
        Log.d("ORM", "Saved record");
    }

    // All Realm saves require a transaction, which is a much safer way to execute data IO
    public static void saveRealmPerson(final Person person){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(person);
            }
        });

    }
}
