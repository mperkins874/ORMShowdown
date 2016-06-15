package realmdemo.perkins.com.realmdemo.sugarobjects;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

import realmdemo.perkins.com.realmdemo.config.Constants;
import realmdemo.perkins.com.realmdemo.core.RandomDataObject;

/**
 * Created by matt on 6/14/16.
 */

// First line of code in the class and already something really strange...
// You have to pass the class you're working on as a generic into the SugarRecord class, WHY?
public class Person extends SugarRecord<Person> implements RandomDataObject {

    // Typical data collected about people
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String socialSecurityNo;


    // For fields we don't want to save to the database, SugarORM provides the @Ignore directive... Handy
    @Ignore
    private String nickname;


    // Needed to generate the table, without a blank constructor, Sugar fails...  Doh!
    public Person() {

    }


    // If an object has child objects, or you want to check if a record exists, SugarORM isn't smart enough to do this.
    // You have to build the code yourself.
    @Override
    public void save() {
        Person existingRecord = checkForExistingRecordBySSN(getSocialSecurityNo());

        if(existingRecord != null) {
            this.setId(existingRecord.getId());
        }

        super.save();
    }


    // To define queries of on a specific object, you have to do something like the following...
    // I wish Java was more like C# and provided LINQ or another way of doing language based queries
    public static Person checkForExistingRecordBySSN(String socialSecurityNo) {
        List<Person> records = Person.find(Person.class, "SOCIAL_SECURITY_NO = ?", socialSecurityNo);

        if(records.size() > 0) {
            return records.get(0);
        }

        return null;
    }


    // Getters and setters... Another time I really wish Java was more like C# in this regard...
    // I guess they're not into that whole brevity thing
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSocialSecurityNo() {
        return socialSecurityNo;
    }

    public void setSocialSecurityNo(String socialSecurityNo) {
        this.socialSecurityNo = socialSecurityNo;
    }

    @Override
    public void generateRandomData() {
        this.firstName = Constants.generateRandomString(255);
        this.lastName = Constants.generateRandomString(255);
        this.emailAddress = Constants.generateRandomString(255);
        this.phoneNumber = Constants.generateRandomString(255);
        this.socialSecurityNo = Constants.generateRandomString(255);
    }

    @Override
    public void saveToORM() {
        this.save();
        Log.d("ORM", "Saved record");
    }
}
