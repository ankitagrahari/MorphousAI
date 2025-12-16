package org.backendbrilliance.morphousai.service;

import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class MorphousTextToImageService {

    private final OpenAiImageModel imageModel;

    public MorphousTextToImageService(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public Image textToImage(String textPrompt){
        ImageOptions options = OpenAiImageOptions.builder()
                .model("dall-e-3")
                .width(1024)
                .height(1024)
                .style("vivid")
                .build();
        ImagePrompt prompt = new ImagePrompt(textPrompt, options);
        ImageResponse response = imageModel.call(prompt);
        return response.getResult().getOutput();
    }
}
