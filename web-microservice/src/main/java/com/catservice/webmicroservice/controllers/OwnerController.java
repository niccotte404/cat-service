package com.catservice.webmicroservice.controllers;

import com.catservice.ownermicroservice.models.Owner;
import com.catservice.ownermicroservice.models.dto.OwnerDto;
import com.catservice.ownermicroservice.models.dto.OwnerPageResponse;
import com.catservice.ownermicroservice.services.interfaces.OwnerService;
import com.catservice.ownermicroservice.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner/")
public class OwnerController {
    private final OwnerService ownerService;
    private final UserService userService;
    @Autowired
    public OwnerController(OwnerService ownerService, UserService userService) {
        this.ownerService = ownerService;
        this.userService = userService;
    }

    @PutMapping("{username}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> updateOwner(
            @RequestBody OwnerDto ownerDto, @PathVariable("username") String username){

        boolean isAdmin = userService.getUserEntityByUsername(username).getRoles().stream().allMatch(role -> role.getName().equals("ADMIN"));
        if (!userService.isCurrentUserEntityEquals(username) && !isAdmin)
            return new ResponseEntity<>("Owner data can not be modified", HttpStatus.BAD_REQUEST);

        ownerService.addOrUpdateOwnerWithDtoByUsername(ownerDto, username);
        return new ResponseEntity<>("Owner was updated successfully", HttpStatus.OK);
    }

    @PostMapping("{username}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> addOwner(
            @RequestBody OwnerDto ownerDto, @PathVariable("username") String username){
        ownerService.addOrUpdateOwnerWithDtoByUsername(ownerDto, username);

        boolean isAdmin = userService.getUserEntityByUsername(username).getRoles().stream().allMatch(role -> role.getName().equals("ADMIN"));
        if (!userService.isCurrentUserEntityEquals(username) && !isAdmin)
            return new ResponseEntity<>("Owner data can not be modified", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Owner was added successfully", HttpStatus.OK);
    }

    @DeleteMapping("{username}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> deleteOwner(@PathVariable("username") String username){

        boolean isAdmin = userService.getUserEntityByUsername(username).getRoles().stream().allMatch(role -> role.getName().equals("ADMIN"));
        if (!userService.isCurrentUserEntityEquals(username) && !isAdmin)
            return new ResponseEntity<>("Owner data can not be modified", HttpStatus.BAD_REQUEST);

        Owner owner = ownerService.getOwnerByUsername(username);
        ownerService.deleteOwner(owner);
        return new ResponseEntity<>("Owner was deleted successfully", HttpStatus.OK);
    }

    @GetMapping("details/{username}")
    public ResponseEntity<OwnerDto> getOwnerDetails(@PathVariable("username") String username){
        return new ResponseEntity<>(ownerService.getOwnerDtoByUsername(username), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<OwnerPageResponse> getAllUsersDetails(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {

        return new ResponseEntity<>(ownerService.getAllOwnerDtoWithPagination(pageNumber, pageSize), HttpStatus.OK);
    }
}
