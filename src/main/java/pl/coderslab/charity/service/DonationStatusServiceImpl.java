package pl.coderslab.charity.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.DonationStatus;
import pl.coderslab.charity.repository.DonationStatusRepository;

@Service
@Primary
public class DonationStatusServiceImpl implements DonationStatusService {

    private final DonationStatusRepository donationStatusRepository;

    public DonationStatusServiceImpl(DonationStatusRepository donationStatusRepository) {
        this.donationStatusRepository = donationStatusRepository;
    }

    @Override
    public void save(DonationStatus donationStatus) {
     donationStatusRepository.save(donationStatus);
    }

    @Override
    public void changeStatus(Long id) {
        DonationStatus donationStatus = donationStatusRepository.findById(id).get();
        donationStatus.setStatus(1);
        donationStatusRepository.save(donationStatus);
    }
}
