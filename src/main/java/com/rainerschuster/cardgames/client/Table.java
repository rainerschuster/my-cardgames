package com.rainerschuster.cardgames.client;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * @author Rainer Schuster
 */
public class Table extends AbsolutePanel {

    public Table() {
        super();

        setStylePrimaryName(MyResources.INSTANCE.css().cgTable());
        setPixelSize(1077, 666);
        getElement().getStyle().setOverflow(Overflow.VISIBLE);
    }
}
