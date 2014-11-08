package com.rainerschuster.cardgames.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface MyResources extends ClientBundle {
    public static final MyResources INSTANCE = GWT.create(MyResources.class);

    @Source("MyCss.css")
//    public CssResource css();
    public MyCss css();

}
