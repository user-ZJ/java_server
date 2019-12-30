package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import com.demo.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = {"/product/"})
public class ProductController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable Long id) {
        Product product = new Product();
        product.setName("空气净化器");
        product.setId(1L);
        product.setProductClass("filters");
        product.setProductId("T12345");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}