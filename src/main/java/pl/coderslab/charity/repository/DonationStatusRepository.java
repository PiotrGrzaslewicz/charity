package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.model.DonationStatus;

public interface DonationStatusRepository extends JpaRepository<DonationStatus, Long> {



}
