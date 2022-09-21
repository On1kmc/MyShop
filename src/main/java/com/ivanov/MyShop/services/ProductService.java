package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Cart;
import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.models.Product;
import com.ivanov.MyShop.repo.CartRepo;
import com.ivanov.MyShop.repo.PersonRepo;
import com.ivanov.MyShop.repo.ProductRepo;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    private final CartRepo cartRepo;



    public ProductService(ProductRepo productRepo, CartRepo cartRepo) {
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Product findById(int id) {
        return productRepo.findById(id).get();
    }

    public List<Product> findAllByMarket_id(int id) {
        return productRepo.findAllByMarket_id(id);
    }



    @Transactional
    public void save(Product product, MultipartFile[] files) throws IOException {
        if (files.length > 0) {

            Files.createDirectory(Path.of("upload/product/" + product.getArticle()));
            int count = 0;
            for (MultipartFile file : files) {
                BufferedImage bufferedImageInput = ImageIO.read(file.getInputStream());
                BufferedImage bufferedImageOutput = new BufferedImage(270, 210, bufferedImageInput.getType());
                Graphics2D g2d = bufferedImageOutput.createGraphics();
                g2d.drawImage(bufferedImageInput, 0, 0, 270, 210, null);
                g2d.dispose();
                count++;
                String filename = count + ".jpg";


                File file1 = new File("upload/product/" + product.getArticle() + "/" + filename);
                file1.createNewFile();
                ImageIO.write(bufferedImageOutput, "jpg", file1);
            }
        }
        productRepo.save(product);
    }

    public List<Product> getRandomList(int count) {
        List<Product> list = productRepo.findAll();
        List<Product> toViewList = new ArrayList<>();
        while(toViewList.size() != count) {
            int index = (int) (Math.random() * list.size());
            toViewList.add(list.get(index));
        }
        return toViewList;
    }

    public List<Product> getRandomListFromMarket(Market market, int count) {
        List<Product> list = productRepo.findAllByMarket_id(market.getId());
        List<Product> toViewList = new ArrayList<>();
        while(toViewList.size() != count) {
            int index = (int) (Math.random() * list.size());
            toViewList.add(list.get(index));
        }
        return toViewList;
    }


    @Transactional
    public void addToCart(Person person, int productId) {
        Cart cart = person.getCart();
        Product product = productRepo.findById(productId).get();
        Hibernate.initialize(cart.getProducts());
        List<Product> list = cart.getProducts();
        list.add(product);
        cart.setProducts(list);
        person.setCart(cart);
        cartRepo.save(cart);
    }


}
