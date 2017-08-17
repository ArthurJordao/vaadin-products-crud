package io.github.arthurjordao.productcrud.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import io.github.arthurjordao.productcrud.model.Product;
import io.github.arthurjordao.productcrud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;


@SpringUI
@Theme("valo")
public class ProductUI extends UI implements ProductTable {

    @Autowired
    private ProductRepository productRepository;

    private VerticalLayout root = new MVerticalLayout();
    private HorizontalLayout grid = new MHorizontalLayout();
    private VerticalLayout leftColumn = new MVerticalLayout();

    private Grid<Product> productGrid = new Grid<>(Product.class);
    private ProductForm productForm = new ProductForm(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addProductList();
    }

    private void setupLayout() {
        addHeader();
        addGrid();
        setContent(root);
    }

    private void addGrid() {
        root.addComponent(grid);
        grid.addComponent(leftColumn);
        grid.addComponent(productForm);
    }

    private void addHeader() {
        Header header = new Header("Products");
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        root.addComponent(header);
    }

    private void addProductList() {
        updateProducts();
        leftColumn.addComponent(productGrid);
        productGrid.addItemClickListener(itemClick -> productForm.setEntity(itemClick.getItem()));
        productGrid.setColumnOrder("id", "name", "price");
    }

    @Override
    public void updateProducts() {
        productGrid.setItems(productRepository.findAll());
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
        updateProducts();
    }

    @Override
    public void removeProduct(Product product) {
        if (productRepository.exists(product.getId()))
            productRepository.delete(product);
        updateProducts();
    }

    @Override
    public ProductRepository getProductRepository() {
        return productRepository;
    }
}
