/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hbhs6
 */
@Embeddable
public class CartPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "UserId")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProId")
    private int proId;

    public CartPK() {
    }

    public CartPK(int userId, int proId) {
        this.userId = userId;
        this.proId = proId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) proId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CartPK)) {
            return false;
        }
        CartPK other = (CartPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.proId != other.proId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CartPK[ userId=" + userId + ", proId=" + proId + " ]";
    }
    
}
