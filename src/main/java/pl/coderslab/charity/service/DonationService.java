package pl.coderslab.charity.service;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;

import java.util.List;

@Service
public interface DonationService {

    int getTotalQuantity();

    long getCount();

    void save(Donation donation);

    Donation setCategories(Donation donation, List<Long> categories);

    List<Donation> getAllDonationsByUser(User user);

    Donation findById(Long id);

    void deleteById(Long id);

    List<Donation> findAll();
}
