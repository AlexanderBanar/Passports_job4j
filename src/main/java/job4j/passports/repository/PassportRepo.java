package job4j.passports.repository;

import job4j.passports.domain.Passport;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface PassportRepo extends CrudRepository<Passport, Integer> {
    Passport findBySerial(int serial);

    List<Passport> findAllByExpirationBefore(Date date);
}
