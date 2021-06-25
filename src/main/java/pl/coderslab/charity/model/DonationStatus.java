package pl.coderslab.charity.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
public class DonationStatus implements Comparable<DonationStatus> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updated;


    @PrePersist
    private void create() {
        this.updated = LocalDateTime.now();
    }

    @PreUpdate
    private void update() {
        this.updated = LocalDateTime.now();
    }

    @Override
    public int compareTo(DonationStatus o) {
        if (this.status.equals(o.status)) {
            return this.updated.compareTo(o.updated);
        } else {
            return this.status.compareTo(o.status);
        }
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.updated.format(formatter);
    }
}
