package com.huijiewei.agile.base.user.repository;

import com.huijiewei.agile.base.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Page<User> findAllByOrderByIdDesc(Pageable pageable);
}
