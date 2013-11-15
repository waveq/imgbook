package com.waveq.imgbook.entity;

import com.waveq.imgbook.entity.Comment;
import com.waveq.imgbook.entity.Image;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-11-14T22:29:34")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SetAttribute<User, Image> imageSet;
    public static volatile SetAttribute<User, Comment> commentSet;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, byte[]> avatar;
    public static volatile SingularAttribute<User, String> password;

}