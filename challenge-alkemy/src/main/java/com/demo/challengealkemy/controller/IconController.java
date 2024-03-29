package com.demo.challengealkemy.controller;

import com.demo.challengealkemy.dto.icon.CreateOrUpdateIconDTO;
import com.demo.challengealkemy.dto.icon.IconContinentDTO;
import com.demo.challengealkemy.dto.icon.IconDTO;
import com.demo.challengealkemy.helper.ApiHelper;
import com.demo.challengealkemy.helper.ResponseBase;
import com.demo.challengealkemy.service.interfaces.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/icon")
public class IconController {

    @Autowired
    private IconService service;

    @GetMapping("/get-all-icons")
    public ResponseEntity<List<IconDTO>> getIcons() {
        List<IconDTO> icons = ApiHelper.IconEntityToIconDTO(service.getAllIcons());
        return ResponseEntity.ok(icons);
    }

    @GetMapping("/get-all-icons-with-details")
    public ResponseEntity<List<IconContinentDTO>> getAllIcons() {
        List<IconContinentDTO> icons = ApiHelper.IconContinentToIconContinentDTO(service.getAllIconsWithDetails());
        return ResponseEntity.ok(icons);
    }

    @GetMapping("/get-icons-by-denomination")
    public ResponseEntity<List<IconDTO>> getIconsByDenomination(@RequestParam(name = "denomination", required = true) String denomination) {
        List<IconDTO> icons = ApiHelper.IconEntityToIconDTO(service.getByDenomination(denomination));
        return ResponseEntity.ok(icons);
    }

    @GetMapping("/get-icons-by-creation-date")
    public ResponseEntity<List<IconDTO>> getIconsByCreationDate(@RequestParam(name = "creationDate", required = true) String creationDate) throws ParseException {
        List<IconDTO> icons = ApiHelper.IconEntityToIconDTO(service.getByCreationDate(creationDate));
        return ResponseEntity.ok(icons);
    }

    @GetMapping("/get-icons-by-city-id/{id}")
    public ResponseEntity<List<IconDTO>> getIconsByCreationDate(@PathVariable Long id) throws ParseException {
        List<IconDTO> icons = ApiHelper.IconEntityToIconDTO(service.getBtCityId(id));
        return ResponseEntity.ok(icons);
    }

    @PostMapping("/create-icon")
    public ResponseBase createIcon(@RequestBody CreateOrUpdateIconDTO iconDTO) {
        return service.createIcon(iconDTO);
    }

    @PutMapping("/update-icon/{id}")
    public ResponseBase updateIcon(@PathVariable Long id, @RequestBody CreateOrUpdateIconDTO iconDTO) {
        return service.updateIcon(id, iconDTO);
    }

    @DeleteMapping("/delete-icon/{id}")
    public ResponseBase updateIcon(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ApiHelper.iconDeleted().getBody();
        }
        return ApiHelper.invalidDeleteIconRequest().getBody();
    }

}
