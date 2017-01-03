package com.coreservices.datatypes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

/**
 * Created by janet on 1/3/17.
 */
@ApiModel (value = "UserDatatype", description = "User data model")
public class UserDatatype {
    private final String lastname;
    private final String firstname;
    private final UUID uuid;
    private final String phone;
    private final long signup;

    public UserDatatype(UUID uuid, String firstname, String lastname, String phone, long signup) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.signup = signup;

    }

    @ApiModelProperty(value = "get User Sign Up Timestamp")
    public long getSignup() {
        return signup;
    }

    @ApiModelProperty(value = "get User phone")
    public String getPhone() {
        return phone;
    }

    @ApiModelProperty(value = "get User firstname")
    public String getFirstname() {
        return firstname;
    }

    @ApiModelProperty(value = "get User lastname")
    public String getLastname() {
        return lastname;
    }

    @ApiModelProperty(value = "get User unique id")
    public UUID getUuid() {
        return uuid;
    }
}
