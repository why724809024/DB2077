package Entity;

import Entity.Cart;
import Entity.Purchase;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T12:36:41")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile CollectionAttribute<Product, Cart> cartCollection;
    public static volatile SingularAttribute<Product, String> img;
    public static volatile SingularAttribute<Product, BigDecimal> price;
    public static volatile SingularAttribute<Product, Integer> proId;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, String> text;
    public static volatile SingularAttribute<Product, String> category;
    public static volatile CollectionAttribute<Product, Purchase> purchaseCollection;

}