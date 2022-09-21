package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.models.Product;
import com.ivanov.MyShop.repo.MarketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

@Service
public class MarketService {

    private final MarketRepo marketRepo;


    @Autowired
    public MarketService(MarketRepo marketRepo) {
        this.marketRepo = marketRepo;
    }

    public List<Market> findAll() {
        return marketRepo.findAll();
    }

    public Market findById(int id) {
        return marketRepo.findById(id).get();
    }

    public List<Market> getRandomList(int count) {
        List<Market> list = marketRepo.findAll();
        List<Market> toViewList = new ArrayList<>();
        while(toViewList.size() != count) {
            int index = (int) (Math.random() * list.size());
            toViewList.add(list.get(index));
        }
        return toViewList;
    }

    public void saveFile(MultipartFile file, Market market) {
        try {
            Files.createDirectory(Path.of("upload/market/" + market.getId()));
            BufferedImage bufferedImageInput = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImageOutput = new BufferedImage(570, 255, bufferedImageInput.getType());
            Graphics2D g2d = bufferedImageOutput.createGraphics();
            g2d.drawImage(bufferedImageInput, 0, 0, 570, 255, null);
            g2d.dispose();
            File file1 = new File("upload/market/" + market.getId() + "/1.jpg");
            file1.createNewFile();
            ImageIO.write(bufferedImageOutput, "jpg", file1);
        } catch (IOException ignored) {
        }
    }
}
