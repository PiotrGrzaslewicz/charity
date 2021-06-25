package pl.coderslab.charity.service;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Institution;

import java.util.List;

@Service
public interface InstitutionService {

    List<Institution> findAll();

    void createInstitution(String name, String description);

    Institution findById(Long id);

    void updateInstitution(Long id, String name, String description);

    void deleteById(Long id);
}
