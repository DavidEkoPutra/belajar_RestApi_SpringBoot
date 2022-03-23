package com.david.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    @Autowired
    private UserDaoService service;

    @Autowired
    private static UserRepository userRepository;


    //get user
    //retrieve all user
    @GetMapping("/jpa/users")
    public List<User> RetrieveAllUser(){
            return userRepository.findAll();
    }

//    public static void main(String[] args) {
//        List<User> user = userRepository.findAll();
//
//        user.forEach(System.out::println);
//
//    }

    //get/user/id
    @GetMapping("/jpa/users/{id}")
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



    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/jpa/users/add")
    public ResponseEntity<Object> addUsertoDB(@Valid @RequestBody User user){
        userRepository.save(user);
        return ResponseEntity.ok("test");
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        User users = service.deleteById(id);
        if (users == null){
            throw new UserNotFoundException("id-"+id);
        }
    }


}
