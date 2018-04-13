package pt.shared.ServerAndClientGeneral.command;

import pt.shared.ServerAndClientGeneral.response.Response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

public class DownloadQuizQuestionsCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private static String Id = "DownloadQuizQuestionsCommand";

    @Override
    public String getId() {
        return Id;
    }

    public DownloadQuizQuestionsCommand(TreeMap<String, Object> argsMap, Double sessionId, PrivateKey privKey,
                                        PublicKey pubK, SecureRandom random) {

        super(argsMap, sessionId, privKey, pubK, random);
    }

    public String getUsername() {
        return ((TreeMap<String, String>) getArgument("return")).get("username");
    }

    public String getBusTicket() {
        return ((TreeMap<String, String>) getArgument("return")).get("busTicketCode");
    }

    public String getSsid() {
        return ((TreeMap<String, String>) getArgument("return")).get("ssid");
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

}