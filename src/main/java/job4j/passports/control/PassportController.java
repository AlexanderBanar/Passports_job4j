package job4j.passports.control;

import job4j.passports.domain.Passport;
import job4j.passports.repository.PassportRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/passports")
public class PassportController {
    private PassportRepo passportRepo;

    public PassportController(PassportRepo passportRepo) {
        this.passportRepo = passportRepo;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        return new ResponseEntity<>(
                passportRepo.save(passport),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update?id={id}")
    public ResponseEntity<Void> update(@RequestBody Passport passport,
                                       @PathVariable int id) {
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional passOpt = passportRepo.findById(id);
        if (passOpt.isPresent() && passport.getId() == id) {
            passportRepo.save(passport);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete?id={id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Passport passport = new Passport();
        passport.setId(id);
        passportRepo.delete(passport);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public List<Passport> findAll() {
        return StreamSupport.stream(
                passportRepo.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/find?serial={serial}")
    public ResponseEntity<Passport> findBySerial(@PathVariable int serial) {
        if (serial <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(
                passportRepo.findBySerial(serial),
                HttpStatus.OK
        );
    }

    @GetMapping("/unavailable")
    public List<Passport> findExpired() {
        return passportRepo.findAllByExpirationBefore(new Date());
    }

    @GetMapping("/find-replaceable")
    public List<Passport> findReplaceable() {
        LocalDate localDate = LocalDate.now().minusMonths(3);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return passportRepo.findAllByExpirationBefore(date);
    }
}