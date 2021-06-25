package pl.coderslab.charity.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

@Primary
@Service
public class InstitutionServiceImpl implements InstitutionService{

    private final InstitutionRepository institutionRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }


    @Override
    public List<Institution> findAll() {
        return institutionRepository.findAll();
    }

    @Override
    public void createInstitution(String name, String description) {
        Institution institution = new Institution();
        institution.setName(name);
        institution.setDescription(description);
        institutionRepository.save(institution);
    }

    @Override
    public Institution findById(Long id) {
        return institutionRepository.findById(id).get();
    }

    @Override
    public void updateInstitution(Long id, String name, String description) {
        Institution institution = institutionRepository.findById(id).get();
        institution.setName(name);
        institution.setDescription(description);
        institutionRepository.save(institution);
    }

    @Override
    public void deleteById(Long id) {
        institutionRepository.deleteById(id);
    }
}