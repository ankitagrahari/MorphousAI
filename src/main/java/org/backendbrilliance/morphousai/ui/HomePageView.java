package org.backendbrilliance.morphousai.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.FileUploadHandler;
import com.vaadin.flow.server.streams.TemporaryFileUploadHandler;
import com.vaadin.flow.server.streams.UploadHandler;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.hilla.parser.core.OpenAPIFileType;
import org.backendbrilliance.morphousai.service.MorphousExplainImageService;
import org.backendbrilliance.morphousai.service.MorphousTextToAudioService;
import org.backendbrilliance.morphousai.service.MorphousTextToImageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Route("")
public class HomePageView extends VerticalLayout {

    private final MorphousExplainImageService explainImageService;
    private final MorphousTextToImageService textToImageService;
    private final MorphousTextToAudioService textToAudioService;

    HomePageView(
            MorphousExplainImageService explainImageService,
            MorphousTextToImageService textToImageService,
            MorphousTextToAudioService textToAudioService){

        this.explainImageService = explainImageService;
        this.textToImageService = textToImageService;
        this.textToAudioService = textToAudioService;

        var appLogo = VaadinIcon.ADD_DOCK.create();
        appLogo.addClassNames(LumoUtility.TextColor.PRIMARY, LumoUtility.IconSize.LARGE);

        var appName = new Span("Morphous AI");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);

        var header = new Div(appLogo, appName);
        header.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Padding.MEDIUM, LumoUtility.Gap.MEDIUM, LumoUtility.AlignItems.CENTER);

        HorizontalLayout mainFeatures = new HorizontalLayout();
        Button explainImage = new Button("Explain Image");
        Button textToImage = new Button("Text to Image");
        Button textToAudio = new Button("Text to Audio");
        mainFeatures.add(
                explainImage,
                textToImage,
                textToAudio
        );

        Div divider = new Div(VaadinIcon.LINE_H.create());

        VerticalLayout explainImageLayout = new VerticalLayout();
        VerticalLayout textToImageLayout = new VerticalLayout();
        VerticalLayout textToAudioLayout = new VerticalLayout();

        explainImage.addClickListener(click -> {
            explainImage.setDisableOnClick(true);
            explainImage.focus();
            textToImageLayout.setVisible(false);
            explainImageLayout.setVisible(true);
            textToAudioLayout.setVisible(false);
            explainImageLayout.add(createExplainImage());
        });

        textToImage.addClickListener(click -> {
            textToImage.setDisableOnClick(true);
            textToImage.focus();
            textToImageLayout.setVisible(true);
            explainImageLayout.setVisible(false);
            textToAudioLayout.setVisible(false);
            textToImageLayout.add(createTextToImage());
        });

        textToAudio.addClickListener(click -> {
            textToAudio.setDisableOnClick(true);
            textToAudio.focus();
            textToImageLayout.setVisible(false);
            explainImageLayout.setVisible(false);
            textToAudioLayout.setVisible(true);
            textToAudioLayout.add(createTextToAudio());
        });

        add(
                header,
                mainFeatures,
                divider
        );
        if(explainImageLayout.isVisible()) add(explainImageLayout);
        if(textToImageLayout.isVisible()) add(textToImageLayout);
        if(textToAudioLayout.isVisible()) add(textToAudioLayout);
    }

    private Component createTextToAudio() {
        VerticalLayout ttaLayout = new VerticalLayout();

        RadioButtonGroup<String> voices = new RadioButtonGroup<>();
        voices.addThemeVariants(RadioGroupVariant.LUMO_HELPER_ABOVE_FIELD);
        voices.setLabel("Voices");
        voices.setItems("ALLOY", "ASH", "BALLAD", "CORAL", "ECHO", "FABLE", "NOVA", "ONYX", "SAGE", "SHIMMER", "VERSE");
        ttaLayout.add(voices);

        Select<Double> speed = new Select<>();
        speed.setLabel("Speed");
        speed.setItems(1d, 1.25, 1.5, 1.75, 2d);
        speed.setValue(1d);
        ttaLayout.add(speed);

        HorizontalLayout textToAudioLayout = new HorizontalLayout();
        TextArea textPrompt = new TextArea();
        textPrompt.setSizeFull();

        Button convert = new Button("Convert");
        convert.addClickListener(
                click -> textToAudioService
                        .textToSpeech(textPrompt.getValue(), voices.getValue() ,speed.getValue()));

        textToAudioLayout.add(textPrompt, convert);
        ttaLayout.add(textToAudioLayout);
        return ttaLayout;
    }

    private Component createTextToImage(){
        VerticalLayout ttiLayout = new VerticalLayout();
        TextArea textPrompt = new TextArea();
        textPrompt.setMinLength(20);
        textPrompt.setManualValidation(true);
        textPrompt.setLabel("Image generation prompt");

        Button convert = new Button("Convert");
        convert.addClickListener(click -> {
            String prompt = textPrompt.getValue();
            if(!textPrompt.isEmpty()){
                //Call AI service to convert to image, and return the image.
                org.springframework.ai.image.Image image = textToImageService.textToImage(prompt);
                Image generatedImage = new Image(DownloadHandler.forFile(new File(image.getUrl())), "generated image");
                ttiLayout.add(generatedImage);
            } else {
                textPrompt.setErrorMessage("Image generation prompt cannot be empty!!");
            }
        });

        ttiLayout.add(
                textPrompt, convert
        );
        return ttiLayout;
    }

    private Component createExplainImage() {
        VerticalLayout eiLayout = new VerticalLayout();
        Paragraph uploadProgress = new Paragraph();

        FileUploadHandler uploadFileHandler = UploadHandler.toFile(
                (metadata, file) -> {
                    System.out.println("File saved to: "+ file.getAbsoluteFile());
                    Image image = new Image(DownloadHandler.forFile(file), "Uploaded Image");
                    add(image);

                    Paragraph response = new Paragraph();
                    if(file.exists()) {
                        System.out.println("Path:"+ file.getAbsolutePath());
                        response.setText(explainImageService.explainImage(new FileSystemResource(file.getAbsolutePath())));
                        add(response);

                        System.out.println("File removed:"+file.delete());
                    }
                }, uploadMetaData -> new File(
                        "src/main/resources/images",
                        System.currentTimeMillis() + "_" + uploadMetaData.fileName()))
                .whenStart(() -> System.out.println("Upload Started"))
                .onProgress((transferredBytes, totalBytes) -> {
                    double per = ((double) transferredBytes /totalBytes) * 100;
                    System.out.println("Uploaded "+ Math.floor(per) +"% ..");
                    uploadProgress.removeAll();
                    uploadProgress.add("Uploaded "+ Math.floor(per) + "% ..");

                })
                .whenComplete(success -> {
                    if(success) {
                        System.out.println("Upload completed!!");
                        uploadProgress.removeAll();
                        uploadProgress.add("Upload Completed!!");
                    }
                    else {
                        System.out.println("Upload failed!!");
                        uploadProgress.removeAll();
                        uploadProgress.add("Upload Failed!!");
                    }
                });
        eiLayout.add(new H3("File upload to explain"));
        Upload upload = new Upload(uploadFileHandler);
        upload.setMaxFiles(1);
        upload.setMaxFileSize(1 * 1024 * 1024);     //1 MB
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        eiLayout.add(upload);
        return eiLayout;
    }
}
