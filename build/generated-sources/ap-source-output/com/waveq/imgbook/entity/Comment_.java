package com.waveq.imgbook.entity;

import com.waveq.imgbook.entity.Image;
import com.waveq.imgbook.entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-11-21T15:11:21")
@StaticMetamodel(Comment.class)
public class Comment_ { 

    public static volatile SingularAttribute<Comment, Integer> id;
    public static volatile SingularAttribute<Comment, String> content;
    public static volatile SingularAttribute<Comment, Image> image;
    public static volatile SingularAttribute<Comment, User> user;

}