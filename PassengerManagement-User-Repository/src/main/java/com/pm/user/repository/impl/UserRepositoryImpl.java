package com.pm.user.repository.impl;

import com.pm.user.domain.exception.UserNotFoundException;
import com.pm.user.domain.exception.UserProcessingException;
import com.pm.user.domain.model.User;
import com.pm.user.domain.repository.UserRepository;
import com.pm.user.domain.vo.UserID;
import com.pm.user.repository.mapper.UserRepositoryMapper;
import com.pm.user.repository.reactive.UserRepositoryReactive;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryReactive userRepositoryReactive;

    private final UserRepositoryMapper userRepositoryMapper;

    public UserRepositoryImpl(UserRepositoryReactive userRepositoryReactive, UserRepositoryMapper userRepositoryMapper) {
        this.userRepositoryReactive = userRepositoryReactive;
        this.userRepositoryMapper = userRepositoryMapper;
    }

    public Mono<User> create(User user) {
        return null;
    }

    public Mono<User> getByUserID(UserID userID) {
        return this.userRepositoryReactive.findByUuid(userID.getValue())
                .map(this.userRepositoryMapper::map)
                .onErrorMap(UserProcessingException::new)
                .switchIfEmpty(Mono.error(new UserNotFoundException(userID)));
    }
}
