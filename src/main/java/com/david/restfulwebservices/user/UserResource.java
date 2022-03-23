package com.david.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;

    //get user
    //retrieve all user
    @GetMapping("/users")
    public List<User> RetrieveAllUser(){
            return service.findAll();
    }

    //get/user/id
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        User users = service.findOne(id);
        if (users == null){
            throw new UserNotFoundException("id-"+id);
        }
        EntityModel<User> model = EntityModel.of(users);

        WebMvcLinkBuilder linkToUser=linkTo(methodOn(this.getClass()).RetrieveAllUser());
        model.add(linkToUser.withRel("all-users"));
        return model;
    }



    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User users = service.deleteById(id);
        if (users == null){
            throw new UserNotFoundException("id-"+id);
        }
    }

}
