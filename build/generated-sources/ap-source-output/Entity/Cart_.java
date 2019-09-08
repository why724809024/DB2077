package Entity;

import Entity.CartPK;
import Entity.Product;
import Entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T12:36:41")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile SingularAttribute<Cart, Integer> number;
    public static volatile SingularAttribute<Cart, Product> product;
    public static volatile SingularAttribute<Cart, CartPK> cartPK;
    public static volatile SingularAttribute<Cart, User> user;

}