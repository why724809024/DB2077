/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hbhs6
 */
@Entity
@Table(name = "cart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c")
    , @NamedQuery(name = "Cart.findByUserId", query = "SELECT c FROM Cart c WHERE c.cartPK.userId = :userId")
    , @NamedQuery(name = "Cart.findByProId", query = "SELECT c FROM Cart c WHERE c.cartPK.proId = :proId")
    , @NamedQuery(name = "Cart.findByNumber", query = "SELECT c FROM Cart c WHERE c.number = :number")})
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CartPK cartPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Number")
    private int number;
    @JoinColumn(name = "ProId", referencedColumnName = "ProId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "UserId", referencedColumnName = "UserId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Cart() {
    }

    public Cart(CartPK cartPK) {
        this.cartPK = cartPK;
    }

    public Cart(CartPK cartPK, int number) {
        this.cartPK = cartPK;
        this.number = number;
    }

    public Cart(int userId, int proId) {
        this.cartPK = new CartPK(userId, proId);
    }

    public CartPK getCartPK() {
        return cartPK;
    }

    public void setCartPK(CartPK cartPK) {
        this.cartPK = cartPK;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //shat by wzy
    
    public float getOneSumPrice(){
        return this.getNumber()*getProduct().getPrice().floatValue();
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartPK != null ? cartPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.cartPK == null && other.cartPK != null) || (this.cartPK != null && !this.cartPK.equals(other.cartPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Cart[ cartPK=" + cartPK + " ]";
    }
    
}
