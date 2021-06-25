package pl.coderslab.charity.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Min(1)
    private int quantity;
    @NotNull
    @ManyToMany
    List<Category> categories;
    @NotNull
    @ManyToOne
    Institution institution;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String zipCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    @NotNull
    private LocalDate pickUpDate;
    @NotNull
    private LocalTime pickUpTime;
    private String pickupComment;
    @NotBlank
    private String phone;
    @ManyToOne
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;
    @OneToOne(cascade = CascadeType.REMOVE)
    private DonationStatus status;


    public Donation() {
        this.categories = new ArrayList<>();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @PrePersist
    private void create() {
        this.created = LocalDateTime.now();
    }


    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.created.format(formatter);
    }

}