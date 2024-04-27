package com.catservice.webmicroservice.controllers;

import com.catservice.catmicroservice.models.Cat;
import com.catservice.catmicroservice.models.dto.CatDto;
import com.catservice.catmicroservice.models.dto.CatPageResponse;
import com.catservice.catmicroservice.services.interfaces.CatService;
import com.catservice.ownermicroservice.models.Owner;
import com.catservice.ownermicroservice.services.interfaces.OwnerService;
import com.catservice.ownermicroservice.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cat/")
public class CatController {

    private final CatService catService;
    private final UserService userService;
    private final OwnerService ownerService;

    @Autowired
    public CatController(CatService catService, UserService userService, OwnerService ownerService) {
        this.catService = catService;
        this.userService = userService;
        this.ownerService = ownerService;
    }

    @PutMapping("{username}/{catId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> updateCat(
            @RequestBody CatDto catDto, @PathVariable("username") String username,
            @PathVariable("catId") UUID catId
    ){

        boolean isAdmin = userService.getUserEntityByUsername(username).getRoles().stream().allMatch(role -> role.getName().equals("ADMIN"));
        if (!userService.isCurrentUserEntityEquals(username) && !isAdmin)
            return new ResponseEntity<>("Cat data can not be modified", HttpStatus.BAD_REQUEST);

        catService.addOrUpdateCatWithDtoById(catDto, catId);
        return new ResponseEntity<>("Cat was updated successfully", HttpStatus.OK);
    }

    @PostMapping("{username}/{catId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> addCat(
            @RequestBody CatDto catDto, @PathVariable("username") String username,
            @PathVariable("catId") UUID catId
    ){

        boolean isAdmin = userService.getUserEntityByUsername(username).getRoles().stream().allMatch(role -> role.getName().equals("ADMIN"));
        if (!userService.isCurrentUserEntityEquals(username) && !isAdmin)
            return new ResponseEntity<>("Cat data can not be modified", HttpStatus.BAD_REQUEST);

        catService.addOrUpdateCatWithDtoById(catDto, catId);
        return new ResponseEntity<>("Cat was updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("{username}/{catId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> deleteOwner(
            @PathVariable("username") String username, @PathVariable("catId") UUID catId
    ){

        boolean isAdmin = userService.getUserEntityByUsername(username).getRoles().stream().allMatch(role -> role.getName().equals("ADMIN"));
        if (!userService.isCurrentUserEntityEquals(username) && !isAdmin)
            return new ResponseEntity<>("Cat data can not be modified", HttpStatus.BAD_REQUEST);

        Cat cat = catService.getCatById(catId);
        catService.deleteCat(cat);
        return new ResponseEntity<>("Cat was updated successfully", HttpStatus.OK);
    }

    @GetMapping("details/{username}/{catId}")
    public ResponseEntity<List<Cat>> getCatDetails(
            @PathVariable("username") String username, @PathVariable("catId") UUID catId
    ){
        boolean isAdmin = userService.getUserEntityByUsername(username).getRoles().stream().allMatch(role -> role.getName().equals("ADMIN"));
        if (!userService.isCurrentUserEntityEquals(username) && !isAdmin)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        Owner owner = ownerService.getOwnerByUsername(username);
        return new ResponseEntity<>(catService.getCatsByOwnerId(owner.getId()), HttpStatus.OK);
    }

    @GetMapping("all/{username}")
    public ResponseEntity<CatPageResponse> getAllUsersDetails(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable("username") String username
    ) {

        boolean isAdmin = userService.getUserEntityByUsername(username).getRoles().stream().allMatch(role -> role.getName().equals("ADMIN"));
        if (!userService.isCurrentUserEntityEquals(username) && !isAdmin)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        Owner owner = ownerService.getOwnerByUsername(username);
        return new ResponseEntity<>(catService.getAllCatDtoWithPagination(pageNumber, pageSize, owner.getId()), HttpStatus.OK);
    }
}
