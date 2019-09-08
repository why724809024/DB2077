/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Purchase;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hbhs6
 */
@Stateless
public class PurchaseFacade extends AbstractFacade<Purchase> {

    @PersistenceContext(unitName = "DB2PU")
    private EntityManager em;

    //ph
        public List<Purchase> purchaseTest() {
        List<Purchase> result = em.createNamedQuery("Purchase.findByDatetime2").getResultList();
        return result;
    }

    public List<Purchase> findbyId(Date id) {
        List<Purchase> result = em.createNamedQuery("Purchase.findByDatetime").setParameter("datetime", id).getResultList();
        return result;
    }
    //ph/
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseFacade() {
        super(Purchase.class);
    }
    
}
