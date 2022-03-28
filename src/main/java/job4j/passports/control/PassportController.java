package job4j.passports.control;

import job4j.passports.domain.Passport;
import job4j.passports.service.PassportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/passports")
public class PassportController {
    private PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        Passport savedPassport = passportService.save(passport);
        if (savedPassport.getId() == 0) {
            return new ResponseEntity<>(
                    Passport.of("null", 0, 0, null),
                    HttpStatus.BAD_REQUEST
            );
        } else {
            return new ResponseEntity<>(
                    savedPassport,
                    HttpStatus.CREATED
            );
        }
    }

    @PutMapping("/update?id={id}")
    public ResponseEntity<Void> update(@RequestBody Passport passport,
                                       @PathVariable int id) {
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        if (passportService.isSaved(id) && passport.getId() == id) {
            passportService.save(passport);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete?id={id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (id <= 0) {
            return ResponseEntity.notFound().build();
        }
        Passport passport = new Passport();
        passport.setId(id);
        if (passportService.delete(passport)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find")
    public List<Passport> findAll() {
        return passportService.findAll();
    }

    @GetMapping("/find?serial={serial}")
    public List<Passport> findBySerial(@PathVariable int serial) {
        if (serial <= 0) {
            return Collections.emptyList();
        }
        return passportService.findBySerial(serial);
    }

    @GetMapping("/unavailable")
    public List<Passport> findExpired() {
        return passportService.findAllByExpirationBefore(new Date());
    }

    @GetMapping("/find-replaceable")
    public List<Passport> findReplaceable() {
        LocalDate localDate = LocalDate.now().minusMonths(3);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return passportService.findAllByExpirationBefore(date);
    }
}