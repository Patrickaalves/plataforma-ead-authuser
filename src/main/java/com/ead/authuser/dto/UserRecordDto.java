package com.ead.authuser.dto;

public record UserRecordDto(String username,
                            String email,
                            String password,
                            String odlPassword,
                            String fullName,
                            String phoneNumber,
                            String imageUrl) {
}
