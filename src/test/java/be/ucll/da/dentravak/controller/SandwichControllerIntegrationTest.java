package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.Application;
import be.ucll.da.dentravak.domain.Sandwich;
import be.ucll.da.dentravak.repository.SandwichRepository;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static be.ucll.da.dentravak.model.SandwichTestBuilder.aSandwich;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SandwichControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private SandwichRepository sandwichRepository;

    @Before
    public void setUpASavedSandwich() {
        sandwichRepository.deleteAll();
    }

    @Test
    public void testGetSandwiches_NoSavedSandwiches_EmptyList() throws JSONException {
        String actualSandwiches = httpGet("sandwiches");
        String expectedSandwiches = "[]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testPostSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();

        String actualSandwichAsJson = httpPost("/sandwiches", sandwich);
        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":4}";

        assertThatJson(actualSandwichAsJson).isEqualTo(expectedSandwichAsJson);
    }

    @Test
    public void testPutSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("test").withIngredients("kees").withPrice(3.0).build();
        Sandwich sandwich_with_id = sandwichRepository.save(sandwich);
        sandwich_with_id.setPrice(new BigDecimal(26.0));

        String actualSandwichAsJson = httpPut("/sandwiches/"+sandwich_with_id.getId(), sandwich);
        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"test\",\"ingredients\":\"kees\",\"price\":3.0}";

        assertThatJson(actualSandwichAsJson).isEqualTo(expectedSandwichAsJson);
        //throw new RuntimeException("Implement this test and then the production code");
    }

    @Test
    public void testGetSandwiches_WithSavedSandwiches_ListReturnsSavedSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("testBreuke").withIngredients("heps").withPrice(3.0).build();
        Sandwich sandwich_with_id = sandwichRepository.save(sandwich);

        String actualSandwiches = httpGet("/sandwiches");
        String expectedSandwichAsJson = "[{\"id\":\"${json-unit.ignore}\",\"name\":\"testBreuke\",\"ingredients\":\"heps\",\"price\":3.0}]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwichAsJson);

        //throw new RuntimeException("Implement this test and then the production code");
    }
}