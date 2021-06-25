package pl.coderslab.charity.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Primary
public class DonationServiceImpl implements DonationService {

    private DonationRepository donationRepository;
    private CategoryRepository categoryRepository;

    @Override
    public void save(Donation donation) {
        donationRepository.save(donation);
    }

    @Override
    public Donation setCategories(Donation donation, List<Long> categories) {
       donation.setCategories(
               categories.stream()
                .map(c -> categoryRepository.findById(c))
                .map(c->c.get())
                .collect(Collectors.toList())
       );
       return donation;
    }

    public DonationServiceImpl(DonationRepository donationRepository, CategoryRepository categoryRepository) {
        this.donationRepository = donationRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public long getCount() {
        return donationRepository.count();
    }

    @Override
    public int getTotalQuantity() {
        return donationRepository.findAll().stream()
                .mapToInt(Donation::getQuantity)
                .sum();

    }

    @Override
    public List<Donation> getAllDonationsByUser(User user) {
        return donationRepository.findAllByUser(user);
    }

    @Override
    public Donation findById(Long id) {
        return donationRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        donationRepository.deleteById(id);
    }

    @Override
    public List<Donation> findAll() {
        return donationRepository.findAll();
    }
}