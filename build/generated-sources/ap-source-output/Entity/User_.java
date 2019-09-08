package Entity;

import Entity.Cart;
import Entity.Purchase;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T12:36:41")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, Cart> cartCollection;
    public static volatile SingularAttribute<User, String> address;
    public static volatile SingularAttribute<User, String> passport;
    public static volatile SingularAttribute<User, String> headImg;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> tel;
    public static volatile CollectionAttribute<User, Purchase> purchaseCollection;
    public static volatile SingularAttribute<User, Integer> userId;
    public static volatile SingularAttribute<User, String> bkAcc;

}