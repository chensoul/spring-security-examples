package com.chensoul.security.customuser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithUserDetails;

@WithUserDetails("user@example.com")
@Retention(RetentionPolicy.RUNTIME)
public @interface WithUser {

}
