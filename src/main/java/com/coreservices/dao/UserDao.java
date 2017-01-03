package com.coreservices.dao;

import com.coreservices.datatypes.UserDatatype;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by janet on 1/3/17.
 */
public class UserDao {
    public static UserDao INSTANCE;

    public UserDao(){
        INSTANCE = this;
    }


    public UserDatatype findById(String param) {
        //Default return type
        return new UserDatatype(UUID.randomUUID(), "Jane", "Doe", "800-555-1234", new Date().getTime());
    }

    public UserDatatype[] findByParams(Map<String, String> queryString) {
        return new UserDatatype[]{findById("default")};
    }
}
