package Entity;

import Entity.Product;
import Entity.PurchasePK;
import Entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T12:36:41")
@StaticMetamodel(Purchase.class)
public class Purchase_ { 

    public static volatile SingularAttribute<Purchase, Integer> number;
    public static volatile SingularAttribute<Purchase, Product> product;
    public static volatile SingularAttribute<Purchase, PurchasePK> purchasePK;
    public static volatile SingularAttribute<Purchase, String> comment;
    public static volatile SingularAttribute<Purchase, User> user;

}