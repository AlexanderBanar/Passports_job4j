package job4j.passports.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int serial;
    private int number;
    private String fullName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;

    public static Passport of(String fullName, int serial, int number) {
        Passport passport = new Passport();
        passport.fullName = fullName;
        passport.serial = serial;
        passport.number = number;
        LocalDate localDate = LocalDate.now().plusYears(10);
        passport.expiration = Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
}