/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hbhs6
 */
@Entity
@Table(name = "purchase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchase.findAll", query = "SELECT p FROM Purchase p")
    , @NamedQuery(name = "Purchase.findByUserId", query = "SELECT p FROM Purchase p WHERE p.purchasePK.userId = :userId")
    , @NamedQuery(name = "Purchase.findByProId", query = "SELECT p FROM Purchase p WHERE p.purchasePK.proId = :proId")
    , @NamedQuery(name = "Purchase.findByDatetime", query = "SELECT p FROM Purchase p WHERE p.purchasePK.datetime = :datetime")
    , @NamedQuery(name = "Purchase.findByNumber", query = "SELECT p FROM Purchase p WHERE p.number = :number")
    , @NamedQuery(name = "Purchase.findByComment", query = "SELECT p FROM Purchase p WHERE p.comment = :comment")
    ,  @NamedQuery(name = "Purchase.findByDatetime2", query = "SELECT p FROM Purchase p ORDER BY p.purchasePK.datetime DESC")})
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PurchasePK purchasePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Number")
    private int number;
    @Size(max = 45)
    @Column(name = "Comment")
    private String comment;
    @JoinColumn(name = "ProId", referencedColumnName = "ProId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "UserId", referencedColumnName = "UserId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Purchase() {
    }


    public Purchase(PurchasePK purchasePK) {
        this.purchasePK = purchasePK;
    }

    public Purchase(PurchasePK purchasePK, int number) {
        this.purchasePK = purchasePK;
        this.number = number;
    }

    public Purchase(int userId, int proId, Date datetime) {
        this.purchasePK = new PurchasePK(userId, proId, datetime);
    }

    public PurchasePK getPurchasePK() {
        return purchasePK;
    }

    public void setPurchasePK(PurchasePK purchasePK) {
        this.purchasePK = purchasePK;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchasePK != null ? purchasePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchase)) {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.purchasePK == null && other.purchasePK != null) || (this.purchasePK != null && !this.purchasePK.equals(other.purchasePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Purchase[ purchasePK=" + purchasePK + " ]";
    }

}
