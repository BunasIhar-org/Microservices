package com.it.mentor.org.springbootsecuritydemo.repositories;

import com.it.mentor.org.springbootsecuritydemo.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import java.util.*;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByUsername(String username);
}