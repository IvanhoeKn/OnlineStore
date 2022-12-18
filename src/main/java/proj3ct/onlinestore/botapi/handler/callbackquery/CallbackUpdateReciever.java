package proj3ct.onlinestore.botapi.handler.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Component
public class CallbackUpdateReciever {

    private ReplyMessageService messageService;

    private LocaleMessageService localeMessageService;

    private List<CallbackQueryHandler> callbackQueryHandlers;

    public CallbackUpdateReciever(ReplyMessageService messageService,
                                  LocaleMessageService localeMessageService,
                                  List<CallbackQueryHandler> callbackQueryHandlers) {
        this.messageService = messageService;
        this.localeMessageService = localeMessageService;
        this.callbackQueryHandlers = callbackQueryHandlers;
    }

    public PartialBotApiMethod<? extends Serializable> processCallbackQuery(CallbackQuery usersQuery) {
        String prefixId = usersQuery.getData().split("_")[0];
        var ref = new Object() {
            CallbackQueryType usersQueryType = CallbackQueryType.valueOf(usersQuery.getData().replaceFirst("^[0-9]+_", ""));
        };
        if (isProcessOrder(ref.usersQueryType))
            ref.usersQueryType = CallbackQueryType.PROCESS_ORDER;
        Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream()
                .filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(ref.usersQueryType))
                .findFirst();
        return queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery))
                .orElse((PartialBotApiMethod) messageService.sendAnswerCallbackQuery(localeMessageService.getMessage("reply.query.failed"), false, usersQuery));
    }

    private boolean isProcessOrder(CallbackQueryType callbackQueryType) {
        switch (callbackQueryType) {
            case INC:
            case DEC:
            case CONFIRM:
            case CANCEL:
                return true;
            default:
                return false;
        }
    }
}
