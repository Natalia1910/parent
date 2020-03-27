package com.lits.model.service;

import com.lits.api.dto.ProfileDTO;
import com.lits.api.service.ProfileService;
import com.lits.model.dao.ProfileDao;
import com.lits.model.entity.Profile;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileServiceImpl implements ProfileService {
    private ProfileDao repo;

    public ProfileServiceImpl(ProfileDao repo) {
        this.repo = repo;
    }

    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setAge(profileDTO.getAge());
        profile.setLastName(profileDTO.getLastName());
        profile.setName(profileDTO.getName());

        return profileToDTO(repo.save(profile));
    }

    public List<ProfileDTO> findAll() {
        return repo.findAll().stream().map(this::profileToDTO).collect(Collectors.toList());
    }

    @Override
    public ProfileDTO findById(int id) {
        Profile profile = repo.findById(id);
        if (profile == null) {
            return null;
        }
        return profileToDTO(profile);
    }

    @Override
    public void updateProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setId(profileDTO.getId());
        profile.setAge(profileDTO.getAge());
        profile.setLastName(profileDTO.getLastName());
        profile.setName(profileDTO.getName());
        profileToDTO(repo.save(profile));
    }

    @Override
    public void deleteProfile(int id) {
        repo.delete(id);
    }

    private ProfileDTO profileToDTO(Profile profile) {
        ProfileDTO p = new ProfileDTO();
        p.setId(profile.getId());
        p.setAge(profile.getAge());
        p.setName(profile.getName());
        p.setLastName(profile.getLastName());
        return p;
    }
}
