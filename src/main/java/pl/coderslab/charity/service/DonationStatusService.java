package pl.coderslab.charity.service;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.DonationStatus;

@Service
public interface DonationStatusService {

    void save(DonationStatus donationStatus);

    void changeStatus(Long id);
}
