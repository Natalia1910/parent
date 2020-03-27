package com.lits.api.service;

import com.lits.api.dto.ProfileDTO;
import sun.java2d.cmm.Profile;

import java.util.List;

public interface ProfileService {
    ProfileDTO createProfile(ProfileDTO profile);
    List<ProfileDTO> findAll();
    ProfileDTO findById(int id);
    void updateProfile(ProfileDTO profile);
    void deleteProfile(int id);
}
