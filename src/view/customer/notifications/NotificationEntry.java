package view.customer.notifications;

import view.shared.TemplateEntry;
import view.shared.FontSize;

import javax.swing.*;
import java.awt.*;
/**
 * View of a notification entry
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class NotificationEntry extends TemplateEntry {

    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for an entry of a notification
     * 
     * @param info Notification information
     */
    public NotificationEntry(String info) {
        super(55);
        JLabel text = new JLabel(info);
        text.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        this.add(text);
    }
}
