package job4j.passports.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "passports",
uniqueConstraints = {@UniqueConstraint(name = "UniqueSerialAndNumber",
columnNames = {"serial", "number"})})
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int serial;
    private int number;

    @Column(name = "fullname")
    private String fullName;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;

    public static Passport of(String fullName, int serial, int number, Date birthday) {
        Passport passport = new Passport();
        passport.fullName = fullName;
        passport.serial = serial;
        passport.number = number;
        passport.birthday = birthday;
        return passport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return id == passport.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Passport{"
                + "id=" + id
                + ", serial=" + serial
                + ", number=" + number
                + ", fullName='" + fullName + '\''
                + ", birthday=" + birthday
                + ", expiration=" + expiration
                + '}';
    }
}