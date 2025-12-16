package org.backendbrilliance.morphousai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class MorphousExplainImageService {

    private final ChatClient chatClient;

    public MorphousExplainImageService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String explainImage(Resource image){
        return chatClient.prompt()
                .user(u -> {
                    u.text("Describe what you can see in this image? If you find any code explain the code snippet.");
                    try {
                        u.media(MimeTypeUtils.IMAGE_JPEG, image.getURL());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .call()
                .content();
    }
}
