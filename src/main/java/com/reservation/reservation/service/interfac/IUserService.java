package com.reservation.reservation.service.interfac;

import com.reservation.reservation.dto.LoginRequest;
import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.User;

public interface IUserService {

        Response register(User user);

        Response login(LoginRequest loginRequest);

        Response getAllUsers();

        Response getUserReservingHistory(String userId);

        Response deleteUser(String userId);

        Response getUserById(String userId);

        Response getMyInfo(String email);

    }

