package jsf;

import Entity.Purchase;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import Session.PurchaseFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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

@Named("purchaseController")
@SessionScoped
public class PurchaseController implements Serializable {

    private Purchase current;
    private DataModel items = null;
    @EJB
    private Session.PurchaseFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    //ph
    private List<Purchase> purchaseTest;
    private boolean isExist = true;
    private boolean isPrice = false;
    private String currentTime1 = "";
    private int currentId1 = 0;
    private String currentTime2 = "";
    private int currentId2 = 0;
    private String previousTime = "";
    private int previousId = 0;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public List<Purchase> getPurchaseTest() {
        this.purchaseTest = getFacade().purchaseTest();
        return purchaseTest;
    }

    public BigDecimal getPrice(Purchase purchase) {
        this.price = (purchase.getProduct().getPrice()).multiply(new BigDecimal(purchase.getNumber()));
        return price;
    }

    public BigDecimal getTotalPrice(Purchase purchase) {
        Date purchaseId = purchase.getPurchasePK().getDatetime();
        List<Purchase> items = getFacade().findbyId(purchaseId);
        BigDecimal result = new BigDecimal(0);

        for (int i = 0; i < items.size(); i++) {
            result = result.add((items.get(i).getProduct().getPrice()).multiply(new BigDecimal(items.get(i).getNumber())));
//            System.out.println("result:" + (items.get(i).getProduct().getPrice()) + "*" + (items.get(i).getNumber()));
        }
        return result;
    }

    public boolean setIsExist(Purchase purchase) {

        String tempTime = purchase.getPurchasePK().getDatetime().toString();
        int tempId = purchase.getProduct().getProId();

        System.out.println("CurrentTime" + currentTime1 + "tempTime:" + tempTime
                + "CurrentID" + currentId1 + "tempId:" + tempId);

        if (tempTime.equals(currentTime1)) {

            if (tempId == currentId1) {
                isExist = true;
            } else {
                currentId1 = tempId;
                isExist = false;
            }
//            System.out.println("temp:" + temp);
        } else {
            currentTime1 = tempTime;
            isExist = true;
        }
        return isExist;
    }

    public boolean getIsExist() {
        return isExist;
    }

    public PurchaseController() {
    }

    public Purchase getSelected() {
        if (current == null) {
            current = new Purchase();
            current.setPurchasePK(new Entity.PurchasePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private PurchaseFacade getFacade() {
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
        current = (Purchase) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Purchase();
        current.setPurchasePK(new Entity.PurchasePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getPurchasePK().setUserId(current.getUser().getUserId());
            current.getPurchasePK().setProId(current.getProduct().getProId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PurchaseCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Purchase) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getPurchasePK().setUserId(current.getUser().getUserId());
            current.getPurchasePK().setProId(current.getProduct().getProId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PurchaseUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Purchase) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PurchaseDeleted"));
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

    public Purchase getPurchase(Entity.PurchasePK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Purchase.class)
    public static class PurchaseControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PurchaseController controller = (PurchaseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "purchaseController");
            return controller.getPurchase(getKey(value));
        }

        Entity.PurchasePK getKey(String value) {
            Entity.PurchasePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entity.PurchasePK();
            key.setUserId(Integer.parseInt(values[0]));
            key.setProId(Integer.parseInt(values[1]));
            key.setDatetime(java.sql.Date.valueOf(values[2]));
            return key;
        }

        String getStringKey(Entity.PurchasePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUserId());
            sb.append(SEPARATOR);
            sb.append(value.getProId());
            sb.append(SEPARATOR);
            sb.append(value.getDatetime());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Purchase) {
                Purchase o = (Purchase) object;
                return getStringKey(o.getPurchasePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Purchase.class.getName());
            }
        }

    }

}
