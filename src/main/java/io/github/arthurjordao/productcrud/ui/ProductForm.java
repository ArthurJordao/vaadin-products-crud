package io.github.arthurjordao.productcrud.ui;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import io.github.arthurjordao.productcrud.model.Product;

public class ProductForm extends FormLayout {

    private final Binder<Product> binder = new Binder<Product>(Product.class);
    private final TextField name;
    private final TextField price;
    private Product product;

    ProductForm(Product product, ProductList productList) {
        this.product = product;
        name = new TextField("name");
        price = new TextField("price");
        binder.forField(name).withValidator(new BeanValidator(Product.class, "name"))
                .bind(Product::getName, Product::setName);
        binder.forField(price).withConverter(new StringToDoubleConverter("Must enter a number"))
                .withValidator(new BeanValidator(Product.class, "price"))
                .bind(Product::getPrice, Product::setPrice);
        binder.setBean(product);
        addComponent(name);
        addComponent(price);
        Button addButton = new Button("Save product", clickEvent -> {
            productList.addProduct(this.product);
            setProduct(new Product());
        });
        Button deleteButton = new Button("Remove product", clickEvent -> {
            productList.remove(this.product);
            setProduct(new Product());
        });
        Button resetButton = new Button("Reset", click -> setProduct(new Product()));
        addComponent(addButton);
        addComponent(deleteButton);
        addComponent(resetButton);
        addButton.setEnabled(false);
        binder.addValueChangeListener(event -> {
            boolean isValid = binder.isValid();
            addButton.setEnabled(isValid);
        });
    }

    public void setProduct(Product product) {
        this.binder.setBean(product);
        this.product = product;
    }
}
