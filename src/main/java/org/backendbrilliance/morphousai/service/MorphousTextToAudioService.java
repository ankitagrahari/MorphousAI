package org.backendbrilliance.morphousai.service;

import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.stereotype.Service;

@Service
public class MorphousTextToAudioService {

    private final OpenAiAudioSpeechModel speechModel;


    public MorphousTextToAudioService(OpenAiAudioSpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    public byte[] textToSpeech(String text, String voice, double speed){
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .model("gpt-4o-mini-tts")
                .voice(detectVoice(voice))
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .speed(speed)
                .build();
        TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(text, speechOptions);
        TextToSpeechResponse response = speechModel.call(speechPrompt);
        return response.getResult().getOutput();
    }

    private OpenAiAudioApi.SpeechRequest.Voice detectVoice(String voice) {
        return switch (voice) {
            case "alloy" -> OpenAiAudioApi.SpeechRequest.Voice.ALLOY;
            case "ash" -> OpenAiAudioApi.SpeechRequest.Voice.ASH;
            case "ballad" -> OpenAiAudioApi.SpeechRequest.Voice.BALLAD;
            case "coral" -> OpenAiAudioApi.SpeechRequest.Voice.CORAL;
            case "echo" -> OpenAiAudioApi.SpeechRequest.Voice.ECHO;
            case "fable" -> OpenAiAudioApi.SpeechRequest.Voice.FABLE;
            case "nova" -> OpenAiAudioApi.SpeechRequest.Voice.NOVA;
            case "onyx" -> OpenAiAudioApi.SpeechRequest.Voice.ONYX;
            case "sage" -> OpenAiAudioApi.SpeechRequest.Voice.SAGE;
            case "shimmer" -> OpenAiAudioApi.SpeechRequest.Voice.SHIMMER;
            case "verse" -> OpenAiAudioApi.SpeechRequest.Voice.VERSE;
            default -> OpenAiAudioApi.SpeechRequest.Voice.ECHO;
        };
    }
}
