package ru.package01.core.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.package01.core.model.User;
import ru.package01.core.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DbServiceUserImpl implements DbServiceUser {

    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserRepository userRepository;

    public DbServiceUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long saveUser(String userString) {
        try {
            Gson gson = new Gson();
            User user = gson.fromJson(userString, User.class);
            if ((userRepository.findByLogin(user.getName()).isEmpty())
                    && !user.getName().equals("")
                    && !user.getPassword().equals("")
            ) {
                userRepository.save(user);
                logger.info("created user:_____");
            }

            long userId = user.getId();
            logger.info("created user: {}", userId);
            return userId;
        } catch (Exception e) {
            System.out.println("DID NOT CREATE");
            throw new DbServiceException(e);
        }
    }

    @Override
    public long updateUser(String userString) {
        try {
            Gson gson = new Gson();
            User user = gson.fromJson(userString, User.class);
            long userId = 0;
            if (userRepository.findByLogin(user.getName()).isEmpty()) {
                logger.info("--There is NO user with this username--");

            } else {
                userId = userRepository.findByLogin(user.getName()).get(0).getId();
                user.setId(userId);
                userRepository.save(user);
                logger.info("user with id= {} has been updated", userId);
            }
            return userId;
        } catch (Exception e) {
            System.out.println("DID NOT FIND");
            throw new DbServiceException(e);
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            logger.info("user: {}", userOptional.orElse(null));
            return userOptional;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public long deleteUser(String userString) {
        try {
            Gson gson = new Gson();
            User user = gson.fromJson(userString, User.class);
            long userId = 0;
            if (userRepository.findByLogin(user.getName()).isEmpty()) {
                logger.info("--There is NO user with this username for DELETING--");

            } else {
                userId = userRepository.findByLogin(user.getName()).get(0).getId();
                userRepository.deleteById(userId);
                logger.info("user with id= {} has been deleted", userId);
            }
            userId = user.getId();
            logger.info("deleted user: {}", userId);
            return userId;
        } catch (Exception e) {
            System.out.println("DID NOT DELETE");
            throw new DbServiceException(e);
        }
    }
}
