package jsf;

import Entity.Cart;
import Entity.CartPK;
import Entity.Product;
import Entity.Purchase;
import Session.CartFacade;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import Session.ProductFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
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

@Named("productController")
@SessionScoped
public class ProductController implements Serializable {

    private Product current;
    private DataModel items = null;
    @EJB
    private Session.ProductFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Product> test;
    private Integer proId;
    private String name;
    private String category;
    private BigDecimal price;
    private String text;
    private String img;

    //begin wzy
    @PersistenceContext
    private EntityManager em;
    int userId = 1;//temporary
    int num = 1;//temporary
    @EJB
    CartFacade cartFacade;

    public void listener(int proid) {
        current = em.find(Product.class, proid);
    }

    public Purchase getPurchase() {
        int ProId = current.getProId();
        try {
            Purchase pc = (Purchase) em.createNamedQuery("Purchase.findByProId").setParameter("proId", ProId).getResultList().get(0);
            return pc;
        } catch (Exception e) {
            //doing something
        }
        return null;
    }

    @Transactional
    public void addToCart() {
        int ProId = current.getProId();

        try {

            Cart c = (Cart) em.find(Cart.class, new CartPK(userId, ProId));

            if (c == null) {
                c = new Cart(new CartPK(userId, current.getProId()), num);
                em.persist(c);

                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductCreated"));
            } else {
                em.merge(c);
                c.setNumber(c.getNumber() + num);
            }
            em.flush();
            em.refresh(c);

        } catch (IllegalArgumentException e) {
            num = 0;

            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }

    }

    public void plusNum() {
        num = num + 1;
    }

    public void minusNum() {
        num = num - 1;
    }
//end by wzy

    public String goToView(Product item) {
        current = item;
        return "GoToView_product";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;

    }

    public ProductController() {
    }

    public List<Product> setTest() {
        test = getFacade().test();
        return test;
    }

    public List<Product> getTest() {
        return test;
    }

    public Product getSelected() {
        if (current == null) {
            current = new Product();
            //selectedItemIndex = -1;
        }
        return current;
    }

    private ProductFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(100) {

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
        current = (Product) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "GoToView_product";
    }

    public String prepareCreate() {
        current = new Product();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setImg(current.getImg().replaceAll("\\\\", "/").substring(img.indexOf("/resources")));
            current.setProId(getFacade().findMaxProId());
            getFacade().create(current);
            System.out.println("CREATED!!!!");
//          getFacade().insert(name, category, price, text, img);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductCreated"));
            return "/manager";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return "/manager";
        }
    }

    public String prepareEdit() {
        current = (Product) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String updateTrigger(Product item) {
        current = item;
        return "/edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductUpdated"));
            return "/manager";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Product item) {
        current = item;
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        getFacade().remove(item);
//        recreatePagination();
//        recreateModel();
        return "/index";
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
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

    public Product getProduct(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Product.class)
    public static class ProductControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductController controller = (ProductController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productController");
            return controller.getProduct(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Product) {
                Product o = (Product) object;
                return getStringKey(o.getProId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Product.class.getName());
            }
        }

    }

}
