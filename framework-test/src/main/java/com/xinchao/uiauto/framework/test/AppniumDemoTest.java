/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.xinchao.uiauto.framework.test;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;

/**
 * @author maping.mp
 * @version 1.0: AppniumDemoTest.java, v 0.1 2020年06月13日 4:22 下午 maping.mp Exp $
 */
public class AppniumDemoTest extends BaseTest{

    private      static AndroidDriver<WebElement> driver;
    private final String                    SEARCH_ACTIVITY = ".app.SearchInvoke";
    private final String                    ALERT_DIALOG_ACTIVITY = ".app.AlertDialogSamples";
    private final String                    PACKAGE = "io.appium.android.apis";

    /**
     * 本地driver服务
     */
    private static AppiumDriverLocalService service;


    public static URL getServiceUrl () throws Exception{
        return new URL("http://0.0.0.0:4723/wd/hub");
    }

    /**
     *
     * 配置相关文档
     * https://nishantverma.gitbooks.io/appium-for-android/desired_capabilities_for_android.html
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("avd", "AutoTest");
        capabilities.setCapability("avdLaunchTimeout",30*1000);
        capabilities.setCapability("avdReadyTimeout",30*1000);
        capabilities.setCapability("app", "/Users/maping.mp/Downloads/ApiDemos-debug.apk");
        driver = new AndroidDriver<WebElement>(getServiceUrl(), capabilities);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }


    @Test()
    public void testSendKeys() {
        driver.startActivity(new Activity(PACKAGE, SEARCH_ACTIVITY));
        AndroidElement searchBoxEl = (AndroidElement) driver.findElementById("txt_query_prefill");
        searchBoxEl.sendKeys("Hello world!");
        AndroidElement onSearchRequestedBtn = (AndroidElement) driver.findElementById("btn_start_search");
        onSearchRequestedBtn.click();
        AndroidElement searchText = (AndroidElement) new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/search_src_text")));
        String searchTextValue = searchText.getText();
        Assert.assertEquals(searchTextValue, "Hello world!");
    }

    @Test
    public void testOpensAlert() {
        // Open the "Alert Dialog" activity of the android app
        driver.startActivity(new Activity(PACKAGE, ALERT_DIALOG_ACTIVITY));

        // Click button that opens a dialog
        AndroidElement openDialogButton = (AndroidElement) driver.findElementById("io.appium.android.apis:id/two_buttons");
        openDialogButton.click();

        // Check that the dialog is there
        AndroidElement alertElement = (AndroidElement) driver.findElementById("android:id/alertTitle");
        String alertText = alertElement.getText();
        Assert.assertEquals(alertText, "Lorem ipsum dolor sit aie consectetur adipiscing\nPlloaso mako nuto siwuf cakso dodtos anr koop.");
        AndroidElement closeDialogButton = (AndroidElement) driver.findElementById("android:id/button1");

        // Close the dialog
        closeDialogButton.click();
    }
}