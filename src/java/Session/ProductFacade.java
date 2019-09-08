/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Product;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hbhs6
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "DB2PU")
    private EntityManager em;

    public int findMaxProId() {
        Product result = (Product) em.createNamedQuery("Product.orderbyProId").getResultList().get(0);
        return result.getProId()+1;
    }

    public List<Product> test() {
        List<Product> test = em.createNamedQuery("Product.findAll").getResultList();
        return test;
    }

    public void insert(String name, String category, BigDecimal price, String text, String img) {

        Query q = em.createNativeQuery("Insert into Product (name,category,price,text,img) values('" + name + "','" + category + "'," + price + ",'" + text + "','" + img + "')");
        q.executeUpdate();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

}
