package com.demo.challengealkemy.service;

import com.demo.challengealkemy.dto.icon.CreateOrUpdateIconDTO;
import com.demo.challengealkemy.helper.ApiHelper;
import com.demo.challengealkemy.helper.ResponseBase;
import com.demo.challengealkemy.model.City;
import com.demo.challengealkemy.model.Icon;
import com.demo.challengealkemy.model.IconContinent;
import com.demo.challengealkemy.repository.interfaces.CityRepository;
import com.demo.challengealkemy.repository.interfaces.IconContinentRepository;
import com.demo.challengealkemy.repository.interfaces.IconRepository;
import com.demo.challengealkemy.service.interfaces.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IconServiceImpl implements IconService {

    @Autowired
    private IconRepository repository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private IconContinentRepository iconContinentRepository;

    @Override
    public List<Icon> getAllIcons() {
        return (List<Icon>) repository.findAll();
    }

    @Override
    public List<IconContinent> getAllIconsWithDetails() {
        return (List<IconContinent>) iconContinentRepository.findAll();
    }

    @Override
    public List<Icon> getByDenomination(String denomination) {
        return repository.findByDenomination(denomination);
    }

    @Override
    public List<Icon> getByCreationDate(String creationDate) throws ParseException {
        String dateString = creationDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateString);
        return repository.findByCreation(date);
    }

    @Override
    public List<Icon> getBtCityId(Long id) {
        return repository.findByCityId(id);
    }

    @Override
    public Optional<Icon> getIconById(Long id) {
        return Optional.empty();
    }

    @Override
    public ResponseBase createIcon(CreateOrUpdateIconDTO iconDTO) {
        if (ApiHelper.validateCreateIconRequest(iconDTO)) {
            return ApiHelper.invalidCreateIconRequest().getBody();
        }
        Optional<City> c = cityRepository.findById(iconDTO.getCityId());

        if (!c.isPresent()) {
            return ApiHelper.invalidCityId().getBody();
        }
        repository.save(ApiHelper.createIconDTOToEntity(iconDTO, c.get()));
        return ApiHelper.iconCreated().getBody();
    }

    @Override
    public ResponseBase updateIcon(Long iconId, CreateOrUpdateIconDTO iconDTO) {
        if (ApiHelper.validateCreateIconRequest(iconDTO)) {
            return ApiHelper.invalidUpdateIconRequest().getBody();
        }
        Optional<City> c = cityRepository.findById(iconDTO.getCityId());
        if (!c.isPresent()) {
            return ApiHelper.invalidCityId().getBody();
        }
        Optional<Icon> i = repository.findById(iconId);
        if (!i.isPresent()) {
            return ApiHelper.invalidIconId().getBody();
        }

        Icon iconExists = i.get();
        repository.save(ApiHelper.updateIconDTOToEntity(iconExists, iconDTO, c.get()));
        return ApiHelper.iconUpdated().getBody();
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Icon> i = repository.findById(id);
        if (i.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
