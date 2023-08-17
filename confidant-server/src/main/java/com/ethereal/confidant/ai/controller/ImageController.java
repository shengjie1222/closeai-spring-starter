package com.ethereal.confidant.ai.controller;

import cn.gjsm.api.pojo.image.Base64Image;
import com.ethereal.confidant.ai.pojo.Image;
import com.ethereal.confidant.ai.pojo.ResponseVo;
import com.ethereal.confidant.ai.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jie Jie
 * date 2023-08-17
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @GetMapping("/say")
    public ResponseVo< List<Base64Image>> answer(String message){
        Image image = new Image();
        image.setPrompt(message);
        List<Base64Image> results = imageService.answer(image);
        return ResponseVo.ok(results);
    }
}
