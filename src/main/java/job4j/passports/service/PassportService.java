package job4j.passports.service;

import job4j.passports.domain.Passport;
import job4j.passports.repository.PassportRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class PassportService {
    private PassportRepo passportRepo;

    public PassportService(PassportRepo passportRepo) {
        this.passportRepo = passportRepo;
    }

    public Passport save(Passport passport) {
        if (!isSaved(passport.getId())) {
            passport.setExpiration(getCalculatedExpDate(passport));
        }
        return passportRepo.save(passport);
    }

    private Date getCalculatedExpDate(Passport passport) {
        LocalDate birthdayLD = passport.getBirthday().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int fullYears = LocalDate.now().getYear() - birthdayLD.getYear();
        int delta = 0;
        if (fullYears < 20) {
            delta = 20 - fullYears;
        }
        if (fullYears > 20 && fullYears < 45) {
            delta = 45 - fullYears;
        }
        Date expiration = new Date(9999, 01, 01);
        if (delta > 0) {
            expiration = Date.from((LocalDate.now().plusYears(delta))
                    .atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return expiration;
    }

    public boolean isSaved(int id) {
        return passportRepo.findById(id).isPresent();
    }

    public boolean delete(Passport passport) {
        if (isSaved(passport.getId())) {
            passportRepo.delete(passport);
            return true;
        }
        return false;
    }

    public List<Passport> findAll() {
        return (List<Passport>) passportRepo.findAll();
    }

    public List<Passport> findBySerial(int serial) {
        return passportRepo.findBySerial(serial);
    }

    public List<Passport> findAllByExpirationBefore(Date date) {
        return passportRepo.findAllByExpirationBefore(date);
    }
}