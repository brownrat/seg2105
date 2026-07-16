package com.example.lab4;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void defaultConstructor_createsProduct() {
        Product product = new Product();

        assertNotNull(product);
    }

    @Test
    public void constructorWithId_setsAllFields() {
        Product product = new Product("1", "Milk", 2.99);

        assertEquals("1", product.getId());
        assertEquals("Milk", product.getProductName());
        assertEquals(2.99, product.getPrice(), 0.001);
    }

    @Test
    public void constructorWithoutId_setsNameAndPrice() {
        Product product = new Product("Bread", 1.99);

        assertNull(product.getId());
        assertEquals("Bread", product.getProductName());
        assertEquals(1.99, product.getPrice(), 0.001);
    }

    @Test
    public void setId_updatesId() {
        Product product = new Product();

        product.setId("ABC123");

        assertEquals("ABC123", product.getId());
    }

    @Test
    public void setProductName_updatesName() {
        Product product = new Product();

        product.setProductName("Coffee");

        assertEquals("Coffee", product.getProductName());
    }

    @Test
    public void setPrice_updatesPrice() {
        Product product = new Product();

        product.setPrice(4.75);

        assertEquals(4.75, product.getPrice(), 0.001);
    }
}