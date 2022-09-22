package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.repo.MarketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.*;
import java.util.List;

@Service
public class MarketService {

    private final MarketRepo marketRepo;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public MarketService(MarketRepo marketRepo, PasswordEncoder passwordEncoder) {
        this.marketRepo = marketRepo;
        this.passwordEncoder = passwordEncoder;
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

    public void savePhoto(MultipartFile file, int id) {
        try {
            File directory = Path.of("upload/market/" + id).toFile();
            if (!directory.exists()) Files.createDirectory(directory.toPath());
            BufferedImage bufferedImageInput = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImageOutput = new BufferedImage(570, 255, bufferedImageInput.getType());
            Graphics2D g2d = bufferedImageOutput.createGraphics();
            g2d.drawImage(bufferedImageInput, 0, 0, 570, 255, null);
            g2d.dispose();
            File file1 = new File("upload/market/" + id + "/1.jpg");
            if (!file1.exists()) file1.createNewFile();
            ImageIO.write(bufferedImageOutput, "jpg", file1);
        } catch (IOException ignored) {
        }
    }

    public void saveBanner(MultipartFile file, int id) {
        try {
            File directory = Path.of("upload/market/" + id).toFile();
            if (!directory.exists()) Files.createDirectory(directory.toPath());
            BufferedImage bufferedImageInput = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImageOutput = new BufferedImage(1920, 560, bufferedImageInput.getType());
            Graphics2D g2d = bufferedImageOutput.createGraphics();
            g2d.drawImage(bufferedImageInput, 0, 0, 1920, 560, null);
            g2d.dispose();
            File file1 = new File("upload/market/" + id + "/banner.jpg");
            if (!file1.exists()) file1.createNewFile();
            ImageIO.write(bufferedImageOutput, "jpg", file1);
        } catch (IOException ignored) {
        }
    }

    @Transactional
    public void update(Market market, Market marketForUpdate) {
        marketForUpdate.setName(market.getName());
        marketForUpdate.setPassword(passwordEncoder.encode(market.getPassword()));
        marketRepo.save(marketForUpdate);
    }
}
