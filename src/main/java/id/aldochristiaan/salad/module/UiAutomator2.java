package id.aldochristiaan.salad.module;

import id.aldochristiaan.salad.module.android.uiautomator2.*;
import id.aldochristiaan.salad.util.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventFlag;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static id.aldochristiaan.salad.Salad.MAX_SWIPE_COUNT;

public class UiAutomator2 extends Mobile {

    protected AndroidDriver<AndroidElement> androidDriver;

    public UiAutomator2(AndroidDriver<AndroidElement> androidDriver) {
        this.androidDriver = androidDriver;
    }

    protected GetElement getElement() {
        return new GetElement(androidDriver);
    }

    protected GetMultipleElement getMultipleElement() {
        return new GetMultipleElement(androidDriver);
    }

    protected LongTap longTapElement() {
        return new LongTap(androidDriver);
    }

    protected Swipe swipe() {
        return new Swipe(androidDriver);
    }

    protected Tap tapElement() {
        return new Tap(androidDriver);
    }

    protected ValidateValue validateValue() {
        return new ValidateValue();
    }

    protected Type typeText() {
        return new Type(androidDriver);
    }

    protected ChangeContext changeContext() {
        return new ChangeContext(androidDriver);
    }

    protected Toast toast() {
        return new Toast(androidDriver);
    }

    protected Randomize randomize() {
        return new Randomize();
    }

    protected FakerUtil fakerUtil() {
        return new FakerUtil();
    }

    protected AndroidElement findElementBy(By by) {
        AndroidElement element = null;
        for (int i = 0; i < MAX_SWIPE_COUNT; i++) {
            try {
                element = androidDriver.findElement(by);
                break;
            } catch (NoSuchElementException e) {
                swipe().up();
            }
        }
        if (element == null) {
            throw new NoSuchElementException("Couldn't find this element : " + by.toString());
        }
        return element;
    }

    protected AndroidElement findElementBy(By by, Direction direction) {
        AndroidElement element = null;
        for (int i = 0; i < MAX_SWIPE_COUNT; i++) {
            try {
                element = androidDriver.findElement(by);
                break;
            } catch (NoSuchElementException e) {
                swipe().toDirection(direction);
            }
        }
        if (element == null) {
            throw new NoSuchElementException("Couldn't find this element : " + by.toString());
        }
        return element;
    }

    protected AndroidElement findElementBy(By by, int timeout) {
        return (AndroidElement) (new WebDriverWait(androidDriver, timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<AndroidElement> findElementsBy(By by) {
        List<AndroidElement> elements = null;
        for (int i = 0; i < MAX_SWIPE_COUNT; i++) {
            try {
                elements = androidDriver.findElements(by);
                break;
            } catch (NoSuchElementException e) {
                swipe().up();
            }
        }
        if (elements == null) {
            throw new NoSuchElementException("Couldn't find this element : " + by.toString());
        }
        return elements;
    }

    protected List<AndroidElement> findElementsBy(By by, Direction direction) {
        List<AndroidElement> elements = null;
        for (int i = 0; i < MAX_SWIPE_COUNT; i++) {
            try {
                elements = androidDriver.findElements(by);
                break;
            } catch (NoSuchElementException e) {
                swipe().toDirection(direction);
            }
        }
        if (elements == null) {
            throw new NoSuchElementException("Couldn't find this element : " + by.toString());
        }
        return elements;
    }

    protected List<WebElement> findElementsBy(By by, int timeout) {
        return (new WebDriverWait(androidDriver, timeout))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected boolean isElementExist(String elementLocator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(androidDriver, timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(elementLocator)));
            return true;
        } catch (Exception e) {
            LogUtil.error("Element with locator : " + elementLocator + " is not present!");
            return false;
        }
    }

    protected boolean isElementVisible(String elementLocator) {
        try {
            return Boolean.parseBoolean(androidDriver.findElement(getLocator(elementLocator)).getAttribute("visible"));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator, e);
        }
    }

    protected boolean isElementEnabled(String elementLocator) {
        try {
            return Boolean.parseBoolean(androidDriver.findElement(getLocator(elementLocator)).getAttribute("enabled"));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator, e);
        }
    }

