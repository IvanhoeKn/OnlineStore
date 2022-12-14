package proj3ct.onlinestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import proj3ct.onlinestore.botapi.UpdateReceiver;
import proj3ct.onlinestore.config.BotConfig;

import java.io.Serializable;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    private final UpdateReceiver updateReceiver;

    public TelegramBot(BotConfig config, UpdateReceiver updateReceiver) {
        this.config = config;
        this.updateReceiver = updateReceiver;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        PartialBotApiMethod<? extends Serializable> responseToUser = updateReceiver.handleUpdate(update);
        /*try {
            execute((SendMessage) responseToUser);
        }
        catch (TelegramApiException e) {
            log.error(e.getMessage());
        }*/
        if (responseToUser instanceof SendDocument) {
            try {
                execute(
                        (SendDocument) responseToUser);
            }
            catch (TelegramApiException e) {
                log.error("Error occurred while sending message to user: {}", e.getMessage());
            }
        }

        if (responseToUser instanceof SendPhoto) {
            try {
                execute(
                        (SendPhoto) responseToUser);
            } catch (TelegramApiException e) {
                log.error("Error occurred while sending message to user: {}", e.getMessage());
            }
        }

        if (responseToUser instanceof BotApiMethod) {
            try {
                execute(
                        (BotApiMethod<? extends Serializable>) responseToUser);
            } catch (TelegramApiException e) {
                log.error("Error occurred while sending message to user: {}", e.getMessage());
            }
        }

    }

}
