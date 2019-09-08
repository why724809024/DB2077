package jsf;

import Entity.Cart;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;

import Session.CartFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import Entity.Purchase;

@Named("cartController")
@SessionScoped
public class CartController implements Serializable {

    private Cart current;
    private DataModel items = null;
    @PersistenceContext
    EntityManager em;
    int userId = 1 ; //test
    @EJB
    private Session.CartFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String commentList;

    public int getUserId() {
        return userId;
    }

    public String setUserId(int userId) {
        this.userId = userId;
        return "gotoCart";
    }
    
    public String getCommentList() {
        return commentList;
    }

    public void setCommentList(String commentList) {
        this.commentList = commentList;
    }

    public CartController() {
    }

    public Cart getSelected() {
        if (current == null) {
            current = new Cart();
            current.setCartPK(new Entity.CartPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private CartFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Cart) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Cart();
        current.setCartPK(new Entity.CartPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getCartPK().setUserId(current.getUser().getUserId());
            current.getCartPK().setProId(current.getProduct().getProId());
            getFacade().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CartCreated"));
            return prepareCreate();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cart) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getCartPK().setUserId(current.getUser().getUserId());
            current.getCartPK().setProId(current.getProduct().getProId());
            getFacade().edit(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CartUpdated"));
            return "View";
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cart) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CartDeleted"));
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        //if (items == null) {
            items = getPagination().createPageDataModel();
        //}
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Cart getCart(Entity.CartPK id) {
        return ejbFacade.find(id);
    }

    //begin by wzy

    public int getAllSumPrice() {
        return 0;
    }

    public String goToComment() {

        return "Checkout";
    }

    @Transactional
    public String addToPurchase() {
        Cart cart = null;
        for (Iterator item = getItems().iterator(); item.hasNext();) {
            cart = (Cart) item.next();
            Purchase pc = new Purchase(userId, cart.getProduct().getProId(), new Date());
            pc.setNumber(cart.getNumber());
//            pc.setComment(cart.getComment());
            try {
                em.persist(pc);
                em.remove(cart);
                em.flush();
            } catch (Exception e) {
            }
        }
        return "gotoList";
    }

    public String goToView() {
        current = (Cart) getItems().getRowData();
        
        return "GoToView_product";
    }

    public String getTest() {

        //SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        //Cart ct = (Cart) item[0].getValue();
        return "窝窝头,一块钱四个,嘿嘿!";

    }

//end by wzy
    
    @FacesConverter(forClass = Cart.class)
    public static class CartControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CartController controller = (CartController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cartController");
            return controller.getCart(getKey(value));
        }

        Entity.CartPK getKey(String value) {
            Entity.CartPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entity.CartPK();
            key.setUserId(Integer.parseInt(values[0]));
            key.setProId(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(Entity.CartPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUserId());
            sb.append(SEPARATOR);
            sb.append(value.getProId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cart) {
                Cart o = (Cart) object;
                return getStringKey(o.getCartPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cart.class.getName());
            }
        }

    }

}