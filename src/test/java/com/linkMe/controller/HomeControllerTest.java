package com.linkMe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HomeControllerTest {

    @Test
    public void testIndex() {
        HomeController homeController = new HomeController();
        String viewName = homeController.index();
        assertEquals("index", viewName);
    }
}

