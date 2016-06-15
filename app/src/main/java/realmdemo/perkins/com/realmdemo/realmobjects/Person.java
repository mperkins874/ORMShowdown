package realmdemo.perkins.com.realmdemo.realmobjects;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import realmdemo.perkins.com.realmdemo.config.Constants;
import realmdemo.perkins.com.realmdemo.core.RandomDataObject;

/**
 * Created by matt on 6/14/16.
 */

// Well so far so good, no redundant generic passing :)
public class Person extends RealmObject{

    // Something Realm provides that is super handy... The ability to specify a primary key field.
    @PrimaryKey
    private long personId;

    // Same fields as the SugarORM class
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String socialSecurityNo;

    // Ability to ignore fields is also provided by Realm... Handy!
    @Ignore
    private String nickname;

    // Really, these getters and setters are just messy... I'm looking at you Oracle...
    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

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




}
