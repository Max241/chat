package com.packagename.vaddin;

import com.packagename.vaddin.xmlrpc.RpcClient;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.net.MalformedURLException;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {
    private Message message;
    private SendMessageService sendMessageService;
    private RpcClient rpcClient;
    private ServerProperties serverProperties;
    private String username;

    public MainView(Message message, SendMessageService sendMessageService, RpcClient rpcClient, ServerProperties serverProperties) {
        this.sendMessageService = sendMessageService;
        this.rpcClient = rpcClient;
        this.serverProperties = serverProperties;
        this.message = message;


        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");

        H1 header = new H1("Vaadin Chat XMLRPC/REST");
        header.getElement().getThemeList().add("dark");

        add(header);
        askUsername();

    }

    private void askUsername() {
        HorizontalLayout layout = new HorizontalLayout();
        TextField usernameField = new TextField();
        Button startButton = new Button("Start chat");
        layout.add(usernameField, startButton);
        add(layout);

        startButton.addClickListener(click -> {
            username = usernameField.getValue();
            remove(layout);
            createInputLayout();
        });
        addClassName("name_view");
    }


    private Component createInputLayout() {
        VerticalLayout layoutV = new VerticalLayout();
        layoutV.setWidth("100%");
        TextArea textArea = new TextArea();
        textArea.setWidth("100%");
        textArea.setHeight("100%");
        textArea.setValue(message.getMessage());

        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setWidth("250px");
        comboBox.setPlaceholder("Technology");
        comboBox.setItems("REST", "XML RPC");

        add(textArea,layout);
        sendButton.addClickListener(clickEvent -> {
            if ("XML RPC".equalsIgnoreCase(comboBox.getValue())) {
                try {
                    rpcClient.sendRPCMessage(username+":" + messageField.getValue());
                } catch (XmlRpcException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                updateMessages(textArea, messageField);
            } else if ("REST".equalsIgnoreCase(comboBox.getValue())) {
                sendMessageService.sendMessage(username+":"+ messageField.getValue());
                updateMessages(textArea, messageField);
            }
            messageField.clear();
        });

        UI ui = UI.getCurrent();
        ui.setPollInterval(100);
        ui.addPollListener(x -> {
            textArea.setValue(message.getMessage());
        });

        layout.add(messageField, sendButton,comboBox);
        layout.expand(messageField);
        return layout;
    }

    private void updateMessages(TextArea textArea, TextField textField) {
        message.addMessage(username +":" + textField.getValue());
        textArea.setValue(message.getMessage());
    }
}
