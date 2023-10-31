package ru.nsu.fit.directors.userservice.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.nsu.fit.directors.userservice.model.User;

public interface SecurityService {

    /**
     * Get user that logged in the system.
     *
     * @return user
     */
    User getLoggedInUser();

    /**
     * Login user with provided cridentials.
     *
     * @param username name of the user
     * @param password user password
     * @param request  servlet request
     */

    void autoLogin(String username, String password, HttpServletRequest request);

    void logout(HttpServletRequest httpServletRequest);
}