    protected boolean isElementSelected(String elementLocator) {
        try {
            return Boolean.parseBoolean(androidDriver.findElement(getLocator(elementLocator)).getAttribute("selected"));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator, e);
        }
    }

    protected boolean isElementChecked(String elementLocator) {
        try {
            return Boolean.parseBoolean(androidDriver.findElement(getLocator(elementLocator)).getAttribute("checked"));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator, e);
        }
    }

    protected String getElementAttributeValue(String elementLocator, String attribute) {
        if (isElementVisible(elementLocator)) {
            return androidDriver.findElement(getLocator(elementLocator)).getAttribute(attribute);
        } else {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator);
        }
    }

    protected void validateElementVisible(String elementLocator) {
        if (isElementVisible(elementLocator)) {
            validateValue().equalsTrue(isElementVisible(elementLocator));
        } else {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator);
        }
    }

    protected void validateElementWithText(String elementLocator, String text) {
        if (isElementVisible(elementLocator)) {
            validateValue().equals(text, getText(elementLocator));
        } else {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator);
        }
    }

    protected void validateElementContainsText(String elementLocator, String text) {
        if (isElementVisible(elementLocator)) {
            validateValue().contains(text, getText(elementLocator));
        } else {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator);
        }
    }

    protected void validateEnabled(String elementLocator) {
        validateValue().equalsTrue(isElementEnabled(elementLocator), "Element with locator : " + elementLocator + " is not enabled!");
    }

    protected void validateEnabled(String elementLocator, String errorMessage) {
        validateValue().equalsTrue(isElementEnabled(elementLocator), errorMessage);
    }

    protected void validateDisabled(String elementLocator) {
        validateValue().equalsFalse(isElementEnabled(elementLocator), "Element with locator : " + elementLocator + " is enabled!");
    }

    protected void validateDisabled(String elementLocator, String errorMessage) {
        validateValue().equalsFalse(isElementEnabled(elementLocator), errorMessage);
    }

    protected void validateSelected(String elementLocator) {
        validateValue().equalsTrue(isElementSelected(elementLocator), "Element with locator : " + elementLocator + " is not selected!");
    }

    protected void validateSelected(String elementLocator, String errorMessage) {
        validateValue().equalsTrue(isElementSelected(elementLocator), errorMessage);
    }

    protected void validateNotSelected(String elementLocator) {
        validateValue().equalsFalse(isElementSelected(elementLocator), "Element with locator : " + elementLocator + " is selected!");
    }

    protected void validateNotSelected(String elementLocator, String errorMessage) {
        validateValue().equalsFalse(isElementSelected(elementLocator), errorMessage);
    }

    protected void validateDisplayed(String elementLocator) {
        validateValue().equalsTrue(isElementVisible(elementLocator), "Element with locator : " + elementLocator + " is not displayed on screen!");
    }

    protected void validateDisplayed(String elementLocator, String errorMessage) {
        validateValue().equalsTrue(isElementVisible(elementLocator), errorMessage);
    }

    protected void validateNotDisplayed(String elementLocator) {
        validateValue().equalsFalse(isElementVisible(elementLocator), "Element with locator : " + elementLocator + " is displayed on screen!");
    }

    protected void validateNotDisplayed(String elementLocator, String errorMessage) {
        validateValue().equalsFalse(isElementVisible(elementLocator), errorMessage);
    }

    protected void validateExist(String elementLocator, int timeout) {
        validateValue().equalsTrue(isElementExist(elementLocator, timeout), "Element with locator : " + elementLocator + " doesn't exist!");
    }

    protected void validateExist(String elementLocator, int timeout, String errorMessage) {
        validateValue().equalsTrue(isElementExist(elementLocator, timeout), errorMessage);
    }

    protected void validateNotExist(String elementLocator, int timeout) {
        validateValue().equalsFalse(isElementExist(elementLocator, timeout), "Element with locator : " + elementLocator + " do exist!");
    }

    protected void validateNotExist(String elementLocator, int timeout, String errorMessage) {
        validateValue().equalsFalse(isElementExist(elementLocator, timeout), errorMessage);
    }

    protected void validateChecked(String elementLocator, String errorMessage) {
        validateValue().equalsTrue(isElementChecked(elementLocator), errorMessage);
    }

    protected void validateStaleness(AndroidElement androidElement, int timeoutInSeconds) {
        validateValue().equalsTrue((new WebDriverWait(androidDriver, timeoutInSeconds)).until(ExpectedConditions.stalenessOf(androidElement)));
    }

    protected String getText(String elementLocator) {
        try {
            return androidDriver.findElement(getLocator(elementLocator)).getText();
        } catch (InvalidElementStateException e) {
            throw new InvalidElementStateException("Problem at element : " + elementLocator, e);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator, e);
        }
    }

    protected String getText(String elementLocator, int index) {
        try {
            return androidDriver.findElements(getLocator(elementLocator)).get(index).getText();
        } catch (InvalidElementStateException e) {
            throw new InvalidElementStateException("Problem at element : " + elementLocator, e);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Couldn't find this element : " + elementLocator, e);
        }
    }

    protected void takeScreenshot(String name) {
        File scrFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
        File imageFile = new File("screenshot/" + name + ".png");
        try {
            FileUtils.copyFile(Objects.requireNonNull(scrFile), imageFile);
            LogUtil.info("Screenshot taken!");
        } catch (IOException e) {
            LogUtil.error("Failed to take screenshot!");
            e.printStackTrace();
        }
    }

    protected void takeScreenshot(String path, String name) {
        File scrFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
        File imageFile = new File(path + "/" + name + ".png");
        try {
            FileUtils.copyFile(Objects.requireNonNull(scrFile), imageFile);
            LogUtil.info("Screenshot taken!");
        } catch (IOException e) {
            LogUtil.error("Failed to take screenshot!");
        }
    }

    protected void pressBackButton() {
        androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    protected void pressEnterButton() {
        androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
    }

    protected void pressSearchButton() {
        androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER)
                .withFlag(KeyEventFlag.SOFT_KEYBOARD)
                .withFlag(KeyEventFlag.KEEP_TOUCH_MODE)
                .withFlag(KeyEventFlag.EDITOR_ACTION));
    }

    protected void hideKeyboard() {
        try {
            androidDriver.hideKeyboard();
        } catch (Exception e) {
            LogUtil.info("No visible keyboard!");
        }
    }

    protected void openDeeplink(String deeplink) {
        androidDriver.get(deeplink);
    }
}
