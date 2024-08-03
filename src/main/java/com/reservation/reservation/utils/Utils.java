package com.reservation.reservation.utils;

import com.reservation.reservation.dto.ReservingDTO;
import com.reservation.reservation.dto.LabDTO;
import com.reservation.reservation.dto.ReviewDTO;
import com.reservation.reservation.dto.UserDTO;
import com.reservation.reservation.entity.Reserving;
import com.reservation.reservation.entity.Lab;
import com.reservation.reservation.entity.Review;
import com.reservation.reservation.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();


    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }


    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static LabDTO mapLabEntityToLabDTO(Lab lab) {
        LabDTO labDTO = new LabDTO();

        labDTO.setId(lab.getId());
        labDTO.setLabType(lab.getLabType());
        labDTO.setLabCapacity(lab.getLabCapacity());
        labDTO.setLabPhotoUrl(lab.getLabPhotoUrl());
        labDTO.setLabDescription(lab.getLabDescription());
        return labDTO;
    }

    public static ReservingDTO mapReservingEntityToReservingDTO(Reserving reserving) {
        ReservingDTO reservingDTO = new ReservingDTO();
        // Map simple fields
        reservingDTO.setId(reserving.getId());
        reservingDTO.setCheckInDate(reserving.getCheckInDate());
        reservingDTO.setCheckInTime(reserving.getCheckInTime());
        reservingDTO.setTotalNumOfStudents(reserving.getTotalNumOfStudents());
        reservingDTO.setReservingConfirmationCode(reserving.getReservingConfirmationCode());
        return reservingDTO;
    }

    public static LabDTO mapLabEntityToLabDTOPlusReservings(Lab lab) {
        LabDTO labDTO = new LabDTO();

        labDTO.setId(lab.getId());
        labDTO.setLabType(lab.getLabType());
        labDTO.setLabCapacity(lab.getLabCapacity());
        labDTO.setLabPhotoUrl(lab.getLabPhotoUrl());
        labDTO.setLabDescription(lab.getLabDescription());

        if (lab.getReservings() != null) {
            labDTO.setReservings(lab.getReservings().stream().map(Utils::mapReservingEntityToReservingDTO).collect(Collectors.toList()));
        }
        return labDTO;
    }

    public static ReservingDTO mapReservingEntityToReservingDTOPlusReservedLabs(Reserving reserving, boolean mapUser) {

        ReservingDTO reservingDTO = new ReservingDTO();
        // Map simple fields
        reservingDTO.setId(reserving.getId());
        reservingDTO.setCheckInDate(reserving.getCheckInDate());
        reservingDTO.setCheckInTime(reserving.getCheckInTime());
        reservingDTO.setTotalNumOfStudents(reserving.getTotalNumOfStudents());
        reservingDTO.setReservingConfirmationCode(reserving.getReservingConfirmationCode());
        if (mapUser) {
            reservingDTO.setUser(Utils.mapUserEntityToUserDTO(reserving.getUser()));
        }
        if (reserving.getLab() != null) {
            LabDTO labDTO = new LabDTO();

            labDTO.setId(reserving.getLab().getId());
            labDTO.setLabType(reserving.getLab().getLabType());
            labDTO.setLabCapacity(reserving.getLab().getLabCapacity());
            labDTO.setLabPhotoUrl(reserving.getLab().getLabPhotoUrl());
            labDTO.setLabDescription(reserving.getLab().getLabDescription());
            reservingDTO.setLab(labDTO);
        }
        return reservingDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserReservingsAndLab(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        if (!user.getReservings().isEmpty()) {
            userDTO.setReservings(user.getReservings().stream().map(reserving -> mapReservingEntityToReservingDTOPlusReservedLabs(reserving, false)).collect(Collectors.toList()));
        }
        return userDTO;
    }

    public static ReviewDTO mapReviewEntityToReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setUser(mapUserEntityToUserDTO(review.getUser()));
        reviewDTO.setLab(mapLabEntityToLabDTO(review.getLab()));
        return reviewDTO;
    }

    public static User mapUserDTOToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole());
        return user;
    }

    public static Lab mapLabDTOToLabEntity(LabDTO labDTO) {
        Lab lab = new Lab();
        lab.setId(labDTO.getId());
        lab.setLabType(labDTO.getLabType());
        lab.setLabCapacity(labDTO.getLabCapacity());
        lab.setLabPhotoUrl(labDTO.getLabPhotoUrl());
        lab.setLabDescription(labDTO.getLabDescription());
        return lab;
    }

    public static Review mapReviewDTOToReviewEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setUser(mapUserDTOToUserEntity(reviewDTO.getUser()));
        review.setLab(mapLabDTOToLabEntity(reviewDTO.getLab()));
        return review;
    }


    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<LabDTO> mapLabListEntityToLabListDTO(List<Lab> labList) {
        return labList.stream().map(Utils::mapLabEntityToLabDTO).collect(Collectors.toList());
    }

    public static List<ReservingDTO> mapReservingListEntityToReservingListDTO(List<Reserving> reservingList) {
        return reservingList.stream().map(Utils::mapReservingEntityToReservingDTO).collect(Collectors.toList());
    }

    public static List<ReviewDTO> mapReviewListEntityToReviewListDTO(List<Review> reviewList) {
        return reviewList.stream()
                .map(Utils::mapReviewEntityToReviewDTO)
                .collect(Collectors.toList());
    }

}


