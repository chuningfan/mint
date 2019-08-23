package com.mint.service.user;


import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.database.annotation.EnableJpaOps;
import com.mint.service.user.meta.UserServiceMetadataProvider;

@MintService(metadataProvider = UserServiceMetadataProvider.class)
@EnableJpaOps(basePackages="com.mint.service.user")
public class UserStarter 
{
    public static void main( String[] args )
    {
        SpringApplication.run(UserStarter.class,args);
    }
}
