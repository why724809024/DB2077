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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hbhs6
 */
@Embeddable
public class PurchasePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "UserId")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProId")
    private int proId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    public PurchasePK() {
    }

    public PurchasePK(int userId, int proId, Date datetime) {
        this.userId = userId;
        this.proId = proId;
        this.datetime = datetime;
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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) proId;
        hash += (datetime != null ? datetime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchasePK)) {
            return false;
        }
        PurchasePK other = (PurchasePK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.proId != other.proId) {
            return false;
        }
        if ((this.datetime == null && other.datetime != null) || (this.datetime != null && !this.datetime.equals(other.datetime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.PurchasePK[ userId=" + userId + ", proId=" + proId + ", datetime=" + datetime + " ]";
    }
    
}
