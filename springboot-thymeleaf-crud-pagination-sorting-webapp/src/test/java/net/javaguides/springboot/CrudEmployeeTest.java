package net.javaguides.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CrudEmployeeTest {

    private WebDriver navegador;

    @BeforeEach
    public void configuracoes() {
        System.setProperty("webdriver.edge.driver", "C:\\edgedriver_win64\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-blink-features=AutomationControlled");

        navegador = new EdgeDriver(options);
    }


    @Test
    @Order(1)
    public void testAddEmployee() {
        navegador.get("http://localhost:8080/");

        navegador.findElement(By.linkText("Add Employee")).click();
        navegador.findElement(By.id("firstName")).sendKeys("Luan");
        navegador.findElement(By.id("lastName")).sendKeys("Amaral");
        navegador.findElement(By.id("email")).sendKeys("luan@email.com");
        navegador.findElement(By.tagName("button")).click();
        
        List<WebElement> tds = navegador.findElements(By.xpath("//td[contains(text(), 'Luan')]"));

        assertEquals(false, tds.isEmpty(), "O funcionário não foi adicionado na tabela");
    }

    @Test
    @Order(2)
    public void testEditEmployee() {
        navegador.get("http://localhost:8080/");

        navegador.findElement(By.linkText("Update")).click();
        navegador.findElement(By.id("firstName")).clear();
        navegador.findElement(By.id("firstName")).sendKeys("nuaL");
        navegador.findElement(By.id("lastName")).clear();
        navegador.findElement(By.id("lastName")).sendKeys("laramA");
        navegador.findElement(By.id("email")).clear();
        navegador.findElement(By.id("email")).sendKeys("luan2@email.com");
        navegador.findElement(By.tagName("button")).click();

        List<WebElement> tds = navegador.findElements(By.xpath("//td[contains(text(), 'nuaL')]"));

        assertEquals(false, tds.isEmpty(), "Os dados do usuário não foram editados");
    }

    @Test
    @Order(3)
    public void testDeleteEmployee() {
        navegador.get("http://localhost:8080/");

        List<WebElement> deleteLinks = navegador.findElements(By.xpath("//tr[td[text()='nuaL']]//a[text()='Delete']"));

        assertEquals(false, deleteLinks.isEmpty(), "Usuário não encontrado ou o delete não foi localizado");

        if (!deleteLinks.isEmpty()) {
            deleteLinks.get(0).click();
        }
    }
    
    @AfterEach
    public void fechar() {
        if (navegador != null) {
            navegador.quit();
        }
    }
}

