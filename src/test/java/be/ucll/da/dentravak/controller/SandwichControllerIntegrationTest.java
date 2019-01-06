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
        String actualSandwiches = httpGet("http://193.191.177.8:10368/den-travak/sandwiches");
        String expectedSandwiches = "[]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testPostSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();

        String actualSandwichAsJson = httpPost("http://193.191.177.8:10368/den-travak/sandwiches", sandwich);
        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":4}";

        assertThatJson(actualSandwichAsJson).isEqualTo(expectedSandwichAsJson);
    }

    @Test
    public void testPutSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("BestTestSandWich").withIngredients("Choco").withPrice(13.0).build();
        Sandwich sandwich_with_id = sandwichRepository.save(sandwich);
        sandwich_with_id.setPrice(new BigDecimal(26.0));

        String actualSandwichAsJson = httpPut("/sandwiches/"+sandwich_with_id.getId(), sandwich);
        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"BestTestSandWich\",\"ingredients\":\"Choco\",\"price\":26}";

        assertThatJson(actualSandwichAsJson).isEqualTo(expectedSandwichAsJson);
        //throw new RuntimeException("Implement this test and then the production code");
    }

    @Test
    public void testGetSandwiches_WithSavedSandwiches_ListReturnsSavedSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("BestTestSandWich2").withIngredients("Choco").withPrice(13.0).build();
        Sandwich sandwich_with_id = sandwichRepository.save(sandwich);

        String actualSandwiches = httpGet("http://193.191.177.8:10368/den-travak/sandwiches");
        String expectedSandwichAsJson = "[{\"id\":\"${json-unit.ignore}\",\"name\":\"BestTestSandWich2\",\"ingredients\":\"Choco\",\"price\":13.0}]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwichAsJson);

        //throw new RuntimeException("Implement this test and then the production code");
    }
}