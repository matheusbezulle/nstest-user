package br.com.nstest.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nstest.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
