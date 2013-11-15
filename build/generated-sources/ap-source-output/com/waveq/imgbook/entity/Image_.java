package com.waveq.imgbook.entity;

import com.waveq.imgbook.entity.Comment;
import com.waveq.imgbook.entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-11-14T22:29:34")
@StaticMetamodel(Image.class)
public class Image_ { 

    public static volatile SingularAttribute<Image, Integer> id;
    public static volatile SingularAttribute<Image, String> title;
    public static volatile SetAttribute<Image, Comment> commentSet;
    public static volatile SingularAttribute<Image, byte[]> image;
    public static volatile SingularAttribute<Image, Date> addDate;
    public static volatile SingularAttribute<Image, Integer> rating;
    public static volatile SingularAttribute<Image, Date> mainDate;
    public static volatile SingularAttribute<Image, User> user;

}