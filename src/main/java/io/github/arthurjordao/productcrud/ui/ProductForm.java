package io.github.arthurjordao.productcrud.ui;

import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import io.github.arthurjordao.productcrud.model.Product;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

public class ProductForm extends AbstractForm<Product> {

    private MTextField name = new MTextField("name");
    private MTextField price = new MTextField("price");
    private ProductTable productTable;

    public ProductForm(ProductTable productTable) {
        super(Product.class);
        this.productTable = productTable;
        modifyBinder();
        addButtons();
    }

    private void addButtons() {
        setSavedHandler(product -> {
            productTable.addProduct(product);
            setEntity(new Product());
        });
        setResetHandler(product -> {
            if (productTable.getProductRepository().exists(product.getId())) {
                setEntity(productTable.getProductRepository().findOne(product.getId()));
            } else {
                setEntity(new Product());
            }
        });
        setDeleteHandler(product -> {
            productTable.removeProduct(product);
            setEntity(new Product());
        });
    }

    private void modifyBinder() {
        getBinder().forField(name).withValidator(new BeanValidator(Product.class, "name"))
                .bind(Product::getName, Product::setName);
        getBinder().forField(price).withConverter(new StringToDoubleConverter("Must be a number"))
                .withValidator(new BeanValidator(Product.class, "price"))
                .bind(Product::getPrice, Product::setPrice);
        setEntity(new Product());
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(name, price, getToolbar());
    }

    @Override
    public HorizontalLayout getToolbar() {
        final HorizontalLayout toolbar = super.getToolbar();
        Button cleanButton = new Button("Clean");
        cleanButton.addClickListener(clickEvent -> setEntity(new Product()));
        toolbar.addComponent(cleanButton);
        return toolbar;
    }
}
