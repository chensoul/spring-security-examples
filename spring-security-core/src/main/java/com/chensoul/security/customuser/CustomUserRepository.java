package com.chensoul.security.customuser;

public interface CustomUserRepository {

	CustomUser findCustomUserByEmail(String email);

}
