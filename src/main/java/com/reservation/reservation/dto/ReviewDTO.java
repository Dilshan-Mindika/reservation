package com.reservation.reservation.dto;

public class ReviewDTO {

    private Long id;
    private String comment;
    private int rating;
    private UserDTO user;
    private LabDTO lab;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LabDTO getLab() {
        return lab;
    }

    public void setLab(LabDTO lab) {
        this.lab = lab;
    }
}
